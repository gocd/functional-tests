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

package com.thoughtworks.cruise.utils.configfile;

import com.thoughtworks.cruise.client.Job;
import com.thoughtworks.cruise.client.Stage;
import com.thoughtworks.cruise.materials.TfsServer;
import com.thoughtworks.cruise.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.thoughtworks.cruise.util.ExceptionUtils.bomb;
import static com.thoughtworks.cruise.util.ExceptionUtils.bombIfNull;
import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@SuppressWarnings("unchecked")
public class CruiseConfigDom {
	private Document document;

	private static final Pattern SCHEMA_VERSION_PATTERN = Pattern.compile(".+"
			+ "fixed=\"(\\d+)\"" + ".+");

	public CruiseConfigDom(String xml) throws DocumentException, SAXException,
			URISyntaxException {
		document = parse(xml);
	}

	public Element getStage(String pipelineName, String stageName) {
		return stageElement(pipelineElement(pipelineName), stageName);
	}

	public Element getPipeline(String pipelineName) {
		return pipelineElement(pipelineName);
	}

	public String getMaterialUrl(String pipelineName, String materialType,
			String materialName) {
		return (String) getMaterial(pipelineName, materialType, materialName)
				.attribute("url").getValue();
	}

	public Element getMaterial(String pipelineName, String materialType,
			String materialName) {
		return materialElement(pipelineElement(pipelineName), materialType,
				materialName);
	}

	public Element getJob(String pipelineName, String stageName, String jobName) {
		Element stage = stage(pipelineName, stageName);
		return (Element) stage.selectSingleNode(format(
				"jobs/job[@name=\"%s\"]", jobName));
	}

	private Element stage(String pipelineName, String stageName) {
		Element stage = stageElement(pipelineElement(pipelineName), stageName);
		return stage;
	}

	public void appendFetchArtifacts(String pipelineName, String stageName,
			String job, String pipelineFetchFrom, String stageFetchFrom,
			String path, String dest) {
		Element jobElement = getJob(pipelineName, stageName, job);
		Element tasks = (Element) jobElement.selectSingleNode("tasks");
		if (tasks == null) {
			tasks = ((Element) jobElement).addElement("tasks");
		}

		Element fetchArtifact = new DefaultElement("fetchartifact");
		fetchArtifact.addAttribute("pipeline", pipelineFetchFrom);
		fetchArtifact.addAttribute("stage", stageFetchFrom);
		fetchArtifact.addAttribute("job", job);
		fetchArtifact.addAttribute("srcfile", path);
		fetchArtifact.addAttribute("dest", dest);

		tasks.add(fetchArtifact);
	}

	public void appendFetchDirectory(String pipelineName, String firstStage,
			String secondStage, String job, String path, String dest) {
		Element jobElement = getJob(pipelineName, secondStage, job);
		Element tasks = (Element) jobElement.selectSingleNode("tasks");

		Element fetchArtifact = new DefaultElement("fetchartifact");
		fetchArtifact.addAttribute("stage", firstStage);
		fetchArtifact.addAttribute("job", job);
		fetchArtifact.addAttribute("srcdir", path);
		fetchArtifact.addAttribute("dest", dest);

		tasks.add(fetchArtifact);

	}

	public void setApprovalTypeForStage(String pipelineName, String stageName,
			String type) {
		Element stage = getStage(pipelineName, stageName);
		Element approval = stage.element("approval");
		if (approval == null) {
			approval = new DefaultElement("approval");
			Element jobs = stage.element("jobs");
			if (jobs != null) {
				jobs.detach();
				stage.add(approval);
				stage.add(jobs);
			} else {
				stage.add(approval);
			}
		}
		Attribute attribute = approval.attribute("type");
		if (attribute != null) {
			attribute.setValue(type);
		} else {
			approval.addAttribute("type", type);
		}
	}

	public String asXml() {
		return document.asXML();
	}

	static Document parse(String xml) throws DocumentException, SAXException,
			URISyntaxException {
		URL resource = schemaResource();
		SAXReader builder = new SAXReader();
		builder.setFeature("http://apache.org/xml/features/validation/schema",
				true);
		builder.setProperty(
				"http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
				resource.toURI().toString());
		builder.setValidation(true);
		Document dom = null;
		try {
			dom = builder.read(new StringReader(xml));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("XML invalid. " + xml, e);
		}
		return dom;
	}

	private static URL schemaResource() {
		return CruiseConfigDom.class.getResource("/cruise-config.xsd");
	}

