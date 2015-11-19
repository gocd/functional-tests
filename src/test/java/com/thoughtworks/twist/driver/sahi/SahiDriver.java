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

import java.util.ArrayList;
import java.util.List;

import net.sf.sahi.client.Browser;

import com.thoughtworks.twist.recorder.RecordingListener;
import com.thoughtworks.twist.recorder.core.ITwistRecorder;
import com.thoughtworks.twist.recorder.core.SahiRecordedScript;
import com.thoughtworks.twist.recorder.core.ScriptLineModificationStrategy;

public class SahiDriver extends Browser implements ITwistRecorder {


	private List<RecordingListener> listeners = new ArrayList<RecordingListener>();
    private boolean recording;
    private final ProxyServer server;
	private boolean isClosed = false;

	public SahiDriver(String browserPath, String browserProcessName, String browserOption, ProxyServer server) {
        super(browserPath, browserProcessName, browserOption);
        this.server = server;
    }

    public void addRecordCommandListener(RecordingListener listener) {
        listeners.add(listener);
    }

    public void beginRecording() {
        recording = true;
        startRecording();
        Runnable runnable = new Runnable() {

            public void run() {
                pollRecorder();
            }
        };
        new Thread(runnable).start();
    }

    private void pollRecorder() {
        while (recording) {
            String[] steps = getRecordedSteps();
            for (String step : steps) {
                if (!"".equals(step)) {
                    for (RecordingListener listener : listeners) {
                        listener.record(new SahiRecordedScript(step.replaceFirst(";$", "")));
                    }
                }
            }
            waitFor(1000);
        }
    }

    public void endRecording() {
        recording = false;
        stopRecording();
    }

    public ScriptLineModificationStrategy getScriptModificationStrategy() {
        return ScriptLineModificationStrategy.NONE;
    }

    public void quit() {
    	close();
    }

    public void removeRecordCommandListener(RecordingListener listener) {
        listeners.remove(listener);
    }
    
    public void restartPlayback() {
        server.restart();
        super.restartPlayback();
        handleRecording();
    }

    private void handleRecording() {
        if (recording) {
            super.startRecording();
        }
    }
    
    @Override
    public void close() {
    	super.close();
    	isClosed = true;
    }
    
    public boolean isClosed() {
    	return isClosed;
    }

}
