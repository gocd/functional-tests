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

import com.thoughtworks.cruise.config.ConfigModifier;
import com.thoughtworks.cruise.config.ModifyCommand;
import com.thoughtworks.cruise.config.RetryOnConflictModifier;
import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.page.OnAgentsPage;
import com.thoughtworks.cruise.preconditions.AgentLauncher;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.FileUtil;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;
import org.apache.commons.io.FileUtils;
import org.dom4j.Element;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class ConfigureCruiseBy {

	private static final String ORIGINAL_LDAP_URI = "original_ldap_uri";
    private final ScenarioState scenarioState;
	private Configuration configuration;
	private final OnAgentsPage agentsPage;
	private static final String CONTANT_SPEC = "* * * * * ? *";
	private final CruiseAgents createdAgents;
	private final RepositoryState repositoryState;
    private ConfigModifier modifier;
	private String rememberedUrl;
    private static String TMP_FILE_SUFFIX = UUID.randomUUID().toString();

	public ConfigureCruiseBy(Configuration configuration, ScenarioState scenarioState, OnAgentsPage agentsPage, CruiseAgents createdAgents, RepositoryState repositoryState) {
		this.scenarioState = scenarioState;
		this.configuration = configuration;
		this.agentsPage = agentsPage;
		this.createdAgents = createdAgents;
		this.repositoryState = repositoryState;
		modifier = new RetryOnConflictModifier(configuration);
	}
	
	public void changingApprovalTypeForPipelineStageTo(final String pipelineName, final String stageName, final String triggerType) {
	    final String dynName = scenarioState.pipelineNamed(pipelineName);
	    modifier.modifyConfig(new ModifyCommand() {
            @Override
            public void modify(CruiseConfigDom dom) {
                dom.setApprovalTypeForStage(dynName, stageName, triggerType);
            }
	        
	    });
	}
	
	public void removingEnvironments() {
	    modifier.modifyConfig(new ModifyCommand() {
            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removeEnvironments();
            }
	    });
	}
	
	public void addingResourceToAllAgents(final String resource) throws Exception {
	    modifier.modifyConfig(new ModifyCommand() {
            
            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addResourceToAllAgents(resource);
            }
        });
	}
	
	public void denyFirstAgent() {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.denyOneAgent();
            }
        });
	}
	
    public void undenyFirstAgent() {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.undenyOneAgent();
            }
        });
    }

	private CruiseConfigDom getDom() {
		return configuration.provideDom();
	}		

	private void writeToConfigFileOnFileSystem(CruiseConfigDom dom) {
    	configuration.setDomOnFileSystem(dom);		
	}
	
    @com.thoughtworks.gauge.Step("Assigning <numberOfAgents> agents with resource <resource> to environment <environment>")
	public void assigningAgentsWithResourceToEnvironment(final int numberOfAgents, final String resource, final String environment) throws Exception {
        agentsPage.open();
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                List<String> idleAgentUuids = agentsPage.idleAgentUuids();
                ConfigureCruiseBy.this.scenarioState.addAgentsByEnvironment(
                        dom.addAgentsToEnvironment(numberOfAgents, resource, environment, idleAgentUuids), environment);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Adding resource <resource> to the job <job>")
	public void addingResourceToTheJob(final String resource, final String job) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addResourceToJob(resource, job);
            }
        });
    }

    public void setRunOnAllAgentsForJobTo(final String pipelineName, final String jobName, final boolean runOnAllAgents) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.setRunOnAllAgentsForJobTo(pipelineName, jobName, runOnAllAgents);
            }
        });
    }
    
	public void setRunInstanceCountForJobTo(final String pipelineName, final String jobName, final int runInstanceCount) throws Exception {
		modifier.modifyConfig(new ModifyCommand() {
			@Override
			public void modify(CruiseConfigDom dom) {
				dom.setRunInstanceCountForJobTo(pipelineName, jobName, runInstanceCount);
			}
		});
	}

    @com.thoughtworks.gauge.Step("Using timer with spec <spec>")
	public void usingTimerWithSpec(final String spec) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.setTimerWithSpec(currentPipeline(), spec);
            }
        });
    }
	
    @com.thoughtworks.gauge.Step("Using timer with invalid spec <spec>")
	public void usingTimerWithInvalidSpec(final String spec) throws Exception {
        CruiseConfigDom dom = configuration.provideDom();
        dom.setTimerWithSpec(currentPipeline(), spec);
        writeToConfigFileOnFileSystem(dom);
    }

    @com.thoughtworks.gauge.Step("Adding pipeline <pipelineName> to <environmentName> environment")
	public void addingPipelineToEnvironment(final String pipelineName, final String environmentName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addPipeLineToEnvironment(scenarioState.pipelineNamed(pipelineName), environmentName);
            }
        });
    }

	public void usingConstantlyBuildingTimer() throws Exception {
		usingTimerWithSpec(CONTANT_SPEC);
	}
	
	public void configureTimerToTriggerPipelineMinutesFromNow(int deltaMinutes) throws Exception {
		Calendar calendar = GregorianCalendar.getInstance();
		int currentMinutes = calendar.get(Calendar.MINUTE);
		int minute = (currentMinutes + deltaMinutes) % 60;
		usingTimerWithSpec(String.format("0 %s * * * ?", minute));
	}
	
	protected String currentPipeline() {
		return scenarioState.currentPipeline();
	}
	
    @com.thoughtworks.gauge.Step("Assigning agent <agentIndex> to environment <environment>")
	public void assigningAgentToEnvironment(final Integer agentIndex, final String environment) {
        final AgentLauncher agent = createdAgents.get(agentIndex);
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addAgentToEnvironment(agent.getUuid(), environment);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Assigning <numberOfAgents> agents to environment <environment>")
	public void assigningAgentsToEnvironment(final Integer numberOfAgents, final String environment) {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                List<String> idleAgentUuids = agentsPage.idleAgentUuids();
                scenarioState.addAgentsByEnvironment(dom.addAgentsToEnvironment(numberOfAgents, environment, idleAgentUuids), environment);
            }
        });
    }

    public void removeTimer() {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removeTimer(currentPipeline());
            }
        });
    }

	@com.thoughtworks.gauge.Step("Setting first stage to auto approval")
	public void settingFirstStageToAutoApproval() throws Exception {
		settingFirstStageOfToAutoApproval(scenarioState.currentPipeline());
	}
	
    @com.thoughtworks.gauge.Step("Add environment variable <name> with value <value> to environment <env>")
	public void addEnvironmentVariableWithValueToEnvironment(final String name, final String value, final String env) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addVariableToEnvironment(name, value, env);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Add environment variable <name> with value <value> to job <env>")
	public void addEnvironmentVariableWithValueToJob(final String name, final String value, final String env) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addVariableToJob(name, value, env);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Add environment variable <name> with value <value> to pipeline <pipeline>")
	public void addEnvironmentVariableWithValueToPipeline(final String name, final String value, final String pipeline) throws Exception {
    	modifier.modifyConfig(new ModifyCommand() {
    		
    		@Override
    		public void modify(CruiseConfigDom dom) {
    			dom.addVariableToPipeline(name, value, scenarioState.pipelineNamed(pipeline));
    		}
    	});
    }
    @com.thoughtworks.gauge.Step("Add environment variable <name> with value <value> to stage <stage>")
	public void addEnvironmentVariableWithValueToStage(final String name, final String value, final String stage) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addVariableToStage(name, value, stage);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Remove pipeline <pipelineName> from environment")
	public void removePipelineFromEnvironment(final String pipelineName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removePipelineFromEnvironment(scenarioState.pipelineNamed(pipelineName));
            }
        });
    }

    @com.thoughtworks.gauge.Step("Setting first stage of <pipelineName> to auto approval")
	public void settingFirstStageOfToAutoApproval(final String pipelineName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.setFirstStageOfPipelineToAutoApproval(scenarioState.pipelineNamed(pipelineName));
            }
        });
    }

    @com.thoughtworks.gauge.Step("Removing <materialName> material from <pipelineName>")
	public void removingMaterialFrom(final String materialName, final String pipelineName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removeMaterialFromPipeline(materialName, scenarioState.pipelineNamed(pipelineName));
            }
        });
    }

    @com.thoughtworks.gauge.Step("Changing destination of <materialName> material of <pipelineName> to <newDestination>")
	public void changingDestinationOfMaterialOfTo(final String materialName, final String pipelineName, final String newDestination)
            throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {

                dom.changeDestinationOfMaterial(materialName, scenarioState.pipelineNamed(pipelineName), newDestination);
            }
        });
    }

    public void changingUrlOfMaterialOfTo(final String materialName, final String pipelineName, final String newUrl) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {
            @Override
            public void modify(CruiseConfigDom dom) {
                dom.changeUrlOfMaterial(materialName, scenarioState.pipelineNamed(pipelineName), newUrl);
            }
        });
    }

    public void removingLicense() throws Exception {
        CruiseConfigDom dom = configuration.provideDom();
        dom.removeLicense();
        writeToConfigFileOnFileSystem(dom);
    }

    @com.thoughtworks.gauge.Step("Making pipeline <pipelineName> auto update")
	public void makingPipelineAutoUpdate(final String pipelineName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.pipelineAutoUpdate(scenarioState.pipelineNamed(pipelineName));
            }
        });
    }

    @com.thoughtworks.gauge.Step("Enable auto update for pipeline <pipelineName>")
	public void enableAutoUpdateForPipeline(final String pipelineName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.enableAutoUpdate(pipelineName);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Removing externals for <materialName> of pipeline <pipelineName>")
	public void removingExternalsForOfPipeline(final String materialName, final String pipelineName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removeExternalsFor(scenarioState.pipelineNamed(pipelineName), materialName);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Adding test artifact with source <source> and destination <dest> to job <job>")
	public void addingTestArtifactWithSourceAndDestinationToJob(final String source, final String dest, final String job) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addTestArtifactToJob(job, source, dest);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Remove lock from pipeline <pipelineName>")
	public void removeLockFromPipeline(final String pipelineName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removeLockFromPipeline(scenarioState.pipelineNamed(pipelineName));
            }
        });
    }

    @com.thoughtworks.gauge.Step("Making <user> an admin user")
	public void makingAnAdminUser(final String user) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addAdminUser(user);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Removing <user> as an admin")
	public void removingAsAnAdmin(final String user) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removeAdminUser(user);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Make pipeline <pipelineName> use non existant material")
	public void makePipelineUseNonExistantMaterial(final String pipelineName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.makeMaterialNonExistantMaterial(scenarioState.pipelineNamed(pipelineName));
            }
        });
    }
	
    @com.thoughtworks.gauge.Step("Make pipeline <pipelineName> use all non existant material")
	public void makePipelineUseAllNonExistantMaterial(final String pipelineName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.makeAllMaterialsNonExistantMAterials(scenarioState.pipelineNamed(pipelineName));
            }
        });
    }

    @com.thoughtworks.gauge.Step("Changing the artifacts location to <artifactLocation>")
	public void changingTheArtifactsLocationTo(final String artifactLocation) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.changeArtifactLocation(artifactLocation);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Removing pipeline <pipelineName>")
	public void removingPipeline(final String pipelineName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removePipeline(scenarioState.pipelineNamed(pipelineName));
            }
        });
    }

    @com.thoughtworks.gauge.Step("Making pipeline <actualPipeline> not depend on <upstreamDependency>")
	public void makingPipelineNotDependOn(final String actualPipeline, final String upstreamDependency) throws Exception {
        
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removeDependency(scenarioState.pipelineNamed(actualPipeline), scenarioState.pipelineNamed(upstreamDependency));
            }
        });
    }

	@com.thoughtworks.gauge.Step("Make cruise config file invalid")
	public void makeCruiseConfigFileInvalid() throws Exception {
		String path = "/config/invalid-cruise-config.xml";
		String configXmlContents = FileUtil.readToEnd(getClass().getResourceAsStream(path));
		System.out.println(configXmlContents);
		configuration.setConfigOnFileSystem(configXmlContents);
		
	}

	@com.thoughtworks.gauge.Step("Save config by name <name>")
	public void saveConfigByName(String name) throws Exception {
		scenarioState.storeAsValidConfigNamed(name, getDom());
	}

	@com.thoughtworks.gauge.Step("Restore config by name <name>")
	public void restoreConfigByName(String name) throws Exception {
		CruiseConfigDom lastValidDom = scenarioState.getValidConfigNamed(name);
		writeToConfigFileOnFileSystem(lastValidDom);
	}	
	
    public void changeTheScmForPipelineFromTo(final String pipelineName, final String oldScm, final String newScm) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                Element newMaterial = dom.changeScm(scenarioState.pipelineNamed(pipelineName), oldScm, newScm);
                repositoryState.createMaterial(newMaterial);
            }
        });
    }

    public void changingLdapUriTo(String ldapUri) throws Exception {
        configuration.rememberValueAs("/cruise/server/security/ldap/@uri", ORIGINAL_LDAP_URI);
        setLdapUri(ldapUri);
    }

    private void setLdapUri(final String ldapUri) {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.changeLdapUriTo(ldapUri);
            }
        });
    }
    
    public void restoreLdapUri() throws Exception {
        String originalLdapUri = configuration.getRememberedValue(ORIGINAL_LDAP_URI);
        setLdapUri(originalLdapUri);
    }

    @com.thoughtworks.gauge.Step("Moving password file to tmp")
	public void movingPasswordFileToTmp() throws Exception {
        String path = passwordFilePath();
        String tmpPath = tmpPath(path);
        FileUtils.deleteQuietly(new File(tmpPath));
        FileUtils.moveFile(new File(path), new File(tmpPath));
    }

    private String passwordFilePath() {
        return configuration.provideDom().getNode("/cruise/server/security/passwordFile/@path").getText();
    }

    public void restoreThePasswordFile() throws Exception {
        String path = passwordFilePath();
        FileUtils.moveFile(new File(tmpPath(path)), new File(path));
    }

    private String tmpPath(String path) {
        return path + TMP_FILE_SUFFIX;
    }

	@com.thoughtworks.gauge.Step("Allow only known users to login")
	public void allowOnlyKnownUsersToLogin() throws Exception {
		setAllowOnlyKnownUsersToLogin(true);
	}

	@com.thoughtworks.gauge.Step("Allow unknown users to login")
	public void allowUnknownUsersToLogin() throws Exception {
		setAllowOnlyKnownUsersToLogin(false);
	}

    private void setAllowOnlyKnownUsersToLogin(final boolean allow) {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.setAllowOnlyKnownUsersToLogin(allow);
            }
        });
    }
    @com.thoughtworks.gauge.Step("Add site uRL")
	public void addSiteURL() throws Exception {
    	addingAsSiteURL(Urls.urlFor(""));
    }

    @com.thoughtworks.gauge.Step("Add secure site uRL")
	public void addSecureSiteURL() throws Exception {
    	addingAsSecureSiteURL(Urls.sslUrlFor(""));
    }

    @com.thoughtworks.gauge.Step("Adding <url> as site uRL")
	public void addingAsSiteURL(final String url) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addSiteUrl(url);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Adding <url> as secure site uRL")
	public void addingAsSecureSiteURL(final String url) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addSecureSiteUrl(url);
            }
        });
    
    }

    public void removingSiteUrl() throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removeSiteUrl();
            }
        });
    }

    public void removingSecureSiteUrl() throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removeSecureSiteUrl();
            }
        });
    }

    @com.thoughtworks.gauge.Step("Adding <user> as a <permission> user for group <groupName>")
	public void addingAsAUserForGroup(final String user, final String permission, final String groupName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                if ("view".equals(permission)) {
                    dom.addUserViewAuthorizationForGroup(user, groupName);
                } else if ("operate".equals(permission)) {
                    dom.addUserOperateAuthorizationForGroup(user, groupName);
                }
            }
        });
    }

    @com.thoughtworks.gauge.Step("Changing <nodeName> attribute <attributeName> to <link> for pipeline <pipelineName>")
	public void changingAttributeToForPipeline(final String nodeName, final String attributeName, final String link, final String pipelineName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.modifyPipeline(scenarioState.pipelineNamed(pipelineName), nodeName, attributeName, link);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Adding <user> as a <permission> role for group <groupName>")
	public void addingAsARoleForGroup(final String user, final String permission, final String groupName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                if ("view".equals(permission)) {
                    dom.addRoleViewAuthorizationForGroup(user, groupName);
                } else if ("operate".equals(permission)) {
                    dom.addRoleOperateAuthorizationForGroup(user, groupName);
                }
            }
        });
    }

    @com.thoughtworks.gauge.Step("Adding <username> as a group admin of <groupName>")
	public void addingAsAGroupAdminOf(final String username, final String groupName) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addGroupAdmin(groupName, username);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Remove job <jobName> from stage <stageName> in pipeline <pipelineName>")
	public void removeJobFromStageInPipeline(final String jobName, final String stageName, String pipelineName) throws Exception {
        final String runtimeName = scenarioState.pipelineNamed(pipelineName);
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removeJob(runtimeName, stageName, jobName);
            }
        });

    }

    public void removingTemplates() throws Exception {
        modifier.modifyConfig(new ModifyCommand() {
            @Override
            public void modify(CruiseConfigDom dom) {
                dom.removeTemplates();
            }
        });
    }

	@com.thoughtworks.gauge.Step("Remember url for material <materialName> of pipeline <pipelineName>")
	public void rememberUrlForMaterialOfPipeline(String materialName, String pipelineName) throws Exception {
		this.rememberedUrl = configuration.provideDom().urlForMaterial(scenarioState.pipelineNamed(pipelineName), materialName);
	}

	@com.thoughtworks.gauge.Step("Add parameter <parameterName> to pipeline <pipelineName>")
	public void addParameterToPipeline(final String parameterName, String pipelineName) throws Exception {
		final String runtimeName = scenarioState.pipelineNamed(pipelineName);
		modifier.modifyConfig(new ModifyCommand() {
			
			@Override
			public void modify(CruiseConfigDom dom) {
				dom.addParameter(runtimeName, parameterName, rememberedUrl);
			}
		});
	}

    @com.thoughtworks.gauge.Step("Add security with password file and users <users> as admin")
	public void addSecurityWithPasswordFileAndUsersAsAdmin(String users) throws Exception {
        File passwordFile = configuration.copyPasswordFile(getClass().getResource("/config/password.properties"));
        addSecurityTagWithPasswordfileAndAdmin(passwordFile.getAbsolutePath(), users);
    }

    private void addSecurityTagWithPasswordfileAndAdmin(final String passwordFilePath, final String users) {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addSecurityWithPasswordFile(passwordFilePath, users);
            }
        });
    }
    
    @com.thoughtworks.gauge.Step("Add security with password file only")
	public void addSecurityWithPasswordFileOnly() throws Exception {
        File passwordFile = configuration.copyPasswordFile(getClass().getResource("/config/password.properties"));
        addSecurityTagWithPasswordfileOnly(passwordFile.getAbsolutePath());
    }

    private void addSecurityTagWithPasswordfileOnly(final String passwordFilePath) {
        modifier.modifyConfig(new ModifyCommand() {

            @Override
            public void modify(CruiseConfigDom dom) {
                dom.addSecurityWithPasswordFileOnly(passwordFilePath);
            }
        });
    }
    
	@com.thoughtworks.gauge.Step("Update value of key <key> to <value> for repo with id <repoId> - Configure Cruise By")
	public void updateValueOfKeyToForRepoWithId(final String key, final String value, final String repoId) throws Exception {
        modifier.modifyConfig(new ModifyCommand() {
        	            @Override
            public void modify(CruiseConfigDom dom) {
                dom.updateValueOfKeyToForRepoWithId(key, value, repoId);
            }
        });		
	}
}