	public static String currentSchemaVersion() throws IOException {
		URL resource = schemaResource();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					resource.openStream()));
			for (String line = bufferedReader.readLine(); line != null; line = bufferedReader
					.readLine()) {
				if (line.contains("schemaVersion")) {
					Matcher matcher = SCHEMA_VERSION_PATTERN.matcher(line);
					if (matcher.matches()) {
						return matcher.group(1);
					}
				}
			}
		} finally {
			IOUtils.closeQuietly(bufferedReader);
		}
		throw bomb("Can not parse schemaVersion!");
	}

	public void updateLicense(String license, String user) {
		Element licenseElement = (Element) root().selectSingleNode("//license");
		licenseElement.clearContent();
		licenseElement.add(new DefaultText(license));
		licenseElement.addAttribute("user", user);
	}

	public void removeLicense() {
		Element serverElement = serverTag();
		Element licenseElement = (Element) serverElement
				.selectSingleNode("./license");
		serverElement.remove(licenseElement);
	}

	public void updateBuildFile(String pipelineName, String stageName,
			String jobName, String buildType, String buildFile) {
		String xpath = String
				.format("//pipelines/pipeline[@name='%s']/stage[@name='%s']/jobs/job[@name='%s']/tasks",
						pipelineName, stageName, jobName);
		Element element = (Element) document.selectSingleNode(xpath);
		element.clearContent();
		Element newElement = new DefaultElement(buildType);
		newElement.addAttribute("buildfile", buildFile);
		element.add(newElement);

	}

	public void setUpSMTP(String hostName, int port, String username,
			String password, String tls, String from, String adminEmail) {
		Element serverTag = serverTag();
		DefaultElement mailhost = new DefaultElement("mailhost");
		mailhost.addAttribute("hostname", hostName);
		mailhost.addAttribute("port", String.valueOf(port));
		mailhost.addAttribute("username", username);
		mailhost.addAttribute("password", password);
		mailhost.addAttribute("tls", tls);
		mailhost.addAttribute("from", from);
		mailhost.addAttribute("admin", adminEmail);
		serverTag.add(mailhost);
	}

	public void createPipeline(String pipelineName, String materialType,
			String url, String dest) {
		createPipeline(pipelineName, materialType, null, url, dest);
	}

	public void createPipeline(String pipelineName, String materialType,
			String materialName, String url, String dest) {

		Element pipeline = pipelines().addElement("pipeline").addAttribute(
				"name", pipelineName);

		Element materials = pipeline.addElement("materials");
		Element material = materials.addElement(materialType).addAttribute(
				"url", url);
		if (materialName != null) {
			material.addAttribute("materialName", materialName);
		}
		if (!StringUtils.isEmpty(dest)) {
			material.addAttribute("dest", dest);
		}
		Element stage = pipeline.addElement("stage").addAttribute("name",
				Stage.DEFAULT_STAGE);
		stage.addElement("approval").addAttribute("type", "manual");
		addDefaultJobToStage(stage);
	}

	private void addDefaultJobToStage(Element stage) {
		Element job = stage.addElement("jobs").addElement("job");
		job.addAttribute("name", Job.DEFAULT_JOB);
		job.addElement("tasks");
	}

	public void addStage(String pipelineName, String stageName) {
		Element pipeline = pipelineElement(pipelineName);
		Element stage = pipeline.addElement("stage").addAttribute("name",
				stageName);
		addDefaultJobToStage(stage);
	}

	public void addMaterial(String pipelineName, String materialType,
			String url, String dest) {
		Element materials = (Element) pipelineElement(pipelineName)
				.selectSingleNode("materials");
		materials.addElement(materialType).addAttribute("url", url)
				.addAttribute("dest", dest);
	}

	public void addDependencyMaterial(String pipelineName,
			String dependentPipeline, String dependentStage) {
		Element materials = (Element) pipelineElement(pipelineName)
				.selectSingleNode("materials");
		materials.addElement("pipeline")
				.addAttribute("pipelineName", dependentPipeline)
				.addAttribute("stageName", dependentStage);
	}

	private Element pipelines(String groupName) {
		String xpath = String.format("pipelines[@group='%s']", groupName);
		Element pipelines = (Element) getNode(xpath);
		if (pipelines == null) {
			Element agents = root().element("agents");
			if (agents != null) {
				agents.detach();
			}
			pipelines = root().addElement("pipelines").addAttribute("group",
					groupName);
			if (agents != null) {
				root().add(agents);
			}
		}
		return pipelines;
	}

	private Element pipelines() {
		Element pipelines = root().element("pipelines");
		if (pipelines == null) {
			Element agents = root().element("agents");
			if (agents != null) {
				agents.detach();
			}
			pipelines = root().addElement("pipelines");
			if (agents != null) {
				root().add(agents);
			}
		}
		return pipelines;
	}

	private Element root() {
		return document.getRootElement();
	}

	private Element pipelineElement(String pipelineName) {
		Element pipeline = (Element) root().selectSingleNode(
				format("pipelines/pipeline[@name=\"%s\"]", pipelineName));
		bombIfNull(pipeline,
				format("Pipeline %s does not exist.", pipelineName));
		return pipeline;
	}

	private List pipelinesFor(String groupName) {
		groupName = groupName.trim();
		List pipelines = root().selectNodes(
				format("pipelines[@group='%s']/pipeline", groupName));
		if (pipelines.isEmpty())
			bomb(format("Group %s does not exist.", groupName));
		return pipelines;
	}

	private Element stageElement(Element pipeline, String stageName) {
		Element stage = (Element) pipeline.selectSingleNode(format(
				"stage[@name=\"%s\"]", stageName));
		bombIfNull(
				stage,
				format("Stage %s does not exist in pipeline %s", stageName,
						pipeline.attributeValue("name")));
		return stage;
	}

	private Element materialElement(Element pipeline, String materialType,
			String materialName) {
		Element material = (Element) pipeline.selectSingleNode(format(
				"materials/%s[@materialName=\"%s\"]", materialType,
				materialName));
		return material;
	}

	private Element stageElement(String pipelineName, String stageName) {
		return stageElement(pipelineElement(pipelineName), stageName);
	}

	public void addPipelineGroupWithSvnMaterial(String groupName,
			String pipelineName, String url) {
		Element pipeline = addPipelineGroupWithSvnMaterialAndOneStage(
				groupName, pipelineName, url);
		pipeline.addElement("stage").addAttribute("name", "secondStage")
				.addElement("jobs").addElement("job")
				.addAttribute("name", Job.DEFAULT_JOB);

	}

	public Element addPipelineGroupWithSvnMaterialAndOneStage(String groupName,
			String pipelineName, String url) {
		Element pipelines = pipelines(groupName);
		Element pipeline = pipelines.addElement("pipeline").addAttribute(
				"name", pipelineName);
		Element materials = pipeline.addElement("materials");
		materials.addElement("svn").addAttribute("url", url);
		pipeline.addElement("stage").addAttribute("name", Stage.DEFAULT_STAGE)
				.addElement("jobs").addElement("job")
				.addAttribute("name", Job.DEFAULT_JOB);
		return pipeline;
	}

	public void removePipelineGroup(String groupName) {
		String xpath = String.format("pipelines[@group='%s']", groupName);
		Element pipelines = (Element) getNode(xpath);
		if (pipelines != null) {
			pipelines.detach();

		}
	}

	public List<String> pipelineGroups() {
		String xpath = String.format("pipelines/@group");
		return attributeValues(xpath);
	}

	public List<String> pipelinesFromGroup(String pipelineGroupName) {
		String xpath = String.format("pipelines[@group='%s']/pipeline",
				pipelineGroupName);
		return elementNames(xpath);
	}

	public List<String> allPipelines() {
		String xpath = "pipelines/pipeline";
		return elementNames(xpath);
	}

	private List<String> elementNames(String xpath) {
		List<String> result = new ArrayList<String>();
		List<Element> list = root().selectNodes(xpath);
		for (Element element : list) {
			result.add(element.attributeValue("name"));
		}
		return result;
	}

	private List<String> attributeValues(String xpath) {
		List<String> result = new ArrayList<String>();
		List<Attribute> list = root().selectNodes(xpath);
		for (Attribute element : list) {
			result.add(element.getValue());
		}
		return result;
	}

	public List<String> stagesFromPipeline(String pipelineName) {
		String xpath = String.format("pipelines/pipeline[@name='%s']/stage",
				pipelineName);
		return elementNames(xpath);
	}

	public void addUserViewAuthorizationForGroup(String userName,
			String groupName) {
		addPermissionForGroup("user", userName, groupName, "view");
	}

	private void addChildrenBack(Element parent, List<Element> children) {
		for (Element element : children) {
			parent.add(element);
		}
	}

	private void detatchChildren(List<Element> elements) {
		for (Element element : elements) {
			element.detach();
		}
	}

	private void addPermissionForGroup(String type, String role,
			String groupName, String permissionName) {
		String xpath = String.format("pipelines[@group='%s']", groupName);
		Element pipelineGroup = (Element) getNode(xpath);
		if (pipelineGroup == null) {
			throw bomb("Pipeline group " + groupName + " was not found.");
		}
		Element authElement = pipelineGroup.element("authorization");
		if (authElement == null) {
			authElement = createAsFirstChild(pipelineGroup, "authorization");
		}
		Element permissionElement = authElement.element(permissionName);
		if (permissionElement == null) {
			permissionElement = authElement.addElement(permissionName);
		}
		permissionElement.addElement(type).setText(role);
	}

	private Element createAsFirstChild(Element parent, String elementName) {
		List<Element> elements = parent.elements();
		detatchChildren(elements);
		Element child = parent.addElement(elementName);
		addChildrenBack(parent, elements);
		return child;
	}

	public void removeViewPermissionDefinition(String groupName) {
		String xpath = String.format("pipelines[@group='%s']/authorization",
				"firstGroup");
		Element authorization = (Element) getNode(xpath);
		if (authorization != null) {
			authorization.detach();
		}

	}

	public void addTaskToJob(String pipelineName, String stageName,
			String jobName, Element taskElement) {
		Element pipeline = pipelineElement(pipelineName);
		Element job = (Element) pipeline.selectSingleNode(String.format(
				"stage[@name='%s']/jobs/job[@name='%s']", stageName, jobName));
		Element tasks = job.element("tasks");
		if (tasks == null) {
			tasks = job.addElement("tasks");
		}
		tasks.add(taskElement);
	}

	public void removePipeline(String pipeline) {
		Element pipelineElement = pipelineElement(pipeline);
		Element pipelineGroup = pipelineElement.getParent();
		pipelineElement.detach();
		if (pipelineGroup.selectNodes("pipeline").size() == 0) {
			pipelineGroup.detach();
		}
		removePipelineFromEnvironment(pipeline);
	}

	public void removePipelineFromEnvironment(String pipeline) {
		List<Element> environmentPipelines = (List<Element>) root()
				.selectNodes(
						String.format(
								"/cruise/environments/environment/pipelines/pipeline[@name='%s']",
								pipeline));
		for (Element element : environmentPipelines) {
			element.detach();
		}
	}

	public void removePipelinesExcept(String... pipelines) {
		HashSet keepPipelines = new HashSet(Arrays.asList(pipelines));
		for (String pipeline : allPipelines()) {
			if (!keepPipelines.contains(pipeline)) {
				removePipeline(pipeline);
			}
		}
	}

	public void moveExistingPipelineToPipelineGroup(String pipeline,
			String pipelineGroup) {
		Element pipelines = pipelines(pipelineGroup);
		pipelines.add(pipelineElement(pipeline).detach());
	}

	public void addUserOperateAuthorizationForGroup(String user,
			String groupName) {
		addPermissionForGroup("user", user, groupName, "operate");
	}

	public void addRoleViewAuthorizationForGroup(String roleName,
			String groupName) {
		addPermissionForGroup("role", roleName, groupName, "view");
	}

	public void addRoleOperateAuthorizationForGroup(String roleName,
			String groupName) {
		addPermissionForGroup("role", roleName, groupName, "operate");
	}

	public void removeOperatePermissionDefinition(String groupName) {
		String xpath = String.format("pipelines[@group='%s']/authorization",
				"firstGroup");
		Element authorization = (Element) getNode(xpath);
		if (authorization != null) {
			authorization.detach();
		}

	}

	public void addJobToStageOfPipeline(String job, String stage,
			String pipelineName) {
		Element pipeline = pipelineElement(pipelineName);
		Element jobs = (Element) pipeline.selectSingleNode(String.format(
				"stage[@name='%s']/jobs", stage));
		jobs.addElement("job").addAttribute("name", job);
	}

	public void addArtifactForFirstJobInFirstPipeline(String src, String dest) {
		String xpathForJob = String.format("//job[@name='%s']", "defaultJob");
		Element job = (Element) getNode(xpathForJob);

		Element artifacts = getSubElement(job, "artifacts");
		artifacts.addElement("artifact").addAttribute("src", src)
				.addAttribute("dest", dest);
	}

	private Element getSubElement(Element element, String subElementName) {
		Element subElement = element.element(subElementName);
		if (subElement == null) {
			subElement = element.addElement(subElementName);
		}

		return subElement;
	}

	public void addApprovalUserForStage(String pipelineName, String stageName,
			String username) {
		Element stage = stageElement(pipelineName, stageName);
		stage.element("approval").addElement("authorization")
				.addElement("user").setText(username);
	}

	public void setAllowOnlyKnownUsersToLogin(boolean value) {
		Element security = (Element) document.getRootElement()
				.selectSingleNode("//security");
		if (security == null) {
			security = new DefaultElement("security");
			Element server = (Element) document.getRootElement()
					.selectSingleNode("//server");
			server.add(security);
		}
		Attribute allowOnlyKnownUsers = security
				.attribute("allowOnlyKnownUsersToLogin");
		if (allowOnlyKnownUsers == null) {
			allowOnlyKnownUsers = new DefaultAttribute(
					"allowOnlyKnownUsersToLogin", Boolean.toString(value));
			security.add(allowOnlyKnownUsers);
		} else {
			allowOnlyKnownUsers.setValue(Boolean.toString(value));
		}
	}

	public void usingPasswordFile(File passwordFile) {
		Element passwordElement = new DefaultElement("passwordFile");
		passwordElement.addAttribute("path", passwordFile.getAbsolutePath());
		Element security = (Element) document.getRootElement()
				.selectSingleNode("//security");
		if (security == null) {
			security = new DefaultElement("security");
			Element server = (Element) document.getRootElement()
					.selectSingleNode("//server");
			server.add(security);
		}
		security.add(passwordElement);
	}

	public void addAdmins(String... users) {
		Element security = (Element) document.getRootElement()
				.selectSingleNode("//security");
		Element admins = (Element) document.getRootElement().selectSingleNode(
				"//admins");
		if (admins == null) {
			admins = new DefaultElement("admins");
			security.add(admins);
		}
		for (String user : users) {
			Element userElement = new DefaultElement("user");
			userElement.addText(user);
			admins.add(userElement);
		}
	}

	public void addRolesToAdmin(String[] roles) {
		Element security = (Element) document.getRootElement()
				.selectSingleNode("//security");
		Element admins = (Element) document.getRootElement().selectSingleNode(
				"//admins");
		if (admins == null) {
			admins = new DefaultElement("admins");
			security.add(admins);
		}
		for (String role : roles) {
			Element userElement = new DefaultElement("role");
			userElement.addText(role);
			admins.add(userElement);
		}
	}

	public void removeUserFromRole(String role, String user) {
		Element roles = (Element) document.getRootElement().selectSingleNode(
				"//role[name='" + role + "']/user[textnode()='" + user + "']");
		document.getRootElement().remove(roles);
	}

	public void addRoles(String[] splitedroles) {
		Element security = (Element) document.getRootElement()
				.selectSingleNode("//security");
		Element roles = (Element) document.getRootElement().selectSingleNode(
				"//roles");
		if (roles == null) {
			roles = new DefaultElement("roles");
			security.add(roles);
		}
		for (String splitedrole : splitedroles) {
			Element role = new DefaultElement("role");
			role.addAttribute("name", splitedrole);
			roles.add(role);
		}
	}

	public void addUsersToRole(String roleName, String[] users) {
		Element security = (Element) document.getRootElement()
				.selectSingleNode("//security");
		Element roles = (Element) document.getRootElement().selectSingleNode(
				"//roles");
		if (roles == null) {
			roles = new DefaultElement("roles");
			security.add(roles);
		}
		Element role = (Element) document.getRootElement().selectSingleNode(
				"//role[name='" + roleName + "']");
		if (role == null) {
			role = new DefaultElement("role");
			role.addAttribute("name", roleName);
			roles.add(role);
		}
		for (String user : users) {
			Element userElement = new DefaultElement("user");
			userElement.addText(user);
			role.add(userElement);
		}
	}

	public Map<String, String> replacePipelineNames() {
		Map<String, String> pipelineNames = new HashMap<String, String>();
		List<Element> list = root().selectNodes("/cruise/pipelines/pipeline");
		for (Element element : list) {
			String initial = element.attribute("name").getText();
			String replaced = initial + StringUtil.shortUUID();
			for (Element pipelineElem : (List<Element>) root().selectNodes(
					"//pipeline[@name='" + initial + "']")) {
				pipelineElem.addAttribute("name", replaced);
			}
			replaceDependentPipeline(initial, replaced);
			replacePipelineInFetchArtifact(initial, replaced);
			pipelineNames.put(initial, replaced);
		}
		return pipelineNames;
	}

	public List<String> getPipelineNames() {
		List<String> pipelineNames = new ArrayList<String>();
		List<Element> list = root().selectNodes("/cruise/pipelines/pipeline");
		for (Element element : list) {
			pipelineNames.add(element.attribute("name").getText());
		}
		return pipelineNames;
	}

	public Map<String, String> replacePackageRepositoryURIForFileSystemBasedRepos() {
		Map<String, String> packageURI = new HashMap<String, String>();
		List<Element> list = root().selectNodes(
				"/cruise/repositories/repository/configuration/property/value");

		for (Element element : list) {
			String initial = element.getStringValue();

			String replaced = initial.matches("^\\$repo*[0-9]+$") ? PackageRepository
					.getPackageRepositoryURI(initial) : initial;

			if (initial != replaced) {
				for (Element propertyElem : (List<Element>) root().selectNodes(
						"//property/value[text()='" + initial + "']")) {
					propertyElem.setText("file://" + replaced);
				}
				packageURI.put(initial.replace("$", ""), replaced);
			}

		}
		return packageURI;
	}

	/** Looks for variables of type: ${http_repo*} in REPO_URL property and replaces it. Example:
     *    <repositories>
     *        <repository id="repo-id" name="tw-repo">
     *          <pluginConfiguration id="yum" version="1"/>
     *          <configuration>
     *            <property>
     *              <key>REPO_URL</key>
     *              <value>http://192.168.0.101:8081/${http_repo1}</value>
     *            </property>
     *
     *     TO
     *
     *    <repositories>
     *        <repository id="repo-id" name="tw-repo">
     *          <pluginConfiguration id="yum" version="1"/>
     *          <configuration>
     *            <property>
     *              <key>REPO_URL</key>
     *              <value>http://192.168.0.101:8081/pkgrepo-RANDOM1234</value>
     *            </property>
     *
     *      AND return a map which contains: {"http_repo1" => "pkgrepo-RANDOM1234"}
	 */
    public Map<String, String> replacePackageRepositoryURIForHttpBasedRepos(Map<String, String> currentRepoNames) {
        Pattern httpRepoNameVariablePattern = Pattern.compile("\\$\\{(http_repo[^\\}]*)\\}");
        Map<String, String> httpRepoNameInConfigToItsNameAtRuntime = new HashMap<String, String>(currentRepoNames);

        List<Element> allRepoUrlPropertyKeys = root().selectNodes("/cruise//repositories/repository/configuration/property/key[text()='REPO_URL']");
        for (Element repoUrlKeyElement : allRepoUrlPropertyKeys) {
            Node repoUrlPropertyInConfig = repoUrlKeyElement.getParent().selectSingleNode("value");
            String existingValueOfRepoUrl = repoUrlPropertyInConfig.getText();

            Matcher matcherForVariable = httpRepoNameVariablePattern.matcher(existingValueOfRepoUrl);
            if (matcherForVariable.find()) {
                String variableName = matcherForVariable.group(1);

                String replacementRepoName = httpRepoNameInConfigToItsNameAtRuntime.get(variableName);
                if (!httpRepoNameInConfigToItsNameAtRuntime.containsKey(variableName)) {
                    replacementRepoName = "pkgrepo-" + RandomStringUtils.randomAlphanumeric(10);
                    httpRepoNameInConfigToItsNameAtRuntime.put(variableName, replacementRepoName);
                }

                String replacementRepoURL = existingValueOfRepoUrl.replaceFirst(httpRepoNameVariablePattern.pattern(), replacementRepoName);
                repoUrlPropertyInConfig.setText(replacementRepoURL);
            }
        }

        return httpRepoNameInConfigToItsNameAtRuntime;
    }

	private void replacePipelineInFetchArtifact(String initial, String replaced) {
		List<Element> selectNodes = (List<Element>) root().selectNodes(
				"//fetchartifact");
		for (Element fetchArtifactElem : selectNodes) {
			Attribute pathFromAncestorAttr = fetchArtifactElem
					.attribute("pipeline");
			if (pathFromAncestorAttr != null) {
				String pathFromAncestor = pathFromAncestorAttr.getStringValue();
				String[] individualPipelineNames = pathFromAncestor.split("/");
				for (String pipelineName : individualPipelineNames) {
					if (pipelineName.equals(initial)) {
						fetchArtifactElem.addAttribute("pipeline",
								pathFromAncestor.replace(initial, replaced));
						break;
					}
				}
			}
		}

	}

	private void replaceDependentPipeline(String initial, String replaced) {
		for (Element pipelineElem : (List<Element>) root().selectNodes(
				"//pipeline[@pipelineName='" + initial + "']")) {
			pipelineElem.addAttribute("pipelineName", replaced);
		}
	}

	public void ignore(String pipelineName, String scm, String pattern) {
		Element pipelineElement = pipelineElement(pipelineName);
		Element scmElement = (Element) pipelineElement
				.selectSingleNode("materials/" + scm);
		scmElement.addElement("filter").addElement("ignore")
				.addAttribute("pattern", pattern);
	}

	public void changeHgUrl(String newURL) {
		List<Element> hgs = root().selectNodes("//hg");
		for (Element hg : hgs) {
			Element parent = hg.getParent();
			parent.remove(hg);
			String dest = hg.attributeValue("dest");
			Element newHg = new DefaultElement("hg");
			if (!StringUtils.isEmpty(dest)) {
				newHg.addAttribute("dest", dest);
			}
			newHg.addAttribute("url", newURL);
			parent.add(newHg);
		}
	}

	public void changeToManualTrigger(String pipelineName) {
		Element pipelineElement = pipelineElement(pipelineName);
		Element firstStageElement = (Element) pipelineElement.selectNodes(
				"stage").get(0);
		Element approval = createAsFirstChild(firstStageElement, "approval");
		approval.addAttribute("type", "manual");
	}

	public void setLabelTemplate(String pipelineName, String labelTemplate) {
		Element pipelineElement = pipelineElement(pipelineName);
		pipelineElement.addAttribute("labeltemplate", labelTemplate);
	}

	public void removeMaterialByURL(String pipelineName, String type, String url) {
		Element pipelineElement = pipelineElement(pipelineName);
		String selector = String.format("materials/%s[@url='%s']", type, url);
		List<Element> selectNodes = pipelineElement.selectNodes(selector);
		for (Element selectNode : selectNodes) {
			selectNode.detach();
		}
	}

	public void setDependencyMaterialNameForPipeline(String pipelineName,
			String materialName) {
		Element pipelineElement = pipelineElement(pipelineName);
		Element materialElement = (Element) pipelineElement
				.selectSingleNode("materials/pipeline[1]");
		materialElement.addAttribute("materialName", materialName);
	}

	public void setTaskForFirstStage(String pipelineName,
			SimpleCruiseConfigTag cruiseConfigTag) {
		Element pipelineElement = pipelineElement(pipelineName);
		// Element tasksElement = (Element)
		// pipelineElement.selectSingleNode("stage/jobs/job/tasks");
		Element jobElement = (Element) pipelineElement
				.selectSingleNode("stage/jobs/job");

		Element tasksElement = jobElement.element("tasks");
		if (tasksElement == null) {
			tasksElement = new DefaultElement("tasks");
			jobElement.add(tasksElement);
		}

		List<Element> taskList = tasksElement.elements();
		for (Element task : taskList) {
			tasksElement.remove(task);
		}

		tasksElement.add(cruiseConfigTag.convertToDomElement());
	}

	public void addTimerFor(String pipelineName, String timerSpec) {
		Element pipeline = pipelineElement(pipelineName);
		List elements = pipeline.elements();
		DefaultElement element = new DefaultElement("timer");
		element.addText(timerSpec);
		elements.add(0, element);
	}

	private Element environments() {
		Element elem = root().element("environments");
		if (elem == null) {
			Element agents = root().element("agents");
			if (agents != null)
				agents.detach();
			elem = root().addElement("environments");
			if (agents != null)
				root().add(agents);
		}
		return elem;
	}

	public void addAgentUnderEnvironment(String environmentName,
			String agentUuid) {
		findOrCreateElement(environment(environmentName), "agents").addElement(
				"physical").addAttribute("uuid", agentUuid);
	}

	public Element environment(String environmentName) {
		Element environment = (Element) getEnvironment(environmentName);
		if (environment == null) {
			environment = environments().addElement("environment");
			environment.addAttribute("name", environmentName);
		}
		return environment;
	}

	public Element getEnvironment(String environmentName) {
		Element environments = environments();
		return (Element) environments.selectSingleNode("environment[@name='"
				+ environmentName + "']");
	}

	public void removeAgentFromEnvironment(String envronmentName, String uuid) {
		Element environment = environment(envronmentName);
		Element agents = environment.element("agents");
		agents.selectSingleNode("physical[@uuid='" + uuid + "']").detach();
		if (agents.elements().size() == 0) {
			environment.detach();
			Element environments = environments();
			if (environments.elements().size() == 0) {
				environments.detach();
			}
		}
	}

	public List<Element> materialsForPipeline(String pipelineName) {
		return materialsTagForPipeline(pipelineName).elements();
	}

	private Element materialsTagForPipeline(String pipelineName) {
		return (Element) root().selectSingleNode(
				String.format("//pipeline[contains(@name, '%s')]//materials",
						pipelineName));
	}

	public void addResourceToAllAgents(String resource) {
		for (Element agent : allAgents()) {
			Element resources = findOrCreateElement(agent, "resources");
			resources.add(resourceElement(resource));
		}
	}

	public void denyOneAgent() {
		for (Element agent : allAgents()) {
			if (agentHasResource(agent, "missing")) {
				continue;
			}
			if (agentHasResource(agent, "denied")) {
				continue;
			}
			if (agent.attributeValue("isDisabled") == null
					|| agent.attributeValue("isDisabled").equals("false")) {
				agent.addAttribute("isDisabled", "true");
				return;
			}
		}
		fail("Could not find an agent to deny.");
	}

	public void undenyOneAgent() {
		for (Element agent : allAgents()) {
			if (agentHasResource(agent, "missing")) {
				continue;
			}
			if (agentHasResource(agent, "denied")) {
				continue;
			}
			if (agent.attributeValue("isDisabled") != null
					&& agent.attributeValue("isDisabled").equals("true")) {
				agent.addAttribute("isDisabled", "false");
				return;
			}
		}
		fail("Could not find an agent to undeny.");
	}

	private boolean agentHasResource(Element agent, String resourceText) {
		List<Element> resources = agent.selectNodes("resources/resource");
		for (Element resource : resources) {
			if (resource.getText().equals(resourceText)) {
				return true;
			}
		}
		return false;
	}

	private List<Element> allAgents() {
		return (List<Element>) root().selectNodes("/cruise/agents/agent");
	}

	private DefaultElement resourceElement(String resource) {
		DefaultElement resourceElement = new DefaultElement("resource");
		resourceElement.setText(resource);
		return resourceElement;
	}

	public Set<String> addAgentsToEnvironment(int numberOfAgents,
			String resource, String environmentName,
			List<String> selectFromUuids) {

		List<Element> agentsWithResource = root()
				.selectNodes(
						String.format(
								"/cruise/agents/agent[resources/resource[text()='%s']]",
								resource));
		return addAgentsToEnvironment(numberOfAgents, environmentName,
				selectFromUuids, agentsWithResource);
	}

	public Set<String> addAgentsToEnvironment(int numberOfAgents,
			String environmentName, List<String> idleAgents) {
		return addAgentsToEnvironment(numberOfAgents, environmentName,
				idleAgents,
				(List<Element>) root().selectNodes("/cruise/agents/agent"));
	}

	public Set<String> removeAgentsFromEnvironment(int numberOfAgents,
			String environmentName, List<String> idleAgents) {
		return removeAgentsFromEnvironment(numberOfAgents, environmentName,
				idleAgents,
				(List<Element>) root().selectNodes("/cruise/agents/agent"));
	}

	public void addAgentToEnvironment(String uuid, String environmentName) {
		assignAgentToEnvironment(environment(environmentName), uuid);
	}

	private Set<String> addAgentsToEnvironment(int numberOfAgents,
			String environmentName, List<String> idleAgents,
			List<Element> possibleAgents) {
		Element environment = environment(environmentName);
		removeAgentsAlreadyInEnvironment(possibleAgents,
				(List<Element>) environment.selectNodes("//agents/physical"));

		if (idleAgents.size() < numberOfAgents) {
			throw new IllegalArgumentException(String.format(
					"There are not enough idle agents (%s) to assign (%s)",
					idleAgents.size(), numberOfAgents));
		}
		if (possibleAgents.size() < numberOfAgents) {
			throw new IllegalArgumentException(
					String.format(
							"Total number of matching agents (%s) is less than requested number (%s)",
							possibleAgents.size(), numberOfAgents));
		}

		Set<String> selectedUuids = new HashSet<String>();
		for (Element possibleAgent : possibleAgents) {
			if (idleAgents.contains(possibleAgent.attribute("uuid").getValue())) {
				selectedUuids.add(possibleAgent.attributeValue("uuid"));
				if (selectedUuids.size() >= numberOfAgents) {
					break;
				}
			}
		}

		for (String uuid : selectedUuids) {
			assignAgentToEnvironment(environment, uuid);
		}

		if (selectedUuids.size() < numberOfAgents) {
			throw new RuntimeException("Could not add " + numberOfAgents
					+ " to environment " + environmentName + " from agents "
					+ possibleAgents + "selecting from " + idleAgents
					+ " number of selected uuids = " + selectedUuids.size());
		}
		return selectedUuids;
	}

	private Set<String> removeAgentsFromEnvironment(int numberOfAgents,
			String environmentName, List<String> idleAgents,
			List<Element> possibleAgents) {
		Element environment = environment(environmentName);
		removeAgentsAlreadyInEnvironment(possibleAgents,
				(List<Element>) environment.selectNodes("//agents/physical"));

		if (idleAgents.size() < numberOfAgents) {
			throw new IllegalArgumentException(String.format(
					"There are not enough idle agents (%s) to assign (%s)",
					idleAgents.size(), numberOfAgents));
		}
		if (possibleAgents.size() < numberOfAgents) {
			throw new IllegalArgumentException(
					String.format(
							"Total number of matching agents (%s) is less than requested number (%s)",
							possibleAgents.size(), numberOfAgents));
		}

		Set<String> selectedUuids = new HashSet<String>();
		for (Element possibleAgent : possibleAgents) {
			if (idleAgents.contains(possibleAgent.attribute("uuid").getValue())) {
				selectedUuids.add(possibleAgent.attributeValue("uuid"));
				if (selectedUuids.size() >= numberOfAgents) {
					break;
				}
			}
		}

		for (String uuid : selectedUuids) {
			deassignAgentToEnvironment(environment, uuid);
		}

		if (selectedUuids.size() < numberOfAgents) {
			throw new RuntimeException("Could not add " + numberOfAgents
					+ " to environment " + environmentName + " from agents "
					+ possibleAgents + "selecting from " + idleAgents);
		}
		return selectedUuids;
	}

	private void assignAgentToEnvironment(Element environment, String uuid) {
		Element physical = new DefaultElement("physical");
		physical.addAttribute("uuid", uuid);
		Element envAgents = (Element) environment.selectSingleNode("agents");
		if (envAgents == null) {
			Element envPipelines = (Element) environment
					.selectSingleNode("pipelines");
			if (envPipelines != null) {
				environment.remove(envPipelines);
			}
			envAgents = new DefaultElement("agents");
			environment.add(envAgents);
			if (envPipelines != null) {
				environment.add(envPipelines);
			}
		}
		envAgents.add(physical);
	}

	private void deassignAgentToEnvironment(Element environment, String uuid) {
		Element physical = new DefaultElement("physical");
		physical.addAttribute("uuid", uuid);
		Element envAgents = (Element) environment.selectSingleNode("agents");
		if (envAgents == null) {
			Element envPipelines = (Element) environment
					.selectSingleNode("pipelines");
			if (envPipelines != null) {
				environment.remove(envPipelines);
			}
			envAgents = new DefaultElement("agents");
			environment.remove(envAgents);
			if (envPipelines != null) {
				environment.add(envPipelines);
			}
		}
		envAgents.add(physical);
	}

	private void removeAgentsAlreadyInEnvironment(List<Element> agents,
			List<Element> agentsInEnvironment) {
		for (Element element : agentsInEnvironment) {
			Iterator<Element> iterator = agents.iterator();
			while (iterator.hasNext()) {
				Element agent = iterator.next();
				if (element.attributeValue("uuid").equals(
						agent.attributeValue("uuid"))) {
					iterator.remove();
				}
			}
		}
	}

	public Element job(String jobName) {
		return (Element) root()
				.selectSingleNode(
						String.format(
								"/cruise/pipelines/pipeline/stage/jobs/job[@name='%s']",
								jobName));
	}

	public Element pipeline(String name) {
		return (Element) root().selectSingleNode(
				String.format("/cruise/pipelines/pipeline[@name='%s']", name));
	}

	public Element stage(String stageName) {
		return (Element) root().selectSingleNode(
				String.format("/cruise/pipelines/pipeline/stage[@name='%s']",
						stageName));
	}

	public void addResourceToJob(String resource, String jobName) {
		Element resources = findOrCreateElement(job(jobName), "resources");
		if (resources.selectSingleNode("/resource[text()='" + resource + "']") == null) {
			DefaultElement resourceElement = new DefaultElement("resource");
			resourceElement.addText(resource);
			resources.add(resourceElement);
		}

	}

	public void setRunOnAllAgentsForJobTo(String pipelineName, String jobName, boolean runOnAllAgents) {
		Element pipeline = pipelineWithName(pipelineName);
		List<Element> jobs = pipeline.selectNodes(String.format("stage/jobs/job[@name='%s']", jobName));
		assertThat("Job " + jobName + " does not exist", jobs.isEmpty(), is(false));
		for (Element job : jobs) {
			job.addAttribute("runOnAllAgents", Boolean.toString(runOnAllAgents));
		}
	}

	public void setRunInstanceCountForJobTo(String pipelineName, String jobName, int runInstanceCount) {
		Element pipeline = pipelineWithName(pipelineName);
		List<Element> jobs = pipeline.selectNodes(String.format("stage/jobs/job[@name='%s']", jobName));
		assertThat("Job " + jobName + " does not exist", jobs.isEmpty(), is(false));
		for (Element job : jobs) {
			if (runInstanceCount == 0) {
				job.remove(job.attribute("runInstanceCount"));
			} else {
				job.addAttribute("runInstanceCount", Integer.toString(runInstanceCount));
			}
		}
	}

	public void setTimerWithSpec(String pipelineName, String spec) {
		Element pipeline = pipelineWithName(pipelineName);
		Element timer = (Element) pipeline.selectSingleNode("timer");
		if (timer == null) {
			timer = new DefaultElement("timer");
			pipeline.elements().add(0, timer);
		}
		timer.setText(spec);
	}

	public void addVariableToJob(String name, String value, String jobName) {
		addEnvironmentVariable(name, value, job(jobName), 0);
	}

	public void addVariableToPipeline(String name, String value,
			String pipelineName) {
		Element pipelineNode = pipeline(pipelineName);
		int index = 0;
		index = (pipelineNode.selectSingleNode("params") == null) ? index
				: index + 1;
		index = (pipelineNode.selectSingleNode("trackingtool") == null) ? index
				: index + 1;
		index = (pipelineNode.selectSingleNode("mingle") == null) ? index
				: index + 1;
		index = (pipelineNode.selectSingleNode("timer") == null) ? index
				: index + 1;
		addEnvironmentVariable(name, value, pipelineNode, index);
	}

	public void addVariableToStage(String name, String value, String stageName) {
		Element stageNode = stage(stageName);
		int index = (stageNode.selectSingleNode("approval") == null) ? 0 : 1;
		addEnvironmentVariable(name, value, stageNode, index);
	}

	public void addVariableToEnvironment(String name, String value,
			String environmentName) {
		addEnvironmentVariable(name, value, environment(environmentName), 0);
	}

	private void addEnvironmentVariable(String name, String value,
			Element parent, int index) {
		Element variables = findOrCreateAt(parent, index,
				"environmentvariables");
		Element variableNode = new DefaultElement("variable").addAttribute(
				"name", name);
		variableNode.add(new DefaultElement("value").addText(value));
		variables.add(variableNode);
	}

	public static Element findOrCreateAt(Element parent, int index,
			String childName) {
		Element found = (Element) parent.selectSingleNode(childName);
		if (found == null) {
			List<Element> children = parent.elements();
			if (children.size() == 0) {
				found = new DefaultElement(childName);
				parent.add(found);
				return found;
			}
			for (Element child : children) {
				parent.remove(child);
				child.detach();
			}
			for (int i = 0; i < children.size(); i++) {
				Element child = children.get(i);
				if (i == index) {
					found = new DefaultElement(childName);
					parent.add(found);
				}
				parent.add(child);
			}
			// Adding to the end
			if (found == null) {
				found = new DefaultElement(childName);
				parent.add(found);
			}
		}
		return found;
	}

	private Element findOrCreateElement(Element parent, String name) {
		Element childNode = (Element) parent.selectSingleNode(name);
		if (childNode == null) {
			parent.add(childNode = new DefaultElement(name));
		}
		return childNode;
	}

	public void addPipeLineToEnvironment(String pipelineName,
			String environmentName) {
		Element environment = environment(environmentName);
		findOrCreateAt(environment, environment.elements().size(), "pipelines")
				.add(new DefaultElement("pipeline").addAttribute("name",
						pipelineName));
	}

	private Element pipelineWithName(String pipelineName) {
		Element pipeline = (Element) root().selectSingleNode(
				String.format(
						"/cruise/pipelines/pipeline[contains(@name, '%s')]",
						pipelineName));
		bombIfNull(pipeline, "Pipeline with name " + pipelineName
				+ " now found");
		return pipeline;
	}

	public void removeTimer(String pipelineName) {
		Element pipeline = pipelineWithName(pipelineName);
		Element timer = (Element) pipeline.selectSingleNode("timer");
		if (timer != null) {
			pipeline.remove(timer);
		}
	}

	public void setFirstStageOfPipelineToAutoApproval(String currentPipeline) {
		enableAutoUpdate(currentPipeline);
		Element approval = (Element) root().selectSingleNode(
				String.format(
						"//pipelines/pipeline[@name='%s']/stage[1]/approval",
						currentPipeline));
		if (approval != null) {
			approval.detach();
		}
	}

	public void enableAutoUpdate(String currentPipeline) {
		List<Element> materials = materialsForPipeline(currentPipeline);
		for (Element material : materials) {
			Attribute autoUpdate = material.attribute("autoUpdate");
			if (autoUpdate != null) {
				material.remove(autoUpdate);
			}
		}
	}

	public Element getPasswordFile() {
		return (Element) root().selectSingleNode("//passwordFile");
	}

	public Element getLdap() {
		return (Element) root().selectSingleNode("//ldap");
	}

	public Element getMailHost() {
		return (Element) root().selectSingleNode("//mailhost");
	}

	public void removeMaterialFromPipeline(String materialName,
			String pipelineName) {
		material(pipelineName, materialName).detach();
	}

	private Element material(String pipelineName, String materialName) {
		return (Element) pipelineElement(pipelineName).selectSingleNode(
				String.format(".//*[@materialName='%s']", materialName));
	}


	public Element getMaterial(String pipelineName, String materialName) {
		return material(pipelineName, materialName);
	}

	public void changeDestinationOfMaterial(String materialName,
			String pipelineName, String newDestination) {
		material(pipelineName, materialName).attribute("dest").setText(
				newDestination);
		((Element) job("svn-pipeline-job").selectSingleNode(".//ant"))
				.attribute("workingdir").setText(newDestination);
	}

	public void changeUrlOfMaterial(String materialName, String pipelineName,
			String newUrl) {
		material(pipelineName, materialName).attribute("url").setText(newUrl);
	}

	public void pipelineAutoUpdate(String pipelineName) {
		List<Element> materials = materialsForPipeline(pipelineName);
		for (Element material : materials) {
			Attribute autoUpdate = material.attribute("autoUpdate");
			if (autoUpdate == null) {
				material.add(new DefaultAttribute("autoUpdate", "true"));
			} else {
				autoUpdate.setValue("true");
			}
		}
	}

	public void removeExternalsFor(String pipelineName, String materialName) {
		Element material = material(pipelineName, materialName);
		if (material.attribute("checkexternals") != null) {
			material.remove(material.attribute("checkexternals"));
		}
	}

	public void addTestArtifactToJob(String job, String source, String dest) {
		Element test = new DefaultElement("test");
		test.addAttribute("src", source);
		test.addAttribute("dest", dest);

		findOrCreateElement(job(job), "artifacts").add(test);
	}

	public void removeLockFromPipeline(String pipelineName) {
		pipelineWithName(pipelineName).attribute("isLocked").setText("false");
	}

	public void addAdminUser(String user) {
		Element adminNode = (Element) root().selectSingleNode(
				"//security//admins");
		Element userElement;
		if (adminNode.selectSingleNode("user") != null) {
			userElement = new DefaultElement("user");
			adminNode.add(userElement);
		} else {
			userElement = findOrCreateAt(adminNode, 0, "user");
		}
		userElement.setText(user);
	}

	public void removeAdminUser(String user) {
		Element userNode = (Element) root().selectSingleNode(
				String.format("//security//admins/user[.='%s']", user));
		if (userNode != null) {
			userNode.detach();
		}
	}

	public String firstStage(String pipelineName) {
		Element pipeline = pipelineWithName(pipelineName);
		String template = pipeline.attributeValue("template");
		Element stage = (Element) (StringUtils.isNotEmpty(template) ? root()
				.selectSingleNode(
						String.format("templates/pipeline[@name='%s']/stage",
								template)) : pipeline.selectSingleNode("stage"));
		return stage.attributeValue("name");

	}

	public void makeMaterialNonExistantMaterial(String pipelineName) {
		Element material = materialsForPipeline(pipelineName).get(0);
		material.attribute("url").setText("non-existant");
	}

	public void makeAllMaterialsNonExistantMAterials(String pipelineName) {
		int numberOfMaterials = materialsForPipeline(pipelineName).size();
		for (int i = 0; i < numberOfMaterials; i++) {
			Element material = materialsForPipeline(pipelineName).get(i);
			if (material.getName().equalsIgnoreCase("p4")) {
				material.attribute("port").setText("nonexistant:6578");
			} else {
				material.attribute("url").setText("non-existant");
			}

		}
	}

	public void changeArtifactLocation(String artifactLocation) {
		Element artifact = (Element) root().selectSingleNode("/cruise/server");
		artifact.attribute("artifactsdir").setText(artifactLocation);
	}

	public void removeDependency(String actualPipeline,
			String upstreamDependency) {
		List<Element> materials = materialsForPipeline(actualPipeline);
		for (Element material : materials) {
			if (upstreamDependency.equals(material
					.attributeValue("pipelineName"))) {
				material.detach();
			}
		}
	}

	public Element changeScm(String pipelineName, String oldScm, String newScm) {
		removeMaterialFromPipeline(oldScm, pipelineName);
		Element element = findOrCreateElement(
				materialsTagForPipeline(pipelineName), newScm);
		element.addAttribute("url", "dummy");
		return element;
	}

	public boolean containsGroups(String... groups) {
		for (String group : groups) {
			try {
				pipelinesFor(group);
			} catch (RuntimeException notPresent) {
				return false;
			}
		}
		return true;
	}

	public boolean containsPipelines(String... pipelines) {
		for (String pipeline : pipelines) {
			try {
				pipelineElement(pipeline);
			} catch (RuntimeException notPresent) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<String> pipelinesFor(String... groups) {
		ArrayList<String> pipelines = new ArrayList<String>();
		for (String group : groups) {
			try {
				List names = pipelinesFor(group);
				for (Object object : names) {
					pipelines.add(((DefaultElement) object)
							.attributeValue("name"));
				}
			} catch (RuntimeException notPresent) {
				return new ArrayList<String>();
			}
		}
		return pipelines;

	}

	public void changeLdapUriTo(String newLdapUri) {
		Element ldap = (Element) getNode("/cruise/server/security/ldap");
		ldap.attribute("uri").setValue(newLdapUri);
	}

	public void changePasswordPathTo(String passwordFilePath) {
		Element passwordFile = (Element) getNode("/cruise/server/security/passwordFile");
		passwordFile.attribute("path").setValue(passwordFilePath);
	}

	public Node getNode(String element) {
		return root().selectSingleNode(element);
	}

	public void addSiteUrl(String url) {
		serverTag().add(new DefaultAttribute("siteUrl", url));
	}

	public void addSecureSiteUrl(String url) {
		serverTag().add(new DefaultAttribute("secureSiteUrl", url));
	}

	private Element serverTag() {
		return (Element) root().selectSingleNode("//server");
	}

	public void removeSiteUrl() {
		removeAttribute(serverTag(), "siteUrl");
	}

	private void removeAttribute(Element serverTag, String attributeName) {
		serverTag.remove(serverTag.attribute(attributeName));
	}

	public void removeSecureSiteUrl() {
		removeAttribute(serverTag(), "secureSiteUrl");
	}

	public boolean hasStage(String pipelineName, String stageName) {
		return stageElement(pipelineName, stageName) != null;
	}

	public void modifyPipeline(String pipelineName, String node,
			String attribute, String attrValue) {

		Element pipeline = pipelineElement(pipelineName);
		Element trackingTool = findOrCreateAt(pipeline, 0, node);
		Attribute linkAttr = trackingTool.attribute(attribute);
		if (linkAttr == null) {
			trackingTool.add(new DefaultAttribute(attribute, attrValue));
		} else {
			linkAttr.setValue(attrValue);
		}
	}

	public boolean isUserAuthorizedOnStage(String pipelineName,
			String stageName, String userName) {
		return stageAuthorizedEntity(pipelineName, stageName, userName, "user");
	}

	private boolean stageAuthorizedEntity(String pipelineName,
			String stageName, String userName, String userOrRole) {
		Element stage = getStage(pipelineName, stageName);
		for (Element element : (List<Element>) stage.selectNodes(String.format(
				".//authorization/%s", userOrRole))) {
			if (element.getTextTrim().equals(userName)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isRoleAuthorizedOnStage(String pipelineName,
			String stageName, String roleName) {
		return stageAuthorizedEntity(pipelineName, stageName, roleName, "role");
	}

	public void addGroupAdmin(String groupName, String username) {
		addPermissionForGroup("user", username, groupName, "admins");
	}

	public void hasMaterialWithAttribs(String pipelineName,
			String materialType, String materialName, String... nameValues) {
		Element material = material(pipelineName, materialName);
		if (material == null) {
			throw new RuntimeException("Could not find material of type '"
					+ materialType + "' and name '" + materialName + "'");
		}
		for (String nameValue : nameValues) {
			String[] nameAndValue = nameValue.split(">");
			String value = nameAndValue[1];
			if("tfs".equals(materialType) && "url".equals(nameAndValue[0])){
				value = TfsServer.getUrl() + value;
			}
			Attribute attrib = material.attribute(nameAndValue[0]);
			if (attrib == null || !attrib.getText().equals(value)) {
				throw new RuntimeException("Could not find attribute pair: "
						+ nameValue);
			}
		}
	}

	public void removeJob(String pipelineName, String stageName, String jobName) {
		Node job = getNode(String.format(
				"//pipeline[@name='%s']//stage[@name='%s']//job[@name='%s']",
				pipelineName, stageName, jobName));
		job.getParent().remove(job);
	}

	public void removeEnvironments() {
		Node environmentsNode = root().selectSingleNode("//environments");
		if (environmentsNode != null) {
			environmentsNode.getParent().remove(environmentsNode);
		}
	}

	public void removeTemplates() {
		Node templatesNode = root().selectSingleNode("//templates");
		if (templatesNode != null) {
			templatesNode.getParent().remove(templatesNode);
		}
	}

	public String getArtifactsDir() {
		return serverTag().attribute("artifactsdir").getText();
	}

	public String urlForMaterial(String pipelineName, String materialName) {
		return material(pipelineName, materialName).attribute("url").getValue();
	}

	public void addParameter(String pipelineName, String parameterName,
			String value) {
		Element pipelineElement = pipelineElement(pipelineName);
		Element params = findOrCreateAt(pipelineElement, 0, "params");
		params.add(paramElement(parameterName, value));
	}

	private DefaultElement paramElement(String parameterName, String value) {
		DefaultElement elem = new DefaultElement("param");
		elem.addAttribute("name", parameterName);
		elem.setText(value);
		return elem;
	}

	public Element getGroup(String groupName) {
		return (Element) root().selectSingleNode(
				String.format("//pipelines[@group=\"%s\"]", groupName));
	}

	public void addSecurityWithPasswordFile(String passwordFilePath,
			String adminUsers) {
		Element passwordFileTag = new DefaultElement("passwordFile");
		passwordFileTag.addAttribute("path", passwordFilePath);

		Element adminsTag = new DefaultElement("admins");
		String[] adminUserList = adminUsers.split(",");
		for (String adminUser : adminUserList) {
			adminsTag.add(new DefaultElement("user").addText(adminUser.trim()));
		}

		Element securityTag = new DefaultElement("security");
		securityTag.add(passwordFileTag);
		securityTag.add(adminsTag);

		serverTag().add(securityTag);
	}

	public void addSecurityWithPasswordFileOnly(String passwordFilePath) {
		Element passwordFileTag = new DefaultElement("passwordFile");
		passwordFileTag.addAttribute("path", passwordFilePath);
		Element securityTag = new DefaultElement("security");
		securityTag.add(passwordFileTag);
		serverTag().add(securityTag);
	}

	public void replaceServerId(String serverId) {
		serverTag().setAttributeValue("serverId", serverId);
	}

	public String getCommandRepositoryLocation() {
		return serverTag().attribute("commandRepositoryLocation").getText();
	}

	public void createPipelineAsFirstPipelineInGroup(String pipelineName,
			String groupName) {
		Element groupContents = getGroup(groupName);
		ArrayList<Element> pipelines = new ArrayList<Element>();
		while (groupContents.elementIterator().hasNext()) {
			Element pipelineElement = (Element) groupContents.elementIterator()
					.next();
			pipelines.add(pipelineElement);
			groupContents.remove(pipelineElement);
		}

		createASimpleGitPipeline(pipelineName);

		for (Element e : pipelines) {
			groupContents.add(e);
		}
	}

	public void createASimpleGitPipeline(String pipelineName) {
		createPipeline(pipelineName, "git", "gitName", "url:git", "destDir");
	}

	public void updateValueOfKeyToForRepoWithId(String key, String value,
			String repoId) {
		Element repo = findRepo(repoId);
		Node userKey = repo.selectSingleNode(String.format(
				"configuration/property/key[text()='%s']", key));
		userKey.getParent().selectSingleNode("value").setText(value);
	}

	private boolean hasArguement(String arguement) {
		return (arguement != "") ? true : false;

	}

	public void addCustomCommandTaskWithInATemplate(String templateName,
			String stageName, String jobName, String newCommand,
			String givenArguement) {
		Element customCommand = hasArguement(givenArguement) ? customCommandWithArguments(
				newCommand, givenArguement) : customCommand(newCommand);
		tasksWithInTemplate(templateName, stageName, jobName)
				.add(customCommand);
	}

	private Element tasksWithInTemplate(String templateName, String stageName,
			String jobName) {
		return (Element) jobWithInTemplate(templateName, stageName, jobName)
				.selectSingleNode("tasks");
	}

	private Element stageWithInTemplate(String templateName, String stageName) {

		return (Element) template(templateName).selectSingleNode(
				format("stage[@name=\"%s\"]", stageName));

	}

	private Element jobWithInTemplate(String templateName, String stageName,
			String jobName) {
		return (Element) stageWithInTemplate(templateName, stageName)
				.selectSingleNode(format("jobs/job[@name=\"%s\"]", jobName));
	}

	private Element template(String templateName) {
		return (Element) root().selectSingleNode(
				String.format("//templates/pipeline[@name=\"%s\"]",
						templateName));

	}

	private Element customCommandWithArguments(String newCommand,
			String givenArguement) {
		Element command = null;
		Element arguement = new DefaultElement("arg");

		arguement.setText(givenArguement);
		command = customCommand(newCommand);
		command.add(arguement);
		return command;
	}

	private Element customCommand(String newCommand) {
		Element customCommand = new DefaultElement("exec");
		customCommand.addAttribute("command", newCommand);
		return customCommand;
	}

	public void removeCustomCommandTaskWithInATemplate(String templateName,
			String stageName, String jobName, String command, String arguement) {
		Element tasks = (Element) tasksWithInTemplate(templateName, stageName,
				jobName);
		String searchString = hasArguement(arguement) ? customCommandSearchStringWithArguments(
				command, arguement)
				: customCommandSearchStringWithOutArguments(command);
		Element customCommand = (Element) tasks.selectSingleNode(searchString);
		customCommand = hasArguement(arguement) ? customCommand.getParent()
				: customCommand;
		customCommand.detach();

	}

	private String customCommandSearchStringWithArguments(String command,
			String arguement) {
		return String.format("./exec[@command=\"%s\"]/arg[text()=\"%s\"]",
				command, arguement);
	}

	private String customCommandSearchStringWithOutArguments(String command) {
		return String.format("./exec[@command=\"%s\"]", command);
	}

	public void deletePackageRepo(String repoId){
		findRepo(repoId).detach();
	}

	private Element findRepo(String repoId){
		return (Element) root().selectSingleNode(
				String.format("//repositories/repository[@id='%s']", repoId));
	}

	public void addANewPackageDefinitionToRepository(String packageId,String packageSpec, String repoId) {
		Element repo = findRepo(repoId);
		Element packagesForRepo = (Element) repo.selectSingleNode("packages");
		Element newPackage =  packagesForRepo.addElement("package");
		newPackage.addAttribute("name", packageId);
		newPackage.addAttribute("id", packageId);
		Element packageSpecProperty = newPackage.addElement("configuration").addElement("property");
		packageSpecProperty.addElement("key").setText("PACKAGE_SPEC");
		packageSpecProperty.addElement("value").setText(packageSpec);
	}

	public void addPackageMaterialToPipeline(String packageId, String pipelineName) {
		Element pipeline = getPipeline(pipelineName);
		Element materials = (Element) pipeline.selectSingleNode("materials");
		materials.addElement("package").addAttribute("ref", packageId);
	}

	public void invertFileFilter(String pipelineName) {
		List<Element> materials = materialsForPipeline(pipelineName);
		for (Element material : materials) {
			Attribute invertFile = material.attribute("invertFilter");
			if (invertFile == null) {
				material.add(new DefaultAttribute("invertFilter", "true"));
			} else {
				invertFile.setValue("true");
			}
		}
	}
}
