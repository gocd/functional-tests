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

package com.thoughtworks.cruise.utils;

import org.apache.log4j.Logger;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class Console {
    private static final Logger LOGGER = Logger.getLogger(Console.class);

    private final PrintWriter sysin;
    private final StreamPumper sysout;
    private final StreamPumper syserr;
    private final Process process;
    private final CommandLine commandLine;
    private final ConsoleOutputStreamConsumer consumer;
    public static final String TIMEOUT_DURATION = "console.timeout.duration";
    public static final String TIMEOUT_UNIT = "console.timeout.unit";
    private static final String TIMEOUT_UNIT_DEFAULT = TimeUnit.MINUTES.toString();

    public Console(CommandLine commandLine, Process process, ConsoleOutputStreamConsumer consumer, String encoding) {
        this.commandLine = commandLine;
        this.consumer = consumer;
        this.process = process;
        sysout = StreamPumper.pump(process.getInputStream(), new OutputConsumer(), "", encoding);
        syserr = StreamPumper.pump(process.getErrorStream(), new ErrorConsumer(), "ERROR: ", encoding);
        sysin = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
        setupTimeoutWatchdog();
    }

    boolean isWatchingForTimeout() {
        return null != System.getProperty(TIMEOUT_DURATION);
    }

    TimeUnit timeoutUnit() throws IllegalArgumentException {
        return TimeUnit.valueOf(System.getProperty(TIMEOUT_UNIT, TIMEOUT_UNIT_DEFAULT).toUpperCase());
    }

    private void setupTimeoutWatchdog() {
        if (!isWatchingForTimeout()) { return; }
        try {
            Long duration = Long.valueOf(System.getProperty(TIMEOUT_DURATION));
            TimeUnit timeUnit = timeoutUnit();
            watchForTimeout(duration, timeUnit);
        } catch (NumberFormatException invalidNumber) {
            LOGGER.error(String.format("Environment %s='%s' is not a valid number. Not watching for a timeout.",
                    TIMEOUT_DURATION,  System.getProperty(TIMEOUT_DURATION)));
        } catch (IllegalArgumentException invalidTimeUnit) {
            LOGGER.error(String.format("Environment %s='%s' is not a valid TimeUnit. Not watching for a timeout.",
                    TIMEOUT_UNIT,  System.getProperty(TIMEOUT_UNIT)));
        }
    }

    public int waitForExit() {
        int returnValue = -1;
        try {
            returnValue = process.waitFor();
            sysout.readToEnd();
            syserr.readToEnd();
        } catch (InterruptedException ignored) {
        }
        return returnValue;
    }

    void finishInput() {
        sysin.close();
    }

    void type(String input) {
        sysin.println(input);
        sysin.flush();
    }

    public void kill() {
        process.destroy();
    }

    public boolean isRunning() {
        try {
            process.exitValue();
            return false;
        }
        catch (IllegalThreadStateException e) {
            return true;
        }
    }

    private void watchForTimeout(long duration, TimeUnit unit) {
        new Thread(new TimeoutWatcher(duration, unit)).start();
    }

    private class OutputConsumer implements StreamConsumer {
        public void consumeLine(String line) {
            consumer.stdOutput(line);
        }
    }

    private class ErrorConsumer implements StreamConsumer {
        public void consumeLine(String line) {
            consumer.errOutput(line);
        }
    }

    private class TimeoutWatcher implements Runnable {
        private final long duration;
        private final TimeUnit unit;

        public TimeoutWatcher(long duration, TimeUnit unit) {
            this.duration = duration;
            this.unit = unit;
        }

        public void run() {
            while (isRunning()) {
                sleepForOneUnit();
                if (sysout.didTimeout(duration, unit) && syserr.didTimeout(duration, unit)) {
                    String message = format("CRUISE: timeout after %d %s for command '%s' ", duration, unit.toString().toLowerCase(), commandLine.toStringForDisplay());
                    consumer.errOutput(message);
                    LOGGER.error(message);
                    kill();
                    waitForExit();
                }
            }
        }

        private void sleepForOneUnit() {
            try {
                unit.sleep(1L);
            } catch (InterruptedException ignored) {
            }
        }

    }
}
