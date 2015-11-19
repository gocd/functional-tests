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

import com.thoughtworks.cruise.editpipelinewizard.AlreadyOnProjectManagementPage;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import org.junit.Assert;

public class MingleCardActivityConfigurationValidation {

    private String mingleURL;

    private String mingleProjectIdentifier;

    private final AlreadyOnProjectManagementPage alreadyOnProjectManagementPage;

    private String groupingCondition;

    public MingleCardActivityConfigurationValidation(AlreadyOnProjectManagementPage alreadyOnProjectManagementPage) {
        this.alreadyOnProjectManagementPage = alreadyOnProjectManagementPage;
    }

    public void setErrOnProjectId(String messageForMingleProjectIdentifier) throws Exception {
        Assert.assertTrue("Error on Mingle Project Identifier is not present or different", alreadyOnProjectManagementPage.isMessagePresent(messageForMingleProjectIdentifier));
    }

    public void setErrOnUrl(String messageForMingleURL) throws Exception {
        Assert.assertTrue("Error on Mingle URL is not present or different", alreadyOnProjectManagementPage.isMessagePresent(messageForMingleURL));
    }

    public void setErrOnMQL(String messageForMQLGroupingConditions) throws Exception {
        Assert.assertTrue("Error on MQL grouping conditions is not present or different", alreadyOnProjectManagementPage.isMessagePresent(messageForMQLGroupingConditions));
    }

    public void setResponseOnSave(final String messageForSaveStatus) throws Exception {
        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {

            public boolean call() throws Exception {
                alreadyOnProjectManagementPage.enterForMingleProjectIdentifier(mingleProjectIdentifier);
                alreadyOnProjectManagementPage.enterForMingleURL(mingleURL);
                alreadyOnProjectManagementPage.enterForMQAGroupingConditions(groupingCondition);
                alreadyOnProjectManagementPage.clickSave();
                return alreadyOnProjectManagementPage.isMessagePresent(messageForSaveStatus);
            }
        });

    }

    public void setMingleProjectIdentifier(String mingleProjectIdentifier) throws Exception {
        this.mingleProjectIdentifier = mingleProjectIdentifier;
    }

    public void setMingleURL(String mingleURL) throws Exception {
        this.mingleURL = mingleURL;
    }

    public void setGroupingCondition(final String groupingCondition) throws Exception {
        this.groupingCondition = groupingCondition;
    }

	@com.thoughtworks.gauge.Step("MingleCardActivityConfigurationValidation <table>")
	public void brtMethod(com.thoughtworks.gauge.Table table) throws Throwable {
		com.thoughtworks.twist.migration.brt.BRTMigrator brtMigrator = new com.thoughtworks.twist.migration.brt.BRTMigrator();
		try {
			brtMigrator.BRTExecutor(table, this);
		} catch (Exception e) {
			throw e.getCause();
		}
	}

}