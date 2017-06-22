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

import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CruiseConfigVerification {

	private final Configuration configuration;
	private final ScenarioState state;

	public CruiseConfigVerification(Configuration configuration, ScenarioState state) {
		this.configuration = configuration;
		this.state = state;
 	}

	@com.thoughtworks.gauge.Step("For pipeline <pipelineName>")
	public void forPipeline(String pipelineName) throws Exception {
		state.usingPipeline(pipelineName);
	}

	@com.thoughtworks.gauge.Step("Verify that stage <stageName> exists")
	public void verifyThatStageExists(String stageName) throws Exception {
		CruiseConfigDom dom = configuration.provideDomAsAdmin();
		Assert.assertThat(dom.hasStage(state.currentRuntimePipelineName(), stageName), is(true));
	}

	@com.thoughtworks.gauge.Step("Verify <taskType> task <attributes> with arg <arg> exists for <jobName> in <stageName>")
	public void verifyTaskWithArgExistsForIn(String taskType, String attributes, String arg, String jobName, String stageName) throws Exception {
        List tasks = getJob(state.currentRuntimePipelineName(), jobName, stageName).selectNodes(String.format("./tasks/%s%s/arg[.='%s']", taskType, conditions(attributes), arg));
        Assert.assertThat(tasks.isEmpty(), is(false));
    }

	@com.thoughtworks.gauge.Step("Verify <taskType> task with attributes <attributes> exists for <jobName> in <stageName>")
	public void verifyTaskWithAttributesExistsForIn(String taskType, String attributes, String jobName, String stageName) {
	    List tasks = getJob(state.currentRuntimePipelineName(), jobName, stageName).selectNodes(String.format("./tasks/%s%s", taskType, conditions(attributes)));
	    Assert.assertThat(tasks.isEmpty(), is(false));
	}
	
	public void verifyTaskWithAttributesExistsForInIn(String taskType, String attributes, String jobName, String stageName, String pipelineName) {
        List tasks = getJob(pipelineName, jobName, stageName).selectNodes(String.format("./tasks/%s%s", taskType, conditions(attributes)));
        Assert.assertThat(tasks.isEmpty(), is(false));
    }
	
	@com.thoughtworks.gauge.Step("Verify <artifactType> artifact with <attributes> exists for <jobName> in <stageName>")
	public void verifyArtifactWithExistsForIn(String artifactType, String attributes, String jobName, String stageName) throws Exception {
		List artifacts = getJob(state.currentRuntimePipelineName(),jobName, stageName).selectNodes(String.format("./artifacts/%s%s", artifactType, conditions(attributes) ));
		Assert.assertThat(artifacts.isEmpty(), is(false));		
	}

	
    private Element getJob(String pipelineName, String jobName, String stageName) {
        CruiseConfigDom dom = configuration.provideDomAsAdmin();
        return dom.getJob(pipelineName, stageName, jobName);
    }
    
    private List<Element> getMaterials() {
        CruiseConfigDom dom = configuration.provideDomAsAdmin();
        return dom.materialsForPipeline(state.currentRuntimePipelineName());
    }

    @com.thoughtworks.gauge.Step("Verify on cancel <taskType> task <attributes> with arg <arg> exists for task <taskIndex> of <jobName> in <stageName>")
	public void verifyOnCancelTaskWithArgExistsForTaskOfIn(String taskType, String attributes, String arg, Integer taskIndex, String jobName, String stageName) throws Exception {
        List tasks = getJob(state.currentRuntimePipelineName(), jobName, stageName).selectNodes(String.format("./tasks/*[%s]/oncancel/%s%s/arg[.='%s']", taskIndex, taskType, conditions(attributes), arg));
        Assert.assertThat(tasks.isEmpty(), is(false));
    }

	@com.thoughtworks.gauge.Step("Verify pipeline group <pipelineGroup> has <type> <name> with <permissions> permissions")
	public void verifyPipelineGroupHasWithPermissions(String pipelineGroup, String type, String name, String permissions) throws Exception {
		assertElementExists(pipelineGroup, type, name, permissions, true);
	}
	
	@com.thoughtworks.gauge.Step("Verify pipeline group <pipelineGroup> does not have <type> <name> with <permissions> permissions")
	public void verifyPipelineGroupDoesNotHaveWithPermissions(String pipelineGroup, String type, String name, String permissions) throws Exception {
		assertElementExists(pipelineGroup, type, name, permissions, false);
	}

	private void assertElementExists(String pipelineGroup, String type, String name, String permissions, boolean elementExists) {
		CruiseConfigDom dom = configuration.provideDomAsAdmin();
		String[] perms = permissions.split(",");
		for (String perm : perms) {
			Node node = dom.getNode(String.format("//pipelines[@group=\"%s\"]/authorization/%s/%s[.='%s']", pipelineGroup, perm.trim(), type, name));
			if (elementExists) {
				assertThat(node, Matchers.notNullValue());
			} else {
				assertThat(node, Matchers.nullValue());
			}
		}
	}

	private static Pattern TUPLE_FORM = Pattern.compile("^\\s*(.+?)\\s*:\\s*(.+?)\\s*$");
    
    private String conditions(String attributes) {
        StringBuilder builder = new StringBuilder();
        for (String attributeValueTuple : split(attributes)) {
            Matcher matcher = TUPLE_FORM.matcher(attributeValueTuple);
            if (matcher.matches()) {
                String attributeValue = matcher.group(2);
                attributeValue = state.expand(attributeValue);
                builder.append("[").
                        append(matcher.group(1)).
                        append("=").
                        append("'").append(attributeValue).append("'").
                        append("]");
            }
        }
        return builder.toString();
    }

	@com.thoughtworks.gauge.Step("Verify task <taskIndex> of <jobName> in <stageName> runs if <runIfs>")
	public void verifyTaskOfInRunsIf(Integer taskIndex, String jobName, String stageName, String runIfs) throws Exception {
        Element job = getJob(state.currentRuntimePipelineName(), jobName, stageName);
        for (String condition : split(runIfs)) {
            List matches = job.selectNodes(String.format("./tasks/*[%s]/runif[@status='%s']", taskIndex, condition));
            assertThat(matches.size(), is(1));
        }
	}

    private String[] split(String runIfs) {
        String[] strings = runIfs.split("\\s*,\\s*");
        List<String> fragments = new ArrayList<String>();
        for (String string : strings) {
            fragments.add(string.trim());
        }
        return fragments.toArray(new String[0]);
    }

	@com.thoughtworks.gauge.Step("Verify no <taskType> task with <attributes> exists in <jobName> under <stageName>")
	public void verifyNoTaskWithExistsInUnder(String taskType, String attributes, String jobName, String stageName) throws Exception {
		Assert.assertThat(getJob(state.currentRuntimePipelineName(), jobName, stageName).selectNodes(String.format("./tasks/%s%s", taskType, conditions(attributes))).isEmpty(), is(true));
	}

    @com.thoughtworks.gauge.Step("Verify stage <stageName> has attribute <attribute> set to <value> where null means <defaultValue>")
	public void verifyStageHasAttributeSetToWhereNullMeans(String stageName, String attribute, String value, String defaultValue) throws Exception {
        CruiseConfigDom dom = configuration.provideDomAsAdmin();
        Element stage = dom.getStage(state.currentRuntimePipelineName(), stageName);
        Attribute attr = stage.attribute(attribute);
        if (attr == null) {
            assertThat(defaultValue, is(value));
        } else {
            assertThat(attr.getValue(), is(value));
        }
    }

    @com.thoughtworks.gauge.Step("Verify has tag <tagName> <attributes>")
	public void verifyHasTag(String tagName, String attributes) throws Exception {

        Assert.assertThat(currentPipeline().selectNodes(String.format(".//%s%s", tagName, conditions(attributes))).isEmpty(), is(false));
    }
    
    @com.thoughtworks.gauge.Step("Verify has tag with file path <tagName> <tagAttribute> <attributeFilePath> <attributes>")
	public void verifyHasTagWithFilePath(String tagName, String tagAttribute,
			String attributeFilePath, String attributes) throws Exception {
		attributeFilePath = new File(attributeFilePath).getPath();
		attributes = tagAttribute + attributeFilePath + attributes;
		verifyHasTag(tagName,attributes);
	}

    public void verifyHasParamWithValue(String paramName, String paramValue) throws Exception {
        Element pipeline = currentPipeline();
        Node paramVal = pipeline.selectSingleNode(".//param[@name='" + paramName + "']/.");
        assertThat(paramVal.getText(), is(paramValue));
    }

    private Element currentPipeline() {
        CruiseConfigDom dom = configuration.provideDomAsAdmin();
        return dom.getPipeline(state.currentPipeline());
    }

    @com.thoughtworks.gauge.Step("Verify uses template <templateName>")
	public void verifyUsesTemplate(String templateName) throws Exception {
        Node templateNode = currentPipeline().selectSingleNode("./@template");
        assertThat(templateNode.getText(), is(templateName));
    }

    @com.thoughtworks.gauge.Step("Verify pipeline <pipelineName> belongs to group <groupName>")
	public void verifyPipelineBelongsToGroup(String pipelineName, String groupName) throws Exception {
        CruiseConfigDom dom = configuration.provideDomAsAdmin();
        Element group = dom.getGroup(groupName);
        Assert.assertThat(group.selectNodes(String.format("./pipeline[@name=\"%s\"]", pipelineName)).isEmpty(), Is.is(false));
        
    }

	@com.thoughtworks.gauge.Step("Verify command repository location is <repoLocation>")
	public void verifyCommandRepositoryLocationIs(String repoLocation){
		CruiseConfigDom dom = configuration.provideDomAsAdmin();
		Assert.assertThat(dom.getCommandRepositoryLocation(), is(repoLocation));
	}

}
