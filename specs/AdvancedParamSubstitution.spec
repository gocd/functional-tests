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

AdvancedParamSubstitution
=========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "parent_pipeline" - setup
* With "2" live agents in directory "ParamSubstitution" - setup
* Capture go state "AdvancedParamSubstitution" - setup

AdvancedParamSubstitution
-------------------------

tags: 4207, Params, template, stage1, 6820

* Force navigate to add new pipeline with pipeline group "basic"

* Enter pipeline name "child_pipeline" - Already on new pipeline wizard
* Go next from settings
* Select material type as "Pipeline"
* Set pipeline and stage "parent_pipeline" "second_stage"
* Go next from materials
* Set "pipeline_group[pipeline][stage][name](text_field): stage_for_ant_target"
* Mark stage manual - On new pipeline wizard
* Set "pipeline_group[pipeline][stage][jobs][][name](text_field): job_for_ant_target"
* Select task type as "Ant"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][ant][buildFile](text_field): hg/dev/build.xml"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][ant][target](text_field): longsleep"
* Save pipeline "child_pipeline" successfully

* Verify created successfully
* Open parameters page

* Enter parameter "1" name "mingle_uri" and value "https://mingle09.thoughtworks.com"
* Enter parameter "2" name "mingle_project_identifier" and value "go"
* Enter parameter "3" name "mql_grouping_condition" and value "Status > 'In Dev'"
* Enter parameter "4" name "custom_command" and value "echo"
* Enter parameter "5" name "arguments" and value "this is a custom command output"
* Enter parameter "6" name "working_directory" and value "."
* Enter parameter "7" name "rake_build_file" and value "rakefile"
* Enter parameter "8" name "rake_target" and value "hello"
* Enter parameter "9" name "rake_directory" and value "git/rake"
* Enter parameter "10" name "ant_build_file" and value "build.xml"
* Enter parameter "11" name "ant_target" and value "longsleep"
* Enter parameter "12" name "ant_directory" and value "hg/dev"
* Enter parameter "13" name "label_template" and value "go-version-${COUNT}"
* Enter parameter "14" name "cron_time_specification" and value "0 0 12 * * ?"
* Enter material url for parameter "15" name "subversion_material_url" and material name "svn" associated with pipeline "parent_pipeline"
* Enter parameter "16" name "subversion_user_name" and value "first_user"
* Enter parameter "17" name "subversion_password" and value "password"
* Enter parameter "18" name "subversion_destination_directory" and value "svn"
* Enter material url for parameter "19" name "hg_material_url" and material name "hg" associated with pipeline "parent_pipeline"
* Enter parameter "20" name "hg_destination_directory" and value "hg"
* Enter material url for parameter "21" name "git_material_url" and material name "git" associated with pipeline "parent_pipeline"
* Enter parameter "22" name "git_destination_directory" and value "git"
* Enter parameter "23" name "value_to_be_used_by_env_variable" and value "substituted value"
* Click save
* Verify saved successfully

* Add environment variable "PIPELINE_VARIABLE_USING_VALUE_FROM_PARAM" with value "#{value_to_be_used_by_env_variable}" to pipeline "child_pipeline"
* Add environment variable "STAGE_VARIABLE_USING_VALUE_FROM_PARAM" with value "#{value_to_be_used_by_env_variable}" to stage "stage_for_ant_target"

* On Admin page
* Open "Pipelines" tab

* Edit pipeline "child_pipeline"

* Enter "#{cron_time_specification}" for cron time specifier
* Enter "#{label_template}" for label template
* Click save - Already On General Options Page
* Verify saved successfully - Already On General Options Page
* Go to project management page

* Select mingle option for tracking tool
* Enter "#{mingle_uri}" for mingle uRL
* Enter "#{mingle_project_identifier}" for mingle project identifier
* Enter "#{mql_grouping_condition}" for mQA grouping conditions
* Click save - Already On Project Management Page
* Go to materials page

* Open stage listing page - Already on material listing page

* Open new add stage details page

* Enter for stage name "deploy"
* Select "manual" for stage trigger
* Enter for job name "deploy_job"
* Select "More..." as task type
* Enter as command "#{custom_command}"
* Enter as arguments "#{arguments}"
* Add stage

* Verify that stage saved successfully
* Open stage "deploy"

* Open jobs

* Open job "deploy_job"

* Open tasks

* Open new task form "Rake"

