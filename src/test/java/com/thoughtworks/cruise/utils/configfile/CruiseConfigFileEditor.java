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

package com.thoughtworks.cruise.utils.configfile;

import com.thoughtworks.cruise.RuntimePath;
import com.thoughtworks.cruise.util.FileUtil;
import com.thoughtworks.cruise.util.XmlUtil;
import com.thoughtworks.cruise.utils.ConfigFileFixture;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import static com.thoughtworks.cruise.util.ExceptionUtils.bomb;

/**
 * Used for editing the config file directly on the file system.
 */
public class CruiseConfigFileEditor extends ConfigFileContents {
    public static final String DEFAULT_CIPHER = "269298bc31c44620";
    private static final String SERVER_ID_XPATH = "//server/@serverId";
    private static final String TOKEN_GENERATION_KEY_XPATH = "//server/@tokenGenerationKey";

    public static File getCruiseConfigFile() {
        return new File(RuntimePath.getServerConfigPath() + "/cruise-config.xml");
    }

    public void write(String xml) {
        FileChannel channel = null;
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileLock lock = null;
        try {
            File configFile = getCruiseConfigFile();
            if (configFile.exists()) {
                String tokenGenerationKey = XmlUtil.parse(FileUtil.readToEnd(configFile)).selectSingleNode(TOKEN_GENERATION_KEY_XPATH).getText();
                String serverId = XmlUtil.parse(FileUtil.readToEnd(configFile)).selectSingleNode(SERVER_ID_XPATH).getText();
                Document dom = XmlUtil.parse(xml);
                Element node = (Element) dom.selectSingleNode("//server");
                node.setAttributeValue("serverId", serverId);
                node.setAttributeValue("tokenGenerationKey", tokenGenerationKey);
                xml = dom.asXML();
            }
            System.err.println("Writing out config file to " + configFile.getCanonicalPath());
            configFile.getParentFile().mkdirs();
            configFile.createNewFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile(configFile, "rw");

            channel = randomAccessFile.getChannel();
            lock = channel.lock();
            randomAccessFile.seek(0);
            randomAccessFile.setLength(0);
            outputStream = new FileOutputStream(randomAccessFile.getFD());
            IOUtils.write(xml, outputStream);
        } catch (Exception e) {
            throw bomb("Error writing config file: " + e.getMessage(), e);
        } finally {
            if (channel != null && lock != null) {
                try {
                    lock.release();
                    channel.close();
                    IOUtils.closeQuietly(outputStream);
                    IOUtils.closeQuietly(inputStream);
                } catch (IOException e) {
                }
            }
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
    }

    public void reset() {
        write(ConfigFileFixture.EMPTY_CRUISE);
        try {
            writeDefaultCipher();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void writeDefaultCipher() throws IOException {
        File cipher = new File(getCruiseConfigFile().getParent(), "cipher");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(cipher);
            fileOutputStream.write(DEFAULT_CIPHER.getBytes());
        } finally {
            if (fileOutputStream != null) fileOutputStream.close();
        }
    }

    protected String currentContents() {
        FileChannel channel = null;
        FileInputStream inputStream = null;
        FileLock lock = null;
        try {
            File configFile = new File("target/cruise-server-1.3.2/config/cruise-config.xml");
            //File configFile = getCruiseConfigFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile(configFile, "r");
            channel = randomAccessFile.getChannel();
            lock = channel.lock(0L, Long.MAX_VALUE, true);
            randomAccessFile.seek(0);
            FileInputStream is = new FileInputStream(randomAccessFile.getFD());
            return IOUtils.toString(is);
        } catch (Exception e) {
            throw bomb("Error reading config file: " + e.getMessage(), e);
        } finally {
            if (channel != null && lock != null) {
                try {
                    lock.release();
                    channel.close();
                    IOUtils.closeQuietly(inputStream);
                } catch (IOException e) {
                }
            }
        }
    }
}
