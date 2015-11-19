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

package com.thoughtworks.cruise.context;

import com.thoughtworks.cruise.RuntimePath;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.preconditions.AgentLauncher;
import com.thoughtworks.cruise.state.CurrentUsernameProvider;
import com.thoughtworks.cruise.util.SystemUtil;
import com.thoughtworks.gauge.AfterSpec;
import com.thoughtworks.gauge.Specification;
import com.thoughtworks.gauge.ExecutionContext;
import net.sf.sahi.client.Browser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.h2.tools.Recover;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;

public class CaptureGoState {
    private File baseDir;
    public static final String SETUP = "SETUP";
    public static final String TEAR_DOWN = "TEAR_DOWN";
    public static final int LAST_FEW_LINES = 500;
    private final Browser browser;

    public CaptureGoState(Browser browser) {
        this.browser = browser;
        baseDir = subDir(new File(RuntimePath.getServerRoot()).getParentFile(), "go-state");
    }


    @com.thoughtworks.gauge.Step("Capture go state <identifier> - setup")
    public void setUp(final String identifier) throws IOException {
        if (SystemUtil.isWindows())
            return;
        report(identifier, SETUP);
    }

    @com.thoughtworks.gauge.Step("Capture go state <identifier> - teardown")
	public void tearDown(String identifier) throws Exception {
        generateReportWithAgentData(identifier, TEAR_DOWN);
    }


    @AfterSpec(tags = {"debug"})
    public void tearDownGauge(ExecutionContext context) throws Exception {
        System.out.println("this is from the after spec");
        generateReportWithAgentData(context.getCurrentSpecification().getName(), TEAR_DOWN);
    }

    public void generateReportWithAgentData(String identifier, String event)
        throws IOException {
        if (SystemUtil.isWindows())
            return;
        report(identifier, event);
        try {
            reportAgentData(identifier);
        } catch (IOException e) {
            //Ignore IOException as file copy failed. We do not want to fail a job if tear down throws up.
        }
    }

    private void report(String identifier, String event) throws IOException {
        try {
            reportScreenshot(eventDir(identifier, event));
        }catch (Exception e) {
            // IGNORE BECAUSE FIREFOX CAN'T HANDLE THOSE FUNKY JS HANDLES THAT PHANTOMJS SUPPORTS! TAKE THAT FIREFOX!
        }
        reportHealthState(eventDir(identifier, event));
        reportCruiseConfig(eventDir(identifier, event));
        reportDatabase(eventDir(identifier, event));
        reportLogFileTails(identifier, event);
    }

    private void reportScreenshot(File eventDir) {
        File screenshotsDir = subDir(eventDir, "screenshots");
        browser.execute(String.format("window.callPhantom('%s/view.%s.jpg')", screenshotsDir.getAbsolutePath(), System.currentTimeMillis()));
    }

