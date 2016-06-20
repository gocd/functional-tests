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

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.state.CurrentUsernameProvider;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.CruiseConstants;
import com.thoughtworks.cruise.util.XmlUtil;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;
import org.apache.commons.httpclient.NameValuePair;
import org.bouncycastle.util.encoders.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.hamcrest.core.Is;
import org.xml.sax.SAXException;

import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertThat;

public class ConfigureCruiseUsingApi {

	public static final String GO_CONFIG_API_URL = Urls.urlFor("/api/admin/config.xml");
	private String groupName;
	private final TalkToCruise talkToCruise;
	private final ScenarioState scenarioState;
	private int responseStatus;
	private com.thoughtworks.cruise.context.Configuration configuration;
	private String templateName;
	private String stageName;
	private String jobName;
    private boolean useCurrentUser = false;

	private interface PipelineGroupEditAction {
		public void applyEdit(Document group);
	}
	
	public ConfigureCruiseUsingApi(ScenarioState scenarioState, TalkToCruise talkToCruise, com.thoughtworks.cruise.context.Configuration configuration) {
		this.talkToCruise = talkToCruise;
		this.scenarioState = scenarioState;
		this.configuration = configuration;
	}

	@com.thoughtworks.gauge.Step("For pipeline group <groupName>")
	public void forPipelineGroup(String groupName) throws Exception {
		this.groupName = groupName;
	}

	@com.thoughtworks.gauge.Step("Making user <username> admin")
	public void makingUserAdmin(final String username) throws Exception {
		editPipelineGroup(new PipelineGroupEditAction() {
			
			@Override
			public void applyEdit(Document group) {
				addUserAsAdmin(group, username);			}
		});
	}


    private void editPipelineGroup(PipelineGroupEditAction action) throws DocumentException, SAXException,
            URISyntaxException {

        CruiseResponse response  = getConfigXML();

        if (!response.isSuccess())
		{
			responseStatus =  response.getStatus();
			return;
		}

        String md5 = response.getResponseHeader("X-CRUISE-CONFIG-MD5");
        Document group = XmlUtil.parse(response.getBody());

        action.applyEdit(group);

        NameValuePair XMLFile = new NameValuePair("xmlFile", group.asXML());
        NameValuePair md5Pair = new NameValuePair("md5", md5);
        response = postConfigXML(XMLFile, md5Pair);
        responseStatus =  response.getStatus();

    }

    private CruiseResponse postConfigXML(NameValuePair... nameValuepairs){

        if(this.useCurrentUser){
            this.useCurrentUser=false;
            return new TalkToCruise(new CurrentUsernameProvider() {

                @Override
                public String loggedInUser() {
                    return scenarioState.loggedInUser();
                }
            }).post(GO_CONFIG_API_URL, nameValuepairs);
        }else {

            return new TalkToCruise(new CurrentUsernameProvider() {

                @Override
                public String loggedInUser() {
                    return scenarioState.loggedInUser() != null ? "admin" : null;
                }
            }).post(GO_CONFIG_API_URL, nameValuepairs);
        }
    }

    private CruiseResponse getConfigXML() throws DocumentException, SAXException, URISyntaxException {

        return new TalkToCruise(new CurrentUsernameProvider() {

            @Override
            public String loggedInUser() {
                return scenarioState.loggedInUser() != null ? "admin" : null;
            }
        }).get(GO_CONFIG_API_URL);

    }

	private void addUserAsAdmin(Document group, String username) {
		Element auth = (Element) group.selectSingleNode("//authorization");
		if (auth == null) {
			Element element = new DefaultElement("authorization");
			element.add(admins(username));
		} else if (auth.selectSingleNode(".//admins") != null) {
			user(username, (Element) auth.selectSingleNode(".//admins"));
		} else {
			auth.add(admins(username));
		}
	}

