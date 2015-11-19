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

package com.thoughtworks.cruise.config;

import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;

public class RetryOnConflictModifier implements ConfigModifier {

    private final Configuration configuration;

    public RetryOnConflictModifier(Configuration configuration) {
        this.configuration = configuration;
    }
    
    @Override
    public void modifyConfig(final ModifyCommand command) {
        Assertions.waitUntil(Timeout.TWO_MINUTES, new Predicate() {
            
            @Override
            public boolean call() throws Exception {
                CruiseConfigDom configDom = getDom();
                command.modify(configDom);
                writeToConfigFile(configDom);
                return true;
            }
        });
    }
    
    private CruiseConfigDom getDom() {
        return configuration.provideDom();
    }       
    
    private void writeToConfigFile(CruiseConfigDom dom) {
        configuration.setDom(dom);
    }
}
