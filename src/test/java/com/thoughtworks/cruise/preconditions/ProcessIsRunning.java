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

package com.thoughtworks.cruise.preconditions;

import com.thoughtworks.cruise.client.Response;
import com.thoughtworks.cruise.util.ProcessUtils;
import com.thoughtworks.cruise.util.SystemUtil;
import com.thoughtworks.cruise.utils.CommandUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static com.thoughtworks.cruise.util.ExceptionUtils.bomb;

public abstract class ProcessIsRunning implements DisposableBean, InitializingBean {

    public void destroy() throws Exception {
        stop();
    }
    
    public void afterPropertiesSet() throws Exception {
        try {
            start();
        } catch (Exception e) {
            System.err.println("Caught an error:" + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void start() throws Exception {
        execute(startCommand(), getStartEnvVariables());
    }

    public void stop() throws Exception {
    	int numberOfSecondsToWait = 60;

    	String command = stopCommand();
        do {
            try {
                execute(command, new HashMap<String, String>());

                Thread.sleep(1000);
                numberOfSecondsToWait--;
            } catch (Throwable e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } while (!isProcessStopped() && numberOfSecondsToWait > 0);

        pKill();

        if (ProcessUtils.waitForProcessesToExitOrTimeoutToElapse(5000, getWorkingDir())) {
        	throw new RuntimeException(String.format("Failed to stop process with pid %d [%s]", pid(), this));
        }
    }

    private void pKill() {
        pkill(getWorkingDir());
    }

	public static void pkill(String pattern) {
		if (SystemUtil.isWindows()) {
            return;
        }

		killQuietly(pattern, false);
	}

    private static boolean killQuietly(String pattern, boolean forceKill) {
        if (forceKill) {
            ProcessUtils.pkill(pattern);
        } else {
            ProcessUtils.pkillForcibly(pattern);
        }

        return ProcessUtils.waitForProcessesToExitOrTimeoutToElapse(5000, pattern);
    }

    protected void execute(String command, Map<String, String> envVariables) throws Exception {
        ProcessBuilder builder = new ProcessBuilder();
        builder.redirectErrorStream(true);

        /* We need to unset all environment variables that the parent agent is setting while running twist jobs. Else, all environment variables are reported as overridden. */
        builder.environment().remove("GO_SERVER_URL");
        builder.environment().remove("AGENT_DIR");
        builder.environment().remove("AGENT_WORK_DIR");
        builder.environment().remove("AGENT_MEM");
        builder.environment().remove("AGENT_MAX_MEM");
        builder.environment().remove("GO_ENVIRONMENT_NAME");
        builder.environment().remove("GO_PIPELINE_NAME");
        builder.environment().remove("GO_PIPELINE_COUNTER");
        builder.environment().remove("GO_PIPELINE_LABEL");
        builder.environment().remove("GO_STAGE_NAME");
        builder.environment().remove("GO_STAGE_COUNTER");
        builder.environment().remove("GO_RERUN_OF_STAGE_COUNTER");
        builder.environment().remove("GO_JOB_NAME");
        builder.environment().remove("CRUISE_SERVER_URL");
        builder.environment().remove("CRUISE_PIPELINE_NAME'");
        builder.environment().remove("CRUISE_PIPELINE_COUNTER");
        builder.environment().remove("CRUISE_PIPELINE_LABEL");
        builder.environment().remove("CRUISE_STAGE_NAME");
        builder.environment().remove("CRUISE_STAGE_COUNTER");
        builder.environment().remove("CRUISE_JOB_NAME");
        builder.environment().remove("CRUISE_ENVIRONMENT_NAME");
        builder.environment().remove("JVM_DEBUG");
        builder.environment().remove("GC_LOG");
        
        builder.environment().putAll(envVariables);
        builder.directory(new File(getWorkingDir()));
        if (SystemUtil.isWindows()) {
        	builder.environment().put("DAEMON", "N");
            builder.command("cmd ", "/c", command);
        } else {
            builder.environment().put("DAEMON", "Y");
            builder.command("bash", "../../scripts/with-java.sh", command);
        }
        System.err.println("Executing command: " + StringUtils.join(builder.command(), " ") + " (in " + getWorkingDir() + ")");
        Process process = builder.start();
        PrintStream out = new PrintStream(new File(getWorkingDir(), "output.log"));
    	NewStreamPumper.pump(process.getInputStream(), "out", out);
    	NewStreamPumper.pump(process.getErrorStream(), "err", out);
        process.getOutputStream().close();
    }

    protected abstract boolean pumpOutputStream();
    protected abstract boolean pumpErrorStream();

    protected String pid() throws IOException {
        return FileUtils.readFileToString(new File(pidFile())).trim();
    }

    protected abstract String pidFile() throws IOException;

    protected abstract boolean isProcessStopped();

    protected abstract String startCommand();

    protected abstract String stopCommand();

    protected abstract String getWorkingDir();

    protected abstract Map<String, String> getStartEnvVariables();

    protected boolean isProcessStoppedOnWindows(String windowTitle) {
        String[] cmd = {"tasklist", "/NH", "/FI", "WINDOWTITLE eq " + windowTitle};
        String output = CommandUtils.exec(cmd);
        return output.contains("No tasks");
    }

    protected boolean isProcessStoppedOnLinux() {
        try {
            String pid = pid();
            String output = CommandUtils.exec("ps", "h", "-p", pid, "-o", "comm");
            boolean isStopped = !output.contains("java");
            return isStopped;
        } catch (IOException e) {
            System.out.println("Unable to read pid in order to stop a process");
            e.printStackTrace();
            throw bomb(e);
        }
    }

	protected static Response head(String url) {
	    return httpInvoke(url, new HeadMethod(url));
	}
	
	protected Response get(String url) {
        return httpInvoke(url, new GetMethod(url));
    }

    private static Response httpInvoke(String url, HttpMethod headMethod) {
        HttpClient httpClient = new HttpClient();
	    try {
	        int returnCode = httpClient.executeMethod(headMethod);
	        return new Response(returnCode, headMethod.getResponseBodyAsString());
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to get resource " + url + ".", e);
	    }
    }
}