	private CruiseResponse getGroupXML(NameValuePair groupNameValur) {
		CruiseResponse response = talkToCruise.get(Urls.adminGroup("GET"), groupNameValur);
//		if (!response.isSuccess()) {
//			throw new RuntimeException(String.format("Could not get the group '%s'. Got response '%s' from the server. The body is '%s'", groupName, response.getStatus(), groupName));
//		}
		return response;
	}

	private Element admins(String username) {
		Element admins = new DefaultElement("admins");
		user(username, admins);
		return admins;
	}

	private void user(String username, Element admins) {
		DefaultElement user = new DefaultElement("user");
		user.setText(username);
		admins.add(user);
	}

	public void removingUserFromBeingAnAdmin(final String username) throws Exception {
		editPipelineGroupAdminsAdmin(username, "user");
	}

	private void editPipelineGroupAdminsAdmin(final String username, final String entity) throws Exception {
		editPipelineGroup(new PipelineGroupEditAction() {
			
			public void applyEdit(Document group) {
				List<Element> users = group.selectNodes("//authorization//admins//" + entity);
				for (Element user : users) {
					if (user.getTextTrim().equals(username)) {
						user.detach();
					}
				}
			}
		});
	}
	

	@com.thoughtworks.gauge.Step("Removing role <roleName> from being an admin")
	public void removingRoleFromBeingAnAdmin(String roleName) throws Exception {
		editPipelineGroupAdminsAdmin(roleName, "role");
	}

	@com.thoughtworks.gauge.Step("Create pipeline <pipelineName> using template <templateName>")
	public void createPipelineUsingTemplate(final String pipelineName, final String templateName) throws Exception {
		editPipelineGroup(new PipelineGroupEditAction() {
			
			public void applyEdit(Document group) {
				Element pipelines = (Element) group.selectSingleNode("//pipelines");
				Element pipeline = new DefaultElement("pipeline");
				pipeline.addAttribute("name", pipelineName);
				pipeline.addAttribute("template", templateName);
				DefaultElement materials = new DefaultElement("materials");
				DefaultElement hg = new DefaultElement("hg");
				hg.addAttribute("url","some-url");
				materials.add(hg);
				pipeline.add(materials);			
				pipelines.add(pipeline);
				scenarioState.pushPipeline(pipelineName, pipelineName);		
			}
		});
	}

	@com.thoughtworks.gauge.Step("Delete pipeline <pipelineName> - Configure cruise using api")
	public void deletePipeline(final String pipelineName) throws Exception {
		editPipelineGroup(new PipelineGroupEditAction() {
			
			public void applyEdit(Document group) {
				Element pipelineToRemove = (Element) group.selectSingleNode(String.format("//pipelines/pipeline[@name='%s']",scenarioState.pipelineNamed(pipelineName)));
				Element pipelines = (Element) group.selectSingleNode("//pipelines");
				pipelines.remove(pipelineToRemove);				
			}
		});
	}	
	
	@com.thoughtworks.gauge.Step("Response should return code <responseStatus>")
	public void responseShouldReturnCode(Integer responseStatus) throws Exception {
		assertThat(this.responseStatus, Is.is(responseStatus));
	}
	
	@com.thoughtworks.gauge.Step("Create pipeline <pipelineName> as first pipeline in group <groupName>")
	public void createPipelineAsFirstPipelineInGroup(final String pipelineName, final String groupName) throws Exception {		
		CruiseConfigDom dom = configuration.provideDomAsAdmin();
		dom.createPipelineAsFirstPipelineInGroup(pipelineName, groupName);	
		configuration.setDom(dom);
    }	
	
	@com.thoughtworks.gauge.Step("Adding pipeline <pipelineName> to <environmentName> environment - Configure cruise Using Api")
	public void addingPipelineToEnvironment(final String pipelineName, final String environmentName) throws Exception {
        CruiseConfigDom dom = configuration.provideDomAsAdmin();
        dom.addPipeLineToEnvironment(scenarioState.pipelineNamed(pipelineName), environmentName);
        configuration.setDom(dom);
    } 
	
