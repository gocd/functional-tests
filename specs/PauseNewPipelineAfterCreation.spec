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

PauseNewPipelineAfterCreation
=============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "PauseNewPipelineAfterCreation" - setup

PauseNewPipelineAfterCreation
-----------------------------

tags: 4812, Clicky Admin, Admin

* Force navigate to add new pipeline

* Enter pipeline name "newpipeline" - Already on new pipeline wizard
* Set "pipeline_group[group](text_field): group1"
* Go next from settings
* Select material type as "Git"
* Set "pipeline_group[pipeline][materials][GitMaterial][url](text_field): http://git.url"
* Set "pipeline_group[pipeline][materials][GitMaterial][branch](text_field): some_branch"
* Go next from materials
* Set "pipeline_group[pipeline][stage][name](text_field): defaultStage"
* Set "pipeline_group[pipeline][stage][jobs][][name](text_field): defaultJob"
* Select task type as "Ant"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][ant][buildFile](text_field): build.xml"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][ant][target](text_field): compile"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][ant][workingDirectory](text_field): src"
* Save pipeline "newpipeline" successfully

* Verify "Pipeline successfully created." message is displayed
* Verify pipeline "newpipeline" is paused with message "Paused by anonymous"
* Open material listing page

* Verify pipeline "newpipeline" is paused with message "Paused by anonymous"
* Open stage listing page - Already on material listing page

* Verify pipeline "newpipeline" is paused with message "Paused by anonymous"
* Open stage "defaultStage"

* Verify pipeline "newpipeline" is paused with message "Paused by anonymous"
* Open jobs

* Open job "defaultJob"

* Verify pipeline "newpipeline" is paused with message "Paused by anonymous"
* Open tasks

* Verify pipeline "newpipeline" is paused with message "Paused by anonymous"
* Unpause pipeline "newpipeline"

* On Pipeline Dashboard Page
* Looking at pipeline "newpipeline"
* Verify pipeline is unpaused




Teardown of contexts
* Capture go state "PauseNewPipelineAfterCreation" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


