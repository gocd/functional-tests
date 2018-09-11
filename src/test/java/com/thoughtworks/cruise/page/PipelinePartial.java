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

//import org.openqa.selenium.By;

import com.thoughtworks.cruise.SahiBrowserWrapper;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Function;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

public class PipelinePartial {

	private final ScenarioState scenarioState;
	private final CruisePage page;
	private final Browser browser;

	public PipelinePartial(ScenarioState scenarioState, CruisePage page, Browser browser) {
		this.scenarioState = scenarioState;
		this.page = page;
		this.browser = browser;
	}

	public void verifyCannotTriggerWithOptions(){
		Assertions.assertOverTime(Timeout.TEN_SECONDS, new Function<Boolean>(){
			@Override
			public Boolean call() {
				return !new SahiBrowserWrapper(browser).isEnabled(elementTriggerWithOptions());
			}

		});
	}

	public void openTriggerWithOptions() {
		Assertions.waitUntil(Timeout.TEN_SECONDS, new Predicate() {
			@Override
			public boolean call() throws Exception {
				page.reloadPage();
				ElementStub element = elementTriggerWithOptions();
				if (!new SahiBrowserWrapper(browser).isEnabled(element)) {
					return false;
				}
				element.click();
				return true;
			}

		});
		Assertions.waitUntil(Timeout.TEN_SECONDS, new Predicate() {
			@Override
			public boolean call() throws Exception {
				return browser.byId("MB_caption").getText().contains("Trigger");
			}
		});
	}

	private ElementStub elementTriggerWithOptions() {
		return browser.submit(String.format("deploy-with-options-%s", scenarioState.currentRuntimePipelineName()));
	}
}