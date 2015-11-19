/*************************GO-LICENSE-START*********************************
 * Copyright 2015 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *************************GO-LICENSE-END***********************************/

package com.thoughtworks.twist.driver.sahi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.sahi.Proxy;
import net.sf.sahi.config.Configuration;

/**
 * Interface to handle lifecycle of proxy server.
 * @author vinayd and sushmaj
 */


public interface ProxyServer {

    void start();

    void stop();

    void restart();

    static final class DefaultProxyServer implements ProxyServer {
    
        private Proxy proxy;
        private Process process;
        private final boolean sahiNTLM;
        private final String sahiNTLMUser;
        private final String sahiBasePath;
        
        public DefaultProxyServer(boolean sahiNTLM, String sahiNTLMUser, String sahiBasePath) {
            this.sahiNTLM = sahiNTLM;
            this.sahiNTLMUser = sahiNTLMUser;
            this.sahiBasePath = sahiBasePath;
        }
    
        /**
         * Start the proxy server and make sure it is up.
         */
        public void start() {
            if (sahiNTLM) {
                startSahiServerAsNTLM();
            } else {
                proxy = new Proxy();
                proxy.start(true);
                waitForProxy(10000);
            }
        }
        
        public void stop() {
            if (sahiNTLM) {
                endProcess();
            } else {
                proxy.stop();
            }
        }

        public void restart() {
            stop();
            start();
        }

        private void waitForProxy(int waitUntil) {
            int counter = 0;
            while(!proxy.isRunning() && counter < waitUntil) {
                try {
                    Thread.sleep(1000);
                    counter += 1000;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            if (!proxy.isRunning()) {
                throw new RuntimeException("Sahi Proxy server is not starting");
            }
        }
        
        private void startSahiServerAsNTLM() {
            String proxyStartCommand = commandForNTLM();
            try {
                startProxyServerProcess(proxyStartCommand);
    
                if (process.exitValue() != 0) {
                    throw new RuntimeException("Error starting sahi proxy");
                }
    
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error starting sahi proxy", e);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException("Error starting sahi proxy", e);
            }
        }

        private void startProxyServerProcess(String proxyStartCommand) throws IOException, InterruptedException {
            process = Runtime.getRuntime().exec(proxyStartCommand, null);
            Thread inputStreamThread = new Thread(new StreamGobbler(process.getInputStream()));
            inputStreamThread.start();
            Thread errorStreamThread = new Thread(new StreamGobbler(process.getErrorStream()));
            errorStreamThread.start();
            process.waitFor();
            inputStreamThread.join();
            errorStreamThread.join();
        }
    
        private String commandWithoutNTLM(String surroundingQuote) {
            StringBuilder builder = new StringBuilder();
            builder.append("java").append(" ").append("-classpath ").append(sahiClassPath(surroundingQuote)).append(" ").append("net.sf.sahi.Proxy").append(" ")
                    .append(surroundingQuote).append(sahiBasePath).append(surroundingQuote).append(" ").append(sahiUserDirectory(surroundingQuote));
        
            return builder.toString();
        }
    
        private String commandForNTLM() {
            return "runas /noprofile /savecred /user:" + sahiNTLMUser + " \"" + commandWithoutNTLM("\\\"") + " \"";
        }
        
        private String sahiUserDirectory(String surroundingQuote) {
            return surroundingQuote + new File("sahi/userdata").getAbsolutePath() + surroundingQuote;
        }
    
        private String sahiClassPath(String surroundingQuote) {
            StringBuilder classPath = new StringBuilder();
            classPath.append(surroundingQuote).append(sahiBasePath).append("/lib/sahi.jar").append(surroundingQuote).append(";").append(surroundingQuote)
                    .append(sahiBasePath).append("/extlib/rhino/js.jar").append(surroundingQuote).append(";")
                    .append(surroundingQuote).append(sahiBasePath).append("/extlib/apc/commons-codec-1.3.jar").append(surroundingQuote).append(";");
        
            return classPath.toString();
        }
    
        private void endProcess() {
            try {
                URL url = new URL("http://localhost:" + Configuration.getPort() + "/_s_/dyn/stopserver");
                InputStream s = url.openStream();
                s.close();
                process.destroy();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {}
        }
    }
    
    static class StreamGobbler implements Runnable {

        private final InputStream outputStream;

        public StreamGobbler(InputStream inputStream) {
            this.outputStream = inputStream;
        }

        public void run() {
            InputStreamReader isr = new InputStreamReader(outputStream);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            try {
                while ((line = br.readLine()) != null) {
                    System.out.println(">" + line);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        }
    }

}