* Set "task[buildFile](text_field):#{rake_build_file}" - Already on Task edit popup
* Set "task[target](text_field):#{rake_target}" - Already on Task edit popup
* Set "task[workingDirectory](text_field):#{rake_directory}" - Already on Task edit popup
* Save and verify saved successfully

* Open new task form "Ant"

* Set "task[buildFile](text_field):#{ant_build_file}" - Already on Task edit popup
* Set "task[target](text_field):#{ant_target}" - Already on Task edit popup
* Set "task[workingDirectory](text_field):#{ant_directory}" - Already on Task edit popup
* Save and verify saved successfully

* Open home for pipeline "child_pipeline"

* Open material listing page

* Open new subversion material creation popup

* Enter material name "svn_material_name"
* Enter url "#{subversion_material_url}"
* Enter username "#{subversion_user_name}"
* Enter password "#{subversion_password}"
* Enter destination directory "#{subversion_destination_directory}"
* Set poll for changes as "false"
* Click save - Already on Subversion Material Add Popup

* Open new git material creation popup

* Enter material name "git_material_name" - Already on git material creation popup
* Enter url "#{git_material_url}" - Already on git material creation popup
* Enter destination directory "#{git_destination_directory}" - Already on git material creation popup
* Set poll for changes as "false" - Already on git material creation popup
* Click save - Already on git material creation popup

* Verify that material saved successfully
* Open new mercurial material creation popup

* Enter material name "hg_material_name" - Already on Mercurial Material Creation Popup
* Enter url "#{hg_material_url}" - Already on Mercurial Material Creation Popup
* Enter destination directory "#{hg_destination_directory}" - Already on Mercurial Material Creation Popup
* Set poll for changes as "false" - Already on Mercurial Material Creation Popup
* Click save - Already on Mercurial Material Creation Popup

* Verify that material saved successfully

* On Pipeline Dashboard Page
* Looking at pipeline "parent_pipeline"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "first_stage" of run "1"

* Trigger stage "second_stage"

* On Pipeline Dashboard Page
* Looking at pipeline "parent_pipeline"
* Verify stage "2" is "Passed" on pipeline with label "1"
* Looking at pipeline "child_pipeline"
* Unpause pipeline
* Trigger pipeline

* Verify stage "1" is "Passed" on pipeline with label "go-version-1"
* Navigate to stage "stage_for_ant_target" of run "1"

* Trigger stage "deploy"

* On Pipeline Dashboard Page
* Looking at pipeline "child_pipeline"
* Verify stage "2" is "Passed" on pipeline with label "go-version-1"
* Navigate to stage "stage_for_ant_target" of run "1"

* Navigate to job "job_for_ant_target"

* Open console tab
* Verify console contains "setting environment variable 'PIPELINE_VARIABLE_USING_VALUE_FROM_PARAM' to value 'substituted value'"
* Verify console contains "setting environment variable 'STAGE_VARIABLE_USING_VALUE_FROM_PARAM' to value 'substituted value'"

* On Pipeline Dashboard Page
* Looking at pipeline "child_pipeline"
* Open trigger with options

* Switch to "Environment Variables" tab
* Override variable named "PIPELINE_VARIABLE_USING_VALUE_FROM_PARAM" with value "value_overridden_by_trigger_with_options"
* Trigger

* On Pipeline Dashboard Page
* Looking at pipeline "child_pipeline"
* Verify stage "1" is "Passed" on pipeline with label "go-version-2"
* Navigate to stage "stage_for_ant_target" of run "2"

* Trigger stage "deploy"

* On Pipeline Dashboard Page
* Looking at pipeline "child_pipeline"
* Verify stage "2" is "Passed" on pipeline with label "go-version-2"
* Navigate to stage "stage_for_ant_target" of run "2"

* Navigate to job "job_for_ant_target"

* Open console tab
* Verify console contains "setting environment variable 'STAGE_VARIABLE_USING_VALUE_FROM_PARAM' to value 'substituted value'"
* Verify console contains " overriding environment variable 'PIPELINE_VARIABLE_USING_VALUE_FROM_PARAM' with value 'value_overridden_by_trigger_with_options'"


Teardown of contexts
____________________
* Capture go state "AdvancedParamSubstitution" - teardown
* With "2" live agents in directory "ParamSubstitution" - teardown
* Using pipeline "parent_pipeline" - teardown
* Basic configuration - teardown


