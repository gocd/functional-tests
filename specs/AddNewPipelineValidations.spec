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

AddNewPipelineValidations
=========================

Setup of contexts
* Secure configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "AddNewPipelineValidations" - setup

AddNewPipelineValidations
-------------------------

tags: 5041, Clicky Admin, 5172, stage1

* Force navigate to add new pipeline

pipeline tab validations(do group here)
* Remember current tab
* Set "pipeline_group[pipeline][name](text_field): NULL"
* Go next from settings
* Verify inline validation message "This field is required."
* Set "pipeline_group[pipeline][name](text_field): some random pipeline name"
* Go next from settings
* Verify inline validation message "Value should contain only alphanumeric characters, dashes, underscores and periods."
* Set "pipeline_group[pipeline][name](text_field): ${runtime_name:edit-pipeline}"
* Go next from settings
* Verify inline validation message "Pipeline name is already in use."
* Set "pipeline_group[pipeline][name](text_field): the.right.thing"
* Set "pipeline_group[group](text_field): some junk group"
* Go next from settings
* Verify inline validation message "Value should contain only alphanumeric characters, dashes, underscores and periods."
* Set "pipeline_group[group](text_field): some.junk.group"
* Go next from settings
* Verify transitioned successfully
* Go back from materials
* Set "pipeline_group[group](text_field): NULL"
* Go next from settings
* Verify inline validation message "This field is required."
* Set "pipeline_group[group](text_field): defaultGroup"
* Go next from settings
* Verify transitioned successfully

git material tab validations
* Remember current tab
* Select material type as "Git"
* Go next from materials
* Verify inline validation message "This field is required."
* Set "pipeline_group[pipeline][materials][GitMaterial][url](text_field): http://git.url"
* Go next from materials
* Verify transitioned successfully
* Go back from stage and job

svn material tab validations
* Remember current tab
* Select material type as "Subversion"
* Go next from materials
* Verify inline validation message "This field is required."
* Set "pipeline_group[pipeline][materials][SvnMaterial][url](text_field): http://svn.url"
* Go next from materials
* Verify transitioned successfully
* Go back from stage and job

hg material tab validations
* Remember current tab
* Select material type as "Mercurial"
* Go next from materials
* Verify inline validation message "This field is required."
* Set "pipeline_group[pipeline][materials][HgMaterial][url](text_field): http://hg.url"
* Go next from materials
* Verify transitioned successfully
* Go back from stage and job

p4 material tab validations
* Remember current tab
* Select material type as "Perforce"
* Go next from materials
* Verify inline validation message "This field is required."
* Set "pipeline_group[pipeline][materials][P4Material][serverAndPort](text_field): p4.url:100"
* Go next from materials
* Verify inline validation message "This field is required."
* Set "pipeline_group[pipeline][materials][P4Material][view](text_area): someview"
* Go next from materials
* Verify transitioned successfully
* Go back from stage and job

tfs material tab validations
* Remember current tab
* Select material type as "Team Foundation Server"
* Go next from materials
* Verify inline validation message "This field is required."
* Set tfs collection "integration_tests"
* Go next from materials
* Verify inline validation message "This field is required."
* Set "pipeline_group[pipeline][materials][TfsMaterial][username](text_field): user"
* Go next from materials
* Verify inline validation message "This field is required."
* Set "pipeline_group[pipeline][materials][TfsMaterial][projectPath](text_field): $/for_tests"
* Go next from materials
* Verify transitioned successfully
* Go back from stage and job


dependency material tab validations
* Remember current tab
* Select material type as "Pipeline"
* Go next from materials
* Verify inline validation message "This field is required."
* Set "pipeline_group[pipeline][materials][DependencyMaterial][pipelineStageName](text_field): ${runtime_name:edit-pipeline} [defaultStage]"
* Go next from materials
* Verify transitioned successfully

stage/job validations
* Remember current tab
* Select task type as "Rake"
* Set "pipeline_group[pipeline][stage][name](text_field): NULL"
* Save pipeline
* Verify inline validation message "This field is required."
* Set "pipeline_group[pipeline][stage][name](text_field): foo bar baz"
* Save pipeline
* Verify inline validation message "Value should contain only alphanumeric characters, dashes, underscores and periods."
* Set "pipeline_group[pipeline][stage][name](text_field): valid.stage.name"
* Set "pipeline_group[pipeline][stage][jobs][][name](text_field): NULL"
* Verify inline validation message "This field is required."
* Set "pipeline_group[pipeline][stage][jobs][][name](text_field): in valid job name"
* Verify inline validation message "Value should contain only alphanumeric characters, dashes, underscores and periods."
* Set "pipeline_group[pipeline][stage][jobs][][name](text_field): valid.job.name"
* Select task type as "More..."
* Save pipeline
* Verify inline validation message "This field is required."
* Set "pipeline_group[pipeline][stage][jobs][][tasks][exec][command](text_field): ls"
* Save pipeline "the.right.thing" successfully

template validations
* Force navigate to add new pipeline

* Set "pipeline_group[pipeline][name](text_field): a.valid.pipeline.name"
* Set "pipeline_group[group](text_field): defaultGroup"
* Go next from settings
* Select material type as "Git"
* Set "pipeline_group[pipeline][materials][GitMaterial][url](text_field): http://git.url"
* Go next from materials
* Remember current tab
* Use template
* Select template named "template.with.params.two"
* Verify has field named "pipeline_group[pipeline][params][][name]" with value "command"
* Verify has field named "pipeline_group[pipeline][params][][name]" with value "command_two"
* Select template named "template.with.params.one"
* Verify has field named "pipeline_group[pipeline][params][][name]" with value "var_name"
* Save pipeline
* Verify error "Environment Variable cannot have an empty name for job 'defaultJob'." is shown on top
* Go next from settings
* Go next from materials
* Set "pipeline_group[pipeline][params][][valueForDisplay](text_field): foo"
* Save pipeline "a.valid.pipeline.name" successfully




Teardown of contexts
____________________
* Capture go state "AddNewPipelineValidations" - teardown
* Using pipeline "edit-pipeline" - teardown
* Secure configuration - teardown


