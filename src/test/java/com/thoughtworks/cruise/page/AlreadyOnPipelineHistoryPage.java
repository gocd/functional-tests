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

package com.thoughtworks.cruise.page;

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Assert;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AlreadyOnPipelineHistoryPage extends CruisePage {
	private final TalkToCruise talkToCruise;
	private String currentLabel;
	
	public AlreadyOnPipelineHistoryPage(CurrentPageState currentPageState, ScenarioState scenarioState, TalkToCruise talkToCruise, Browser browser) {
		super(scenarioState, true, browser);
		this.talkToCruise = talkToCruise;
		currentPageState.assertCurrentPageIs(CurrentPageState.Page.PIPELINE_HISTORY);
	}

	@com.thoughtworks.gauge.Step("Looking at pipeline with label <currentLabel>")
	public void lookingAtPipelineWithLabel(String currentLabel) throws Exception {
		this.currentLabel = currentLabel;
	}

	public void triggerNewPipeline() throws Exception {
		browser.button("force-run-pipeline").click();
	}

	@com.thoughtworks.gauge.Step("Approve stage <stageName>")
	public void approveStage(String stageName) throws Exception {
		approveStageWithLabel(stageName, currentLabel());
	}

	@com.thoughtworks.gauge.Step("Approve stage <stageName> with label <label>")
	public void approveStageWithLabel(String stageName, String label) throws Exception {
		boolean javascriptAlertBoxesStillExistAndAreUntestableWithWebDriver = true; // that sucks.
		if (javascriptAlertBoxesStillExistAndAreUntestableWithWebDriver) {
	        String url = Urls.urlFor(String.format("/run/%s/%s/%s", scenarioState.currentRuntimePipelineName(), label, stageName));
	        System.err.println("posting to " + url);
	        CruiseResponse response = talkToCruise.post(url);
	        Assert.assertThat(String.format("Got back return code %s-%s from url %s", response.getStatus(), response.getBody(), url),
	        		response.isSuccess(), Is.is(true));
		} else {
			browser.button(String.format("approve-%s-%s", label, stageName)).click();
		}
	}

	private String currentLabel() {
		if (this.currentLabel == null) { throw new IllegalStateException("Must look at a particular pipeline instance"); }
		return this.currentLabel;
	}

	private void showBuildCauseMessage(final int row) {
		Assertions.waitUntil(Timeout.FIVE_SECONDS, new Predicate() {
			public boolean call() throws Exception {
				ElementStub pipelineRow = browser.row(row).in(browser.table("pipeline-history-group"));
				ElementStub link = browser.link(1).in(pipelineRow);
				link.click();
				String buildCauseMessage = getBuildCauseMessage(row);
				return !org.apache.commons.lang.StringUtils.isEmpty(buildCauseMessage);
			}
		});
	}

	private String getBuildCauseMessage(int row) {
		return browser.div("/build-cause-summary-container/").in(browser.row(row).in(browser.table("pipeline-history-group"))).getText();
	}

	@com.thoughtworks.gauge.Step("Verify build cause message on row <row> contains <text> and not <text2>")
	public void verifyBuildCauseMessageOnRowContainsAndNot(Integer row,	String text, String text2) throws Exception {
		showBuildCauseMessage(row);
		String msg = getBuildCauseMessage(row);
		org.junit.Assert.assertThat(msg, Matchers.containsString(scenarioState.expand(text)));
		org.junit.Assert.assertThat(msg, Matchers.not(Matchers.containsString(scenarioState.expand(text2))));
	}

	//TODO: Get rid of this stupid duplication
	@Override
	protected String url() {
		return browserWrapper.getCurrentUrl();
	}
	
	@com.thoughtworks.gauge.Step("Verify build cause message contains <shouldExist>")
	public void verifyBuildCauseMessageContains(String shouldExist) throws Exception {
		String msg = buildCauseText();	
		
		
//		String runtime_matcher = ".*(\\$\\{runtime_name:.*\\}).*";
//		String variable_matcher = ".*:(.*)\\}";
//		String replaceableVariable = "";
//		Pattern pattern = Pattern.compile(runtime_matcher);
//		Pattern variablePattern = Pattern.compile(variable_matcher);
//		Matcher matcher = pattern.matcher(shouldExist);
//		String stateVariable;
//		 
//		if (matcher.find())
//		{
//		    replaceableVariable = matcher.group(1);
//			Matcher stringMatcher = variablePattern.matcher(replaceableVariable);
//			System.out.println("below");
//			System.out.println(replaceableVariable);
//			stateVariable = "";
//			if(stringMatcher.find())
//			{
//				stateVariable = stringMatcher.group(1);	
//			}
//			
//			
//			System.out.println(stateVariable);
//			System.out.print("above");

		
		org.junit.Assert.assertThat(msg, Matchers.containsString(scenarioState.expand(shouldExist)));
			
		
	}
	
	public void verifyBuildCauseMessageDoesNotContain(String shouldNotExist) throws Exception {
		String msg = buildCauseText();
		org.junit.Assert.assertThat(msg, Matchers.not(Matchers.containsString(shouldNotExist)));
	}
	
	private String buildCauseText() {
		showBuildCauseMessage();
		return getBuildCauseMessage();
	}

	private String getBuildCauseMessage() {
		return browser.div("/build-cause-summary-container/").in(pipelineSelection()).getText();
	}

	private void showBuildCauseMessage() {
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			public boolean call() throws Exception {
				browser.link("/Triggered by/").in(pipelineSelection()).click();
				String buildCauseMessage = getBuildCauseMessage();
				return !StringUtils.isEmpty(buildCauseMessage);
			}
		});
	}
		
	private ElementStub pipelineSelection() {
		return browser.tableHeader("/^" + currentLabel + "/");
	}

	@com.thoughtworks.gauge.Step("Verify on pipeline history page for <pipelineName>")
	public void verifyOnPipelineHistoryPageFor(String pipelineName) throws Exception {
		assertThat(browserWrapper.getCurrentUrl(), containsString(scenarioState.pipelineNamed(pipelineName)));
	}

	@com.thoughtworks.gauge.Step("Open changes section for counter <counter> - Already on pipeline history page")
	public void openChangesSectionForCounter(final String counter) throws Exception {
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
            @Override
            public boolean call() throws Exception {
                ElementStub showChangesElement = browser.link("Triggered by view").near(browser.link(counter));
                if (!showChangesElement.exists())
                    return false;
                showChangesElement.click();
                return true;
            }
        });
	}
        
      @com.thoughtworks.gauge.Step("Verify stage <stageName> of pipeline can be rerun")
	public void verifyStageOfPipelineCanBeRerun(final String stageName){
    	  Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate(){
    		  @Override
    		  public boolean call() throws Exception {
    			  String pipelineName = scenarioState.currentRuntimePipelineName();
    			  return browser.byId("rerun-"+pipelineName+"-"+currentLabel()+"-"+stageName).exists();
    		  }
    	  });
      }
      
      
      @com.thoughtworks.gauge.Step("Verify stage <stageName> of pipeline cannot be rerun")
	public void verifyStageOfPipelineCannotBeRerun(String stageName){
    	  String pipelineName = scenarioState.currentRuntimePipelineName();
          assertTrue("Rerun link found for run "+currentLabel()+" for pipeline "+pipelineName, !browser.byId("rerun-"+pipelineName+"-"+currentLabel()+"-"+stageName).exists());  	  
      }

	@com.thoughtworks.gauge.Step("Rerun stage <stageName> - Already On Pipeline History Page")
	public void rerunStage(final String stageName) throws Exception{
		String pipelineName = scenarioState.currentRuntimePipelineName();
		browser.byId("rerun-"+pipelineName+"-"+currentLabel()+"-"+stageName).click();
	}


	@com.thoughtworks.gauge.Step("Verify <stageName> stage can be cancelled")
	public void verifyStageCanBeCancelled(final String stageName) throws Exception {
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate(){
			@Override
			public boolean call() throws Exception {
				String pipelineName = scenarioState.currentRuntimePipelineName();
				return browser.byId("cancel-"+pipelineName+"-"+currentLabel()+"-"+stageName).exists();
			}
		});
	}
	
	@com.thoughtworks.gauge.Step("Cancel stage <stageName>")
	public void cancelStage(String stageName){
		 String pipelineName = scenarioState.currentRuntimePipelineName();
	  	 browser.byId("cancel-"+pipelineName+"-"+currentLabel()+"-"+stageName).click();
	}

	@com.thoughtworks.gauge.Step("Pause pipeline on activity page")
	public void pausePipelineOnActivityPage() throws Exception {
		browser.byId("pause-"+scenarioState.currentRuntimePipelineName()).click();
	}

	@com.thoughtworks.gauge.Step("Verify pipeline is paused on pipeline activity page")
	public void verifyPipelineIsPausedOnPipelineActivityPage() throws Exception {
		reloadPage();
		assertEquals("Pipeline not paused", "unpause", browser.byId("pause-"+scenarioState.currentRuntimePipelineName()).getText().toLowerCase());  	  
        
	}

	@com.thoughtworks.gauge.Step("Unpause pipeline on pipeline activity page")
	public void unpausePipelineOnPipelineActivityPage() throws Exception {
		browser.byId("pause-"+scenarioState.currentRuntimePipelineName()).click();
		reloadPage();
	    
	} 
	
	@com.thoughtworks.gauge.Step("Verify pipeline cannot be paused")
	public void verifyPipelineCannotBePaused() throws Exception {
		Assert.assertThat(!browser.byId("pause-"+scenarioState.currentRuntimePipelineName()).exists(),Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Verify <stageName> can be approved")
	public void verifyCanBeApproved(final String stageName) throws Exception {
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            @Override
            public boolean call() throws Exception {
                ElementStub approveElement = browser.byId("approve-"+currentLabel()+"-"+stageName);
                if (approveElement.exists())
                    return true;
                else
                	return false;
            }
        });
		
	}

	@com.thoughtworks.gauge.Step("Verify <stageName> cannot be approved")
	public void verifyCannotBeApproved(final String stageName) throws Exception {
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            @Override
            public boolean call() throws Exception {
                ElementStub approveElement = browser.byId("approve-"+currentLabel()+"-"+stageName);
                if (approveElement.exists())
                    return false;
                else
                	return true;
            }
        });
		
	}

	@com.thoughtworks.gauge.Step("Verify pipeline is triggered by <user>")
	public void verifyPipelineIsTriggeredBy(final String user) throws Exception {
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
			@Override
			public boolean call() throws Exception {
				return browser.link("/Triggered by/").in(pipelineSelection()).getText().equals("Triggered by "+user);
			}
			
		});
		
	}

	public void waitForStageToPass(final String stageName) throws Exception {
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate(){
			@Override
			public boolean call() throws Exception {
				reloadPage();
				return browser.div("passed-stage").in(browser.byId("stage-detail-"+currentLabel()+"-"+stageName)).exists();
				
				
			}
		});
	}
	
	
	
}
      
      
      
      