	@com.thoughtworks.gauge.Step("Rename pipeline <originalName> to <newName>")
	public void renamePipelineTo(final String originalName, final String newName) throws Exception {
		editPipelineGroup(new PipelineGroupEditAction() {
			@Override
			public void applyEdit(Document group) {
				Element pipelineToRename = (Element) group.selectSingleNode(String.format("//pipelines/pipeline[@name='%s']",scenarioState.pipelineNamed(originalName)));
				pipelineToRename.addAttribute("name", newName);
			}
		});
	}

	@com.thoughtworks.gauge.Step("For template <templateName>")
	public void forTemplate(String templateName) throws Exception {
		this.templateName = templateName;
	}

	@com.thoughtworks.gauge.Step("For stage <stageName>")
	public void forStage(String stageName) throws Exception {
		this.stageName = stageName;
	}

	@com.thoughtworks.gauge.Step("For job <jobName>")
	public void forJob(String jobName) throws Exception {
		this.jobName = jobName;
	}

		
	@com.thoughtworks.gauge.Step("Add custom command <given>")
	public void addCustomCommand(String given) throws Exception {
		String [] commandWithArguement = given.split(":");
		String command = commandWithArguement[0];
		String arguement = hasArguement(commandWithArguement) ? commandWithArguement[1] : "";
		CruiseConfigDom dom = configuration.provideDom();
		dom.addCustomCommandTaskWithInATemplate(templateName,stageName,jobName, command,arguement);
		configuration.setDom(dom);
	}

	private boolean hasArguement(String[] commandWithArguement) {
		// TODO Auto-generated method stub
		return commandWithArguement.length > 1 ? true : false;
	}

	@com.thoughtworks.gauge.Step("Remove custom command <given>")
	public void removeCustomCommand(String given) throws Exception {
		String [] commandWithArguement = given.split(":");
		String command = commandWithArguement[0];
		String arguement = hasArguement(commandWithArguement) ? commandWithArguement[1] : "";
		CruiseConfigDom dom = configuration.provideDom();
		dom.removeCustomCommandTaskWithInATemplate(templateName,stageName,jobName, command,arguement);
		configuration.setDom(dom);
	}
	
	@com.thoughtworks.gauge.Step("Update value of key <key> to <value> for repo with id <repoId>")
	public void updateValueOfKeyToForRepoWithId(final String key, final String value, final String repoId) throws Exception {
		CruiseConfigDom dom = configuration.provideDom();
		dom.updateValueOfKeyToForRepoWithId(key, value, repoId);		
		configuration.setDom(dom);
	}
	
	public void deleteRepoWithId(final String repoId) throws Exception {
		CruiseConfigDom dom = configuration.provideDom();
        dom.deletePackageRepo(repoId);
		configuration.setDom(dom);
	}

	@com.thoughtworks.gauge.Step("Add a new package definition with <packageId> and spec <packageSpec> to repository <repoId>")
	public void addANewPackageDefinitionWithAndSpecToRepository(final String packageId, final String packageSpec, final String repoId){
		CruiseConfigDom dom = configuration.provideDom();
        dom.addANewPackageDefinitionToRepository(packageId, packageSpec, repoId);
		configuration.setDom(dom);
	}

	@com.thoughtworks.gauge.Step("Add package material <packageId> to pipeline <pipelineName>")
	public void addPackageMaterialToPipeline(final String packageId, final String pipelineName){
		CruiseConfigDom dom = configuration.provideDom();
        dom.addPackageMaterialToPipeline(packageId, scenarioState.pipelineNamed(pipelineName));
		configuration.setDom(dom);
	}

	@com.thoughtworks.gauge.Step("Invert file filter for pipeline <pipelineName>")
	public void invertFileFilterForPipeline(final String pipelineName){
		CruiseConfigDom dom = configuration.provideDom();
		dom.invertFileFilter(scenarioState.pipelineNamed(pipelineName));
		configuration.setDom(dom);
	}

    @com.thoughtworks.gauge.Step("As current User")
    public void setUseCurrentUser(){
        this.useCurrentUser=true;

    }
}
