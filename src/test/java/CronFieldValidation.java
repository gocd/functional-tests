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

import com.thoughtworks.cruise.editpipelinewizard.AlreadyOnGeneralOptionsPage;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;

public class CronFieldValidation {

	
	private final AlreadyOnGeneralOptionsPage alreadyOnGeneralPage;
	private String cronTimeSpecifierValue;

	
	public CronFieldValidation(AlreadyOnGeneralOptionsPage alreadyOnGeneralPage) {
		this.alreadyOnGeneralPage = alreadyOnGeneralPage;
	}


	public void setCronFieldValue(String cronTimeSpecifierValue) throws Exception {
		this.cronTimeSpecifierValue = cronTimeSpecifierValue;
	}

	public void setValid(final String isValid) throws Exception {
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
			public boolean call() throws Exception {
				alreadyOnGeneralPage.enterForCronTimeSpecifier(cronTimeSpecifierValue);
				alreadyOnGeneralPage.clickSave();
				return alreadyOnGeneralPage.isSavedSuccessfully() == Boolean.valueOf(isValid);
			}
		});
	}


	@com.thoughtworks.gauge.Step("CronFieldValidation <table>")
	public void brtMethod(com.thoughtworks.gauge.Table table) throws Throwable {
		com.thoughtworks.twist.migration.brt.BRTMigrator brtMigrator = new com.thoughtworks.twist.migration.brt.BRTMigrator();
		try {
			brtMigrator.BRTExecutor(table, this);
		} catch (Exception e) {
			throw e.getCause();
		}
	}

}