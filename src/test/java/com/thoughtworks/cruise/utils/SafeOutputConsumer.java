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

import java.util.ArrayList;
import java.util.List;

public class SafeOutputConsumer extends ConsoleOutputStreamConsumer {
    private List<CommandArgument> arguments = new ArrayList<CommandArgument>();

    public SafeOutputConsumer(ConsoleOutputStreamConsumer consumer) {
        super(consumer.getStdConsumer(), consumer.getErrorConsumer());
    }

    public void addArgument(CommandArgument argument) {
        arguments.add(argument);
    }

    public void stdOutput(String line) {
        line = replaceSecretInfo(line);

        super.stdOutput(line);
    }

    public void errOutput(String line) {
        line = replaceSecretInfo(line);

        super.errOutput(line);
    }

    private String replaceSecretInfo(String line) {
        for (CommandArgument argument : arguments) {
            line = argument.replaceSecretInfo(line);
        }
        return line;
    }

    public void addArguments(List<CommandArgument> arguments) {
        this.arguments.addAll(arguments);
        }
    }
