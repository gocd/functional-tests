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

AddNewPipelineGroupAdmin
========================

Setup of contexts
* Group admin security configuration - setup
* Login as "group1Admin" - setup
* Capture go state "AddNewPipelineGroupAdmin" - setup

AddNewPipelineGroupAdmin
------------------------

tags: 5172, 5041, Clicky Admin

* Force navigate to add new pipeline

make sure group admins don't see autocomplete for group
* Verify that group name autocomplete is not present
* Verify that group name select box is present and contains "group1"
* Set "pipeline_group[pipeline][name](text_field): some_pipeline_name"
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
* Save pipeline "some_pipeline_name" successfully

* Verify created successfully

* For pipeline "some_pipeline_name"
* Verify pipeline "some_pipeline_name" belongs to group "group1"
* Verify has tag "git" "@url: http://git.url, @branch: some_branch"
* Verify has tag "ant" "@buildfile: build.xml, @target: compile, @workingdir: src"

* On Admin page
* Open "Pipelines" tab

* Click clone button for pipeline "some_pipeline_name"

* Enter pipeline name "cloned-some-pipeline-name"
* Save for success

* Verify "Cloned successfully." message is displayed
* Verify pipeline "cloned-some-pipeline-name" is paused with message "Paused by group1Admin"

* On Admin page
* Open "Pipelines" tab

* Verify "group1" has pipelines "some_pipeline_name, cloned-some-pipeline-name"
* Delete "cloned-some-pipeline-name"
* Verify "group1" has pipelines "some_pipeline_name"





Teardown of contexts
* Capture go state "AddNewPipelineGroupAdmin" - teardown
* Login as "group1Admin" - teardown
* Group admin security configuration - teardown


