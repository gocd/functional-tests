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

PipelineCreationConfigMergeConflict
===================================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Capture go state "PipelineCreationConfigMergeConflict" - setup

PipelineCreationConfigMergeConflict
-----------------------------------

tags: 7280

* Force navigate to add new pipeline

* Set "pipeline_group[pipeline][name](text_field): P1"
* Set "pipeline_group[group](text_field): PG1"
* Go next from settings
* Select material type as "Git"
* Set "pipeline_group[pipeline][materials][GitMaterial][url](text_field): http://git.url"
* Set "pipeline_group[pipeline][materials][GitMaterial][branch](text_field): some_branch"
* Go next from materials
* Set "pipeline_group[pipeline][stage][name](text_field): some_stage"
* Set "pipeline_group[pipeline][stage][jobs][][name](text_field): some_job"
* Select task type as "More..."
* Set "pipeline_group[pipeline][stage][jobs][][tasks][exec][command](text_field): ls"
* Save pipeline "P1" successfully

* On Admin page
* Force navigate to add new pipeline

* Remember current tab
* Set "pipeline_group[pipeline][name](text_field): P3"
* Set "pipeline_group[group](text_field): PG1"
* Go next from settings
* Select material type as "Git"
* Set "pipeline_group[pipeline][materials][GitMaterial][url](text_field): http://git.url"
* Set "pipeline_group[pipeline][materials][GitMaterial][branch](text_field): some_branch"
* Go next from materials
* Set "pipeline_group[pipeline][stage][name](text_field): some_stage"
* Set "pipeline_group[pipeline][stage][jobs][][name](text_field): some_job"
* Select task type as "More..."
* Set "pipeline_group[pipeline][stage][jobs][][tasks][exec][command](text_field): ls"

* For pipeline group "PG1"
* Create pipeline "P2" using template "simple-pass"

* Remember md5 - Already on new pipeline wizard
* Save pipeline
* Verify validation message "Save failed. Configuration file has been modified by someone else."
* Verify md5 is same - Already on new pipeline wizard
* Set "pipeline_group[pipeline][stage][jobs][][tasks][exec][command](text_field): ls"
* Save pipeline
* Verify validation message "Save failed. Configuration file has been modified by someone else."

* For pipeline group "PG1"
* Delete pipeline "P2" - Configure cruise using api

* On Admin page
* Force navigate to add new pipeline

* Set "pipeline_group[pipeline][name](text_field): P3"
* Set "pipeline_group[group](text_field): PG1"
* Go next from settings
* Select material type as "Git"
* Set "pipeline_group[pipeline][materials][GitMaterial][url](text_field): http://git.url"
* Set "pipeline_group[pipeline][materials][GitMaterial][branch](text_field): some_branch"
* Go next from materials
* Remember current tab
* Remember md5 - Already on new pipeline wizard
* Set "pipeline_group[pipeline][stage][name](text_field): some_stage"
* Set "pipeline_group[pipeline][stage][jobs][][name](text_field): some_job"
* Select task type as "More..."
* Save pipeline
* Verify inline validation message " This field is required."
* Verify md5 is same - Already on new pipeline wizard
* Set "pipeline_group[pipeline][stage][jobs][][tasks][exec][command](text_field): ls"

* Create pipeline "P2" as first pipeline in group "PG1"

* Save pipeline
* Verify validation message "Pipeline successfully created. The configuration was modified by someone else, but your changes were merged successfully."



Teardown of contexts
____________________
* Capture go state "PipelineCreationConfigMergeConflict" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


