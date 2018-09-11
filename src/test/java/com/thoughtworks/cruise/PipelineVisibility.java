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

package com.thoughtworks.cruise;

import com.thoughtworks.cruise.api.UsingPipelineApi;
import com.thoughtworks.cruise.page.OnPipelineDashboardPage;
import com.thoughtworks.cruise.page.UsingPipelineDashboardAPI;
import net.sf.sahi.client.Browser;

public class PipelineVisibility {

    private String pipelineName;

    private final OnPipelineDashboardPage dashboardPage;
    private final UsingPipelineDashboardAPI dashboardApi;

    private final UsingPipelineApi usingPipelineApi;

    public PipelineVisibility(Browser browser, OnPipelineDashboardPage dashboardPage, UsingPipelineApi usingPipelineApi, UsingPipelineDashboardAPI dashboardApi) {
        this.dashboardPage = dashboardPage;
        this.usingPipelineApi = usingPipelineApi;
        this.dashboardApi = dashboardApi;
    }

    public void setPipelineName(String pipelineName) throws Exception {
        this.pipelineName = pipelineName;
        dashboardApi.lookingAtPipeline(pipelineName);
        usingPipelineApi.forPipeline(pipelineName);
    }

    public void setUp() throws Exception {
        dashboardPage.open();
    }

    public void tearDown() throws Exception {
        dashboardPage.open();
    }

    public String isVisible() throws Exception {
        try {
            dashboardApi.verifyPipelineIsVisible(pipelineName);
        } catch (AssertionError failure) {
            return "false";
        }
        return "true";
    }

    public String canOperate() throws Exception {
        try {
            usingPipelineApi.scheduleShouldReturnCode(202);
        } catch (AssertionError failure) {
            return "false";
        } catch (RuntimeException e) {
            return "false";
        }
        return "true";
    }
    
    public String canOperateUsingUi() throws Exception{
        
        try {
            dashboardApi.verifyTriggerButtonIsPresent();
            dashboardApi.verifyTriggerWithOptionsButtonIsPresent();
		} catch (AssertionError failure) {			
			return "false";
		} catch (Exception e) {			
			return "false";
		}
        return "true";

    }

    public String canPauseUsingApi() throws Exception{
        try {
            usingPipelineApi.attemptToPausePiplineWithCauseAndShouldReturnWithHttpStatus(pipelineName,"Pausing",200);
            usingPipelineApi.attemptToUnpausePipelineAndShouldReturnWithHttpStatus(pipelineName, 200);
        } catch (AssertionError failure) {
            return "false";
        } catch (Exception e) {
            return "false";
        }
        return "true";
    }

    public String canPauseUsingUi() throws Exception {
        
        try {
            dashboardApi.pausePipelineWithReason("Pausing through UI");
            dashboardApi.verifyPipelineIsPausedWithReason("Pausing through UI");
            dashboardApi.unpausePipeline();
            dashboardApi.verifyPipelineIsUnpaused();
		} catch (AssertionError failure) {			
			return "false";
		} catch (Exception e) {			
			return "false";
		}
        return "true";
    }

	@com.thoughtworks.gauge.Step("PipelineVisibility <table>")
	public void brtMethod(com.thoughtworks.gauge.Table table) throws Throwable {
		com.thoughtworks.twist.migration.brt.BRTMigrator brtMigrator = new com.thoughtworks.twist.migration.brt.BRTMigrator();
		try {
			brtMigrator.BRTExecutor(table, this);
		} catch (Exception e) {
			throw e.getCause();
		}
	}

}
