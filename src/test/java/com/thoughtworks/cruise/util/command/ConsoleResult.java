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

package com.thoughtworks.cruise.util.command;

import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.cruise.util.ListUtil.join;

public class ConsoleResult {
    private int returnValue;
    private List<String> output;
    private List<String> error;
    private boolean failOnNonZeroReturn;

    public ConsoleResult(int returnValue, List<String> output, List<String> error) {
        this(returnValue, output, error, true);
    }

    public ConsoleResult(int returnValue, List<String> output, List<String> error, boolean failOnNonZeroReturn) {
        this.returnValue = returnValue;
        this.output = output;
        this.error = error;
        this.failOnNonZeroReturn = failOnNonZeroReturn;
    }

    public List<String> output() {
        return output;
    }

    public List<String> error() {
        return error;
    }

    public int returnValue() {
        return returnValue;
    }

    public String outputAsString() {
        return join(output(), "\n");
    }

    public String errorAsString() {
        return join(error(), "\n");
    }

    public boolean failed() {
        // Some git commands return non-zero return value for a "successfull" command (e.g. git config --get-regexp)
        // In such a scenario, we can't simply rely on return value to tell whether a command is successful or not
        return failOnNonZeroReturn ? returnValue() != 0 : false;
    }

    public String describe() {
        return "--OUTPUT ---\n" + outputAsString() + "\n"
                + "--- ERROR ---\n" + errorAsString() + "\n"
                + "---\n"; 
    }

    public static ConsoleResult unknownResult() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Unknown result.");
        return new ConsoleResult(-1, new ArrayList<String>(), list);
    }
}