    @SuppressWarnings("unchecked")
        private void reportAgentData(String identifier) throws IOException {
            if (!AgentLauncher.TWIST_AGENTS_DIR.exists()) { return; }
            File agentsDir = subDir(stateDir(identifier), "agent-data");
            FileUtils.copyDirectory(AgentLauncher.TWIST_AGENTS_DIR, agentsDir, new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    return !(pathname.isDirectory() && pathname.getName().equals("pipelines"));
                }
            });
            Collection<File> files = FileUtils.listFiles(agentsDir, null, true);
            for (File file : files) {
                if (!file.getAbsolutePath().endsWith(".log")) file.delete();
            }
        }

    private File stateDir(String identifier) {
        return subDir(baseDir, identifier);
    }

    private File subDir(final File baseDir, String suffix) {
        File subDir = new File(baseDir, suffix);
        if (! subDir.exists()) subDir.mkdirs();
        return subDir;
    }

    private File eventDir(String identifier, final String event) {
        return subDir(stateDir(identifier), event);
    }

    private void reportLogFileTails(final String identifier, final String event) throws IOException {
        File serverRoot = new File(RuntimePath.getServerRoot());
        FileFilter fileFilter = new RegexFileFilter("^.+\\.log.?\\d*$");
        for (File logFile : serverRoot.listFiles(fileFilter)) {
            reportLogTail(logFile, identifier, event);
        }
    }

    private void reportLogTail(File logFile, final String identifier, final String event) throws IOException {
        FileReader fileReader = null;
        LineNumberReader lineReader = null;
        LinkedList<String> queue = new LinkedList<String>();
        try {
            fileReader = new FileReader(logFile);
            lineReader = new LineNumberReader(fileReader);
            String line = null;
            do {
                queue.add(line);
                if (queue.size() > LAST_FEW_LINES) {
                    queue.remove();
                }
                line = lineReader.readLine();
            } while (line != null);
            StringBuilder builder = new StringBuilder();
            for (String lineFromQueue : queue) {
                builder.append(lineFromQueue).append("\n");
            }
            writeTo(new File(eventDir(identifier, event), "log-tail-" + event), String.format("------\nFile: %s\n\nLast few lines: \n\n%s\n\n------\n\n\n", logFile.getPath(), builder.toString()));
        } finally {
            if (lineReader != null) {
                lineReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
        }
    }

    private void reportDatabase(File dir) throws IOException {
        File file = new File(RuntimePath.getServerRoot(), "db/h2db/cruise.h2.db");
        File db = new File(dir, "cruise.h2.db");
        FileUtils.copyFile(file, db);
        try {
            new Recover().runTool("-dir", dir.getAbsolutePath(), "-db", "cruise");
        } catch (Exception e) {
            writeTo(new File(dir, "error_capturing_db"), ExceptionUtils.getStackTrace(e));
        }
        FileUtils.deleteQuietly(db);
    }

    private void reportCruiseConfig(File dir) throws IOException {
        captureCruiseConfigOverHttp(dir, "cruise-config-via-http-as-ADMIN", getTalkToCruiseAsUser("admin"));
        captureCruiseConfigOverHttp(dir, "cruise-config-via-http-as-ANON", getTalkToCruiseAsUser(null));
        FileUtils.copyFile(configFileOnDisk(), new File(dir, "cruise-config-from-FS.xml"));
    }

    private File configFileOnDisk() {
        return new File(RuntimePath.getServerConfigPath(), "cruise-config.xml");
    }

    private void captureCruiseConfigOverHttp(File dir, String file, TalkToCruise asUser) throws IOException {
        File statusFile = new File(dir, file + ".status");

        try {
            TalkToCruise.CruiseResponse response = configContentFromServer(asUser);
            writeTo(statusFile, "Status: " + response.getStatus());
            writeTo(statusFile, "Md5: " + response.getResponseHeader(Configuration.X_CRUISE_CONFIG_MD5));
        } catch (Exception e) {
            writeTo(statusFile, String.format("Response status: %s\n\nBody:\n%s", "500", ExceptionUtils.getStackTrace(e)));
        }
    }

    private CruiseResponse configContentFromServer(TalkToCruise asUser) {
        return asUser.get(Urls.urlFor("/admin/configuration/file.xml"));
    }

    private static void writeTo(File file, String message) throws IOException {
        PrintWriter printer = null;
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
            printer = new PrintWriter(writer);
            printer.println(message);
        } finally {
            if (printer != null) {
                printer.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    private void reportHealthState(final File eventDir) throws IOException {
        File dir = subDir(eventDir, "health_dump");
        reportServerHealthStateAs("admin", new File(dir, "as_admin.json"));
        reportServerHealthStateAs(null, new File(dir, "as_anon.json"));
    }

    private void reportServerHealthStateAs(String user, File file) throws IOException {
        try {
            TalkToCruise asAdmin = getTalkToCruiseAsUser(user);
            TalkToCruise.CruiseResponse response = asAdmin.get(Urls.urlFor("/server/messages.json"));
            writeTo(file, String.format("Response status: %s\n\nBody:\n%s", response.getStatus(), response.getBody()));
        } catch (Exception e) {
            writeTo(file, String.format("Response status: %s\n\nBody:\n%s", "500", ExceptionUtils.getStackTrace(e)));
        }
    }

    private static TalkToCruise getTalkToCruiseAsUser(final String user) {
        CurrentUsernameProvider admin = new CurrentUsernameProvider() {
            public String loggedInUser() {
                return user;
            }
        };
        return new TalkToCruise(admin);
    }

    public static interface LogFileHandler {
        void handle(File logFile) throws IOException;
    }
}
