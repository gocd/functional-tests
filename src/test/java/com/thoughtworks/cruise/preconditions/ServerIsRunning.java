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

import com.thoughtworks.cruise.RuntimePath;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.util.SystemUtil;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Timeout;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigFileEditor;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerIsRunning extends ProcessIsRunning {
    private CruiseConfigUtil configFile;
    private boolean twistStartedServer;
    private boolean fanInTurnedOff;

    public ServerIsRunning() {
        configFile = new CruiseConfigUtil(new CruiseConfigFileEditor());
    }

    public void start() throws Exception {
        twistStartedServer = !isAlreadyRunning();
        forceStart();
    }

    public void forceStart() throws IOException, Exception {
        if (!(fanInTurnedOff)) {
            configFile.resetCruiseConfig();
        }

        if (!isAlreadyRunning()) {
            copyLog4jProperties();
            copyJabbaWrapper();
            super.start();
            waitForServerToStart();
        }
    }

    private void copyLog4jProperties() throws IOException {
        File agentLog4j = new File(RuntimePath.getServerConfigPath(), "logback.xml");
        FileUtils.copyFile(new File(RuntimePath.pathFor("properties"), "server-logback.xml"), agentLog4j);
    }

    private void copyJabbaWrapper() throws IOException {
        File withJava = new File(RuntimePath.getServerRoot(), "with-java.sh");
        FileUtils.copyFile(new File(RuntimePath.pathFor("scripts"), "with-java.sh"), withJava);
    }

    public void stop() throws Exception {
        if (twistStartedServer) {
            forceStop();
        }
    }

    public void forceStop() throws Exception {
        super.stop();
    }

    public static boolean isAlreadyRunning() {
        try {
            return head(Urls.urlFor("/admin/agent")).success();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isProcessStopped() {
        if (SystemUtil.isWindows()) {
            return isProcessStoppedOnWindows("cruise server - server.cmd");
        } else {
            return isProcessStoppedOnLinux();
        }
    }

    private void waitForServerToStart() throws Exception {
        Assertions.waitUntil(Timeout.TEN_MINUTES, new Assertions.Predicate() {
            public boolean call() {
                return isAlreadyRunning();
            }
        });
    }

    protected String getWorkingDir() {
        return RuntimePath.getServerRoot();
    }

    public File fileFor(String relPath) {
        return new File(getWorkingDir(), relPath);
    }

    protected Map<String, String> getStartEnvVariables() {
        String extraDBParams = additionalDBParametersIfPostgresqlIsToBeUsed();
        StringBuilder extraParams = new StringBuilder(extraDBParams);
        if ("Y".equals(System.getenv("USE_JETTY_6"))){
            extraParams.append(" -Dapp.server=com.thoughtworks.go.server.Jetty6Server");
        }
        HashMap<String, String> env = new HashMap<String, String>();
        env.put("GO_SERVER_SYSTEM_PROPERTIES",
                "-Dalways.reload.config.file=true " +
                        "-Dcruise.buildCause.producer.interval=1000 " +
                        "-Dcruise.material.update.interval=3000 " +
                        "-Dcruise.material.update.delay=1000 " +
                        "-Dcruise.produce.build.cause.interval=1000 " +
                        "-Dcruise.produce.build.cause.delay=1000 " +
                        "-Dmaterial.update.idle.interval=1000 " +
                        "-Dcruise.xmpp.port=61221 " +
                        "-Dcruise.agent.service.refresh.interval=5000 " +
                        "-Dcruise.shine.sparql.url=http://localhost:8253/go/shine/sparql.xml " +
                        "-Dcruise.shine.stage.feed=http://localhost:8253/go/api/feeds/stages.xml " +
                        "-Dagent.connection.timeout=50 " +
                        "-Dcruise.unresponsive.job.warning=1 " +
                        "-Dcruise.pipelineStatus.cache.interval=800 " +
                        "-Dcommand.repo.warning.timeout=30000 " +
                        "-Dnew.plugins.framework.enabled=Y " +
                        "-DDB_DEBUG_MODE=true" +
                        extraParams.toString());
        env.put("GO_SERVER_PORT", Urls.SERVER_PORT);
        env.put("GO_SERVER_SSL_PORT", Urls.SSL_PORT);
        String pipeline_counter = System.getenv("GO_PIPELINE_COUNTER") != null ? System.getenv("GO_PIPELINE_COUNTER") : "0";
        env.put("GO_PIPELINE_COUNTER", pipeline_counter);
        String serverMem = System.getenv("TWIST_GO_SERVER_MEM") != null ? System.getenv("TWIST_GO_SERVER_MEM") : "512m";
        String serverMaxMem = System.getenv("TWIST_GO_SERVER_MAX_MEM") != null ? System.getenv("TWIST_GO_SERVER_MAX_MEM") : "1024m";
        env.put("SERVER_MEM", serverMem);
        env.put("SERVER_MAX_MEM", serverMaxMem);
        env.put("JVM_DEBUG", "true");
        if (fanInTurnedOff) {
            String addFaninJVMArg = env.get("GO_SERVER_SYSTEM_PROPERTIES") + " -Dresolve.fanin.revisions=N";
            env.put("GO_SERVER_SYSTEM_PROPERTIES", addFaninJVMArg);
        }
        String useNewRails = System.getenv("USE_NEW_RAILS");
        if (useNewRails != null && useNewRails.equals("N")) {
            String addRails4JVMArg = env.get("GO_SERVER_SYSTEM_PROPERTIES") + " -Duse.new.rails=N";
            env.put("GO_SERVER_SYSTEM_PROPERTIES", addRails4JVMArg);
        }
        String startUpArgs = System.getenv("ADDITIONAL_STARTUP_ARGS");
        if (startUpArgs != null && !startUpArgs.equals("")) {
            String additionalStartupArgs = env.get("GO_SERVER_SYSTEM_PROPERTIES") + " " + startUpArgs;
            env.put("GO_SERVER_SYSTEM_PROPERTIES", additionalStartupArgs);
        }
        return env;
    }


    private String additionalDBParametersIfPostgresqlIsToBeUsed() {
        if (!("Y".equals(System.getenv("USE_POSTGRESQL")))) {
            return "";
        }
        String db_user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "postgres";
        String db_pwd = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "";
        return String.format(" -Duse.postgresql.db=Y -Dgo.database.provider=com.thoughtworks.go.postgresql.PostgresqlDatabase -Ddb.host=%s -Ddb.user=%s -Ddb.password=%s -Ddb.name=%s -Ddb.port=%s",
                System.getenv("POSTGRES_DB_HOST_TO_USE"), db_user, db_pwd, System.getenv("POSTGRES_DB_NAME_TO_USE").toLowerCase(), "5432");
    }

    protected String pidFile() throws IOException {
        return new File(getWorkingDir(), "go-server.pid").getCanonicalPath();

    }

    protected String startCommand() {
        return SystemUtil.isWindows() ? "start-server.bat" : "./server.sh";
    }

    protected String stopCommand() {
        return SystemUtil.isWindows() ? "stop-server.bat" : "./stop-server.sh";
    }

    @Override
    protected boolean pumpOutputStream() {
        return false;
    }

    @Override
    protected boolean pumpErrorStream() {
        return true;
    }

    public void setFanInOff(boolean fanInTurnedOff) {
        this.fanInTurnedOff = fanInTurnedOff;
    }
}
