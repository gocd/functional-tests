// --GO-LICENSE-START--
// Copyright 2015 ThoughtWorks, Inc.
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//    http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --GO-LICENSE-END--

EditPipelineGroups
==================

Setup of contexts
* Group admin security configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline1,pipeline2,pipeline4,pipeline5,pipeline-with-group-and-stage-security,pipeline-down" - setup
* Capture go state "EditPipelineGroups" - setup

EditPipelineGroups
------------------

tags: 4369, Clicky Admin, pipeline group

* Open "Pipelines" tab

* Verify groups "group1,group2,groupWithStageSecurity" are visible
* Verify "group1" has pipelines "pipeline1,pipeline4"
* Verify "group2" has pipelines "pipeline2,pipeline-down"
* Verify "group4" has pipelines "pipeline5"
* Verify "groupWithStageSecurity" has pipelines "pipeline-with-group-and-stage-security"
* Verify can delete "pipeline4,pipeline5,pipeline-with-group-and-stage-security,pipeline-down"
* Verify cannot delete "pipeline1,pipeline2"
* Delete "pipeline4"
* Verify "group1" does not have pipelines "pipeline4"
* Delete "pipeline5"
* Verify "group4" has message "No pipelines associated with this group"
* Click to create a new pipeline to group "groupWithStageSecurity"

* Set "pipeline_group[pipeline][name](text_field): new-pipeline"
* Verify has field named "pipeline_group[group]" with value "groupWithStageSecurity"
* Go next from settings
* Select material type as "Git"
* Set "pipeline_group[pipeline][materials][GitMaterial][url](text_field): http://git.url"
* Set "pipeline_group[pipeline][materials][GitMaterial][branch](text_field): some_branch"
* Go next from materials
* Set "pipeline_group[pipeline][stage][name](text_field): some_stage"
* Set "pipeline_group[pipeline][stage][jobs][][name](text_field): some_job"
* Select task type as "Ant"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][ant][buildFile](text_field): build.xml"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][ant][target](text_field): compile"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][ant][workingDirectory](text_field): src"
* Save pipeline "new-pipeline" successfully

* On Admin page
* Open "Pipelines" tab

Verify Edit button
* Verify "groupWithStageSecurity" has pipelines "new-pipeline"
* Verify that the edit button for pipeline "pipeline1" is a link for edit pipeline

* Logout and login as "group1Admin"

* On Admin page
* Open "Pipelines" tab

* Verify groups "group1" are visible
* Verify groups "group2,groupWithStageSecurity" are not visible
* Verify that move button is not present for "pipeline1"
* Verify that "pipeline1" cannot be moved from group "group1" to group "group2"
* Verify that "pipeline1" cannot be moved from group "group1" to group "groupWithStageSecurity"

Move pipeline  from one group to another

* Logout and login as "admin"

* On Admin page
* Open "Pipelines" tab

* Click move button for pipeline "pipeline2"
* Verify that "pipeline2" cannot be moved from group "group2" to group "group2"
* Move pipeline "pipeline2" from group "group2" to group "group1"
* Verify "group2" has pipelines "pipeline-down"
* Verify "group1" has pipelines "pipeline1, pipeline2"





Teardown of contexts
* Capture go state "EditPipelineGroups" - teardown
* Using pipeline "pipeline1,pipeline2,pipeline4,pipeline5,pipeline-with-group-and-stage-security,pipeline-down" - teardown
* Login as "admin" - teardown
* Group admin security configuration - teardown


