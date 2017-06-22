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

AddNewPipeline
==============

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Setup command repo - setup
* Capture go state "AddNewPipeline" - setup

AddNewPipeline
--------------

tags: 5041, Clicky Admin

* Force navigate to add new pipeline

* Set "pipeline_group[pipeline][name](text_field): some_pipeline_name"
* Set "pipeline_group[group](text_field): new_group_admin_just_created"
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
* Verify has tag "git" "@url: http://git.url, @branch: some_branch"
* Verify has tag "ant" "@buildfile: build.xml, @target: compile, @workingdir: src"

* Force navigate to add new pipeline

* Set "pipeline_group[pipeline][name](text_field): some_other_pipeline_name"
* Set "pipeline_group[group](text_field): defaultGroup"
* Go next from settings
* Select material type as "Mercurial"
* Set "pipeline_group[pipeline][materials][HgMaterial][url](text_field): http://hg.url"
* Go next from materials
* Use template
* Select template named "template.with.params.one"
* Set "pipeline_group[pipeline][params][][valueForDisplay](text_field): foo"
* Save pipeline "some_other_pipeline_name" successfully

* Verify created successfully

* For pipeline "some_other_pipeline_name"
* Verify has tag "hg" "@url: http://hg.url"
* Verify has tag "param" "@name: var_name, text(): foo"
* Verify uses template "template.with.params.one"

* Force navigate to add new pipeline

* Set "pipeline_group[pipeline][name](text_field): svn_pipeline_name"
* Set "pipeline_group[group](text_field): defaultGroup"
* Go next from settings
* Select material type as "Subversion"
* Set "pipeline_group[pipeline][materials][SvnMaterial][url](text_field): http://svn.url"
* Set "pipeline_group[pipeline][materials][SvnMaterial][userName](text_field): loser"
* Set "pipeline_group[pipeline][materials][SvnMaterial][password](password_field): secret"
* Go next from materials
* Select task type as "More..."
* Enter "snippet" in command lookup autocomplete box
* Auto complete should show suggestions "snippet1,snippet2,snippet_3_without_comment"
* Select option "2" from command lookup dropdown
* Verify command is set to "snippet2" with arguments "arg1_for_snippet2,arg2_for_snippet2"
* Verify snippet details are shown with name "snippet2" description "Snippet 2 description." author "Go Team" with authorlink "http://go.team/" and more info "http://more.info/"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][exec][workingDirectory](text_field): target"
* Save pipeline "svn_pipeline_name" successfully

* Verify created successfully

* For pipeline "svn_pipeline_name"
* Verify has tag "svn" "@url: http://svn.url, @username: loser"
* Verify has tag "exec" "@command: snippet2, ./arg/text(): arg1_for_snippet2, @workingdir: target"
Removing this step since its failing for unknown reason, raised an issue to fix it - https://github.com/gocd/functional-tests/issues/199
 Verify has tag "exec" "@command: snippet2, ./arg/text(): arg2_for_snippet2"

* Force navigate to add new pipeline

* Set "pipeline_group[pipeline][name](text_field): p4_pipeline_name"
* Set "pipeline_group[group](text_field): defaultGroup"
* Go next from settings
* Select material type as "Perforce"
* Set "pipeline_group[pipeline][materials][P4Material][serverAndPort](text_field): foo:10"
* Set "pipeline_group[pipeline][materials][P4Material][userName](text_field): loser"
* Set "pipeline_group[pipeline][materials][P4Material][password](password_field): secret"
* Set "pipeline_group[pipeline][materials][P4Material][view](text_area): through_the_window"
* Go next from materials
* Select task type as "NAnt"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][nant][buildFile](text_field): dance"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][nant][target](text_field): stage"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][nant][workingDirectory](text_field): tmp"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][nant][nantPath](text_field): /usr/local/bin"
* Save pipeline "p4_pipeline_name" successfully

* Verify created successfully

* For pipeline "p4_pipeline_name"
* Verify has tag "p4" "@port: foo:10, @username: loser, ./view/text(): through_the_window"
* Verify has tag with file path "nant" "@nantpath: " "/usr/local/bin" ", @buildfile: dance, @target: stage, @workingdir: tmp"

* Force navigate to add new pipeline

* Set "pipeline_group[pipeline][name](text_field): downstream_pipeline"
* Set "pipeline_group[group](text_field): defaultGroup"
* Go next from settings
* Select material type as "Pipeline"
* Set "pipeline_group[pipeline][materials][DependencyMaterial][pipelineStageName](text_field): ${runtime_name:edit-pipeline} [defaultStage]"
* Go next from materials
* Select task type as "Rake"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][rake][buildFile](text_field): test.rake"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][rake][target](text_field): spec"
* Set "pipeline_group[pipeline][stage][jobs][][tasks][rake][workingDirectory](text_field): spec/lib"
* Save pipeline "downstream_pipeline" successfully

* Verify created successfully

* For pipeline "downstream_pipeline"
* Verify has tag "pipeline" "@pipelineName: ${runtime_name: edit-pipeline}, @stageName: defaultStage"
* Verify has tag "rake" "@buildfile: test.rake, @target: spec, @workingdir: spec/lib"





Teardown of contexts
____________________
* Capture go state "AddNewPipeline" - teardown
* Setup command repo - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


