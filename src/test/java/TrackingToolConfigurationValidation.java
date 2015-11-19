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

public class TrackingToolConfigurationValidation {

    private AlreadyOnProjectManagementPage alreadyOnProjectManagementPage;

    private String trackingToolURL;

    private String regexValue;

    public TrackingToolConfigurationValidation(AlreadyOnProjectManagementPage alreadyOnProjectManagementPage) {
        this.alreadyOnProjectManagementPage = alreadyOnProjectManagementPage;
    }

    public void setMessageForRegex(String messageForRegex) throws Exception {
        Assert.assertTrue("Error message on URL not present or different.",
                alreadyOnProjectManagementPage.isMessagePresent(messageForRegex));
    }

    public void setMessageForSaveStatus(final String messageForSaveStatus) throws Exception {
        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            public boolean call() throws Exception {
                alreadyOnProjectManagementPage.enterForTrackingToolURL(trackingToolURL);
                alreadyOnProjectManagementPage.enterTrackingToolRegex(regexValue);
                alreadyOnProjectManagementPage.clickSave();
                return alreadyOnProjectManagementPage.isMessagePresent(messageForSaveStatus);
            }
        });
    }

    public void setMessageForURL(String messageForURL) throws Exception {
        Assert.assertTrue("Error message on URL not present or different.", alreadyOnProjectManagementPage.isMessagePresent(messageForURL));
    }

    public void setRegexValue(String regexValue) throws Exception {
        this.regexValue = regexValue;
    }

    public void setURLValue(String urlValue) throws Exception {
        this.trackingToolURL = urlValue;
    }

	@com.thoughtworks.gauge.Step("TrackingToolConfigurationValidation <table>")
	public void brtMethod(com.thoughtworks.gauge.Table table) throws Throwable {
		com.thoughtworks.twist.migration.brt.BRTMigrator brtMigrator = new com.thoughtworks.twist.migration.brt.BRTMigrator();
		try {
			brtMigrator.BRTExecutor(table, this);
		} catch (Exception e) {
			throw e.getCause();
		}
	}

}