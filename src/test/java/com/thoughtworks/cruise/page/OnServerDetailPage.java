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

import com.thoughtworks.cruise.CruiseAgents;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.api.UsingAgentsApi;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class OnServerDetailPage extends CruisePage {
    public static final String URL = "/about";

    public static final Map<String, License> licenses = new HashMap<String, License>() {
        {
            put("enterprise", new License("Cruise team internal", "E0TCr1FPWPOfh+yfApnYqUkqgNDbByvoWnaql4QvzgOj4Y8kfKCpM9GzOEQz"
                    + "WAR/8e0RmcziTKT2/IqKD8zUd1n4hFkUeHn9x9Lne1krwgBRLdqH44gkZa7s"
                    + "O87bQY/U+jpUF8J53z9blLtrpbvJTlnXq+CPatFV5YWKb6RMhZDN1JBjIg1h"
                    + "kFtCSEXGs+M2m51TC/dFKO3xAB3TpuSSltLbzfpjArdbnawglXKgEy5x72I5"
                    + "jnZuqHroZU9tL0uvp+t8l9mpsWQnFy1Fo4qL+wA7UETCRF+ugDJIPZBPx5eb" 
                    + "wEnzXtpMspeFVWJfYXqzmV3uJg7XyUJmQajT/mdXDw=="));
            put("two-users", new License("Go Test", "Jfv1UxL9k2YjmWkkWD2yUFLW5aH1Y5WPn7iRmQVfhRt30nmwqXcDkf2rWzvX"
                    + "Ux+uNqkEQoetkZNtXFoa5RStdRTHUziNKou4saASDL+XxsqHK3Y1qFMBlUDG"
                    + "fUllHlekKnaSWJUghvDNyDzjPdoHCn1MhI7dhVQMroYMuT0vWv517OZ1cgJe"
                    + "uNvuIPX6yZsCkEgP7CXfxHhhNZ4HAk+P7b9VgWLaf0qQDbwx4ncLl208UuGw"
                    + "iAEBa6VC6gEjTw94nz7ClvBxjLZuSidDcA2TwfRs4jBsNAEGweQ63a2bAupK" 
                    + "GdjHOExONsBDcMvgauJOCaEJiX2VBJDwBbGYpE+Qvw=="));
            put("five-users", new License("Go Test 5", "a5zuLn8Ypeq21QeOnZSnUbYfsNPm/j925m9cUBe6MNiE8DKg/Ud+MQbweiAu"
                    + "txXTYdWJKtLDMsATA9WGf3+a6Tcugigq+qSY34/S8W74ZDA8JDA4+Jp1o3af"
                    + "ogv9XviISo5bRXw7VQ4C8s9oYBV7Apfto822GrJXbOb94IWw5Pw14oMQZ1wn"
                    + "6Kg8/u19v63jfvmPk+At3J/PjU98G02HIQ+vZWnAee42gCqQIGQnk6p25sDJ"
                    + "2GTtF4SgnKYg9DXjQ8uumuMs21rf7FvTn5AbhJvI2DRm7OGDoXmTjrKl6xIi" 
                    + "VITg8WqDoAIinIl1C3OHFjnR0UPuFtawx7qcsNBkHg=="));
            put("expired-enterprise", new License("Go", "ldTnrBI3LPQxi88YeQESf5Y6Z4Wb8M7KjFpSvjIVzgE75K0gVgfr6H5ea7x2"
                    + "Sa2RhA5C/lmOGanH6e/r9o9FL4hAYfywad/CvTTPJLERpHQFkJLE5MozmZv3"
                    + "SDSqaIRIa/0Jq17rcfKvY2/rn+arVIdcVoGPJj1Nfo8XMHrUDjW60CQTruB4"
                    + "SMrUJ1xE/LMbkbvA3ECs4rOnKiLo4dbjcJpO27OhjZ/+G6at6/JvGPs+Djne"
                    + "zC6evyzXrJd1QvA+ksBrXq1NkcPfUbn0BhW8L7vEw6+IOnB/ZGxWgmE6Mod5"
                    + "yCZ1oAnOX0zX4SeybmWaOj7XEdfAIqgCSGOxNrxMjg=="));
            put("expired-remote-agents-community", new License("Go", "SiO4z2aZMygvxVM9esZLAoVm3SzW2oaeeft+BYbsdNiFgec5fROLIGqEHH5A"
                    + "NIOlo1oveE9IuSpA0dZkIt7meq244SXRnwLQhBONjwpkHEpzYUPjFldXRMKw"
                    + "XNrY2ogul43RlqQE0HIRwPTQnDG2/FlamOCGoJdAkvVIsmYTnm+lH4LpesrS"
                    + "sbUyvfGd8w61Go10+mDhNaqzpSlTSq2Vz1Spm05ddRMsXRw/IN2hOC6fpD5x"
                    + "PL+2tDBp27HzbWFgUJFy2L1fvGhj6ZjIFp/HOIkTlnEUyf0zsRMQofEnewnE"
                    + "Lq8zsnoMMvvia7sS/p6vWZ2gbGpor/TSSBm8ogq7nQ=="));
            put("invalid-license", new License("Go-invalid-license", "InvalidLicenseKeyForGo "));
        }
    };

    public OnServerDetailPage(ScenarioState state, UsingAgentsApi agentsApi, CruiseAgents createdAgents, Browser browser) {
        super(state, browser);
    }
    
    public OnServerDetailPage(ScenarioState state, UsingAgentsApi agentsApi, CruiseAgents createdAgents, Browser browser, boolean alreadyOn) {
        super(state, alreadyOn, browser);
    }

    @Override
    protected String url() {
        return Urls.urlFor(URL);
    }

	public void save() {
		browser.byId("save").click();
	}

    public static class License {
        final String user;
        final String key;

        public License(String user, String key) {
            this.user = user;
            this.key = key;
        }
    }

    @com.thoughtworks.gauge.Step("Verify cruise footer - On Server Detail Page")
	@Override
    public void verifyCruiseFooter() throws Exception {
        super.verifyCruiseFooter();
    }

	public void verifyAllowsUsers(Integer users) throws Exception {
		assertValueOfSection("Number of users Go is licensed for:", users);
	}
	
	public void verifyAllowsRemoteAgents(Integer numberOfAgents) throws Exception {
		assertValueOfSection("Number of remote agents Go is licensed for:", numberOfAgents);
	}
	
	public void verifyValidTillEternity() throws Exception {
		assertValueOfSection("License expiry date:", "9999-12-31");
	}

	public void verifyUsingEdition(String edition) throws Exception {
	    assertValueOfSection("Go edition:", edition);
	}
	
	private void assertValueOfSection(String column, final Object value) {
		ElementStub row = browser.cell(column).parentNode();
		ElementStub valueNode = browser.cell("value_column").in(row);
		Assert.assertThat(valueNode.text(), Is.is(value.toString()));
	}

}
