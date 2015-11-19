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

public class SystemOutStreamConsumer extends ConsoleOutputStreamConsumer {

    public SystemOutStreamConsumer(Class clazz) {
        super(new StandardOutConsumer(clazz.getName()), new StandardOutConsumer(clazz.getName()));
    }

    private static class StandardOutConsumer implements StreamConsumer{

        private Logger log;
        
        public StandardOutConsumer(String name) {
            this.log = Logger.getLogger(name);
        }

        @Override
        public void consumeLine(String line) {
            log.info(line);  
        }
    }
}
