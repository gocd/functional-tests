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

package com.thoughtworks.cruise.state;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CurrentPageState {
	private Page currentPage;
	
	public void currentPageIs(Page page) {
		this.currentPage = page;
	}

	public void assertCurrentPageIs(Page expectedPage) {
		assertThat(currentPage, is(expectedPage));
	}
	
	public static enum Page {
		ENVIRONMENT_DETAIL,
		JOB_DETAILS,
		PIPELINE_HISTORY, 
		STAGE_DETAILS,
		STAGE_DETAIL_MATERIALS_TAB,
		STAGE_DETAIL_JOBS_TAB,
		PIPELINE_DETAILS, 
		PIPELINE_DASHBOARD,
		ADMIN_OAUTH_TAB, 
		USERS_SUMMARY_PAGE, 
		SERVER_CONFIGURATION_TAB, 
		SERVER_DETAILS_PAGE,
		ENVIONMENT_SHOW_PAGE, 
		COMPARE_PAGE,
		EDIT_PIPELINE_WIZARD_GENERAL_PAGE, 
		EDIT_PIPELINE_WIZARD_PROJECT_MANAGEMENT_PAGE,		
		EDIT_PIPELINE_WIZARD_PARAMETERS_PAGE, 
		EDIT_PIPELINE_WIZARD_ENVIRONMENT_PAGE, 
		EDIT_PIPELINE_WIZARD_STAGES_PAGE, 
		EDIT_PIPELINE_WIZARD_ADD_STAGE_POPUP, 
		EDIT_PIPELINE_WIZARD_EDIT_STAGE_PAGE,
		PIPELINE_WIZARD_JOB_LISTING_PAGE, 
		PIPELINE_WIZARD_JOB_EDIT_PAGE, 
		PIPELINE_WIZARD_TASK_LISTING_PAGE, 
		PIPELINE_WIZARD_TASK_EDIT_POPUP, 
		PIPELINE_WIZARD_ARTIFACTS_LISTING_PAGE, 
		PIPELINE_WIZARD_JOB_TABS_LISTING_PAGE, 
		EDIT_PIPELINE_WIZARD_MATERIAL_LISTING_PAGE, 
		EDIT_PIPELINE_WIZARD_MATERIAL_POPUP, 
		EDIT_STAGE_WIZARD_JOBS_PAGE, 
		EDIT_STAGE_WIZARD_ADD_JOB_POPUP, 
		EDIT_PIPELINE_GROUPS_TAB,
		ADD_NEW_PIPELINE_PAGE,
		EDIT_TEMPLATES_TAB,
		EDIT_PIPELINE_GROUP, 
		NEW_TEMPLATE_POPUP, 
		EDIT_TEMPLATE_PAGE, 
		NEW_PIPELINE_GROUP_POPUP, 
		ADD_NEW_PIPELINE_WIZARD, 
		COMPARE_PAGE_TIMELINE_POPUP, 
		CLONE_PIPELINE_POPUP,
		VIEW_TEMPLATE_POPUP,
		AGENT_DETAILS,
		AGENT_JOB_RUN_HISTORY, 
		STAGE_DETAIL_CONFIG_TAB, 
		BACKUP_SERVER_TAB, 
		PERFORM_BACKUP_POPUP, 
		EDIT_PIPELINE_GROUP_XML_TAB,
		SOURCE_XML_TAB,
		USER_ROLES_PAGE, 
		ADMIN_PLUGINS_PAGE,
		STAGE_CONFIG_CHANGES_POPUP, 
		PACKAGE_REPOSITORIES_PAGE,
		PACKAGE_DETAILS_PAGE,
		STAGE_DETAIL_TESTS_TAB, 
		PERMISSIONS_PAGE_FOR_TEMPLATE
	}
}
