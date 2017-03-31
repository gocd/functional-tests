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

CurlTaskPlugin
==============

Setup of contexts
* Plugin configuration - setup
* Login as "admin" - setup
* Using pipeline "curl-task-pass,curl-task-fail" - setup
* With "1" live agents in directory "PluginAgents" - setup
* Capture go state "CurlTaskPlugin" - setup

CurlTaskPlugin
--------------

tags: #7928, plugins-tests

* Open "Plugins" tab

* Verify plugin with id "cd.go.contrib.task.skeleton" is loaded

* Click on pipeline "curl-task-pass" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open tasks

* Open new task form "Curl"

Include server and angular validation on onCancel
* Save and verify save failed
* Verify inline validation message "URL cannot be empty, This field is required" - Already on Task edit popup
* Set "task[Url](url_field): some_random_value" - Already on Task edit popup
* Verify inline validation message "Incorrect url format." - Already on Task edit popup
* Set url as "task[Url](url_field): /api/pipelines/curl-task-fail/schedule"
* Select secureConnection as "No"
* Select requestType as "POST"
* Set "task[AdditionalOptions](text_field): -u admin:badger -H CONFIRM:true" - Already on Task edit popup
* Set "task[hasCancelTask](check_box): true" - Already on Task edit popup
* Select task "Curl"
* Set "task[onCancelConfig][pluggable_task_cd_go_contrib_task_skeletonOnCancel][Url](url_field): http://www.google.co.in" - Already on Task edit popup
* Save and verify saved successfully


* Verify task "1" is "Curl " task without on cancel and properties "Url: https://www.codeschool.com, Secure Connection: yes, Request Type: -G" that runs if state is "Passed"
* Verify task "2" is "Curl" task with cancel "Curl" and properties "Secure Connection: no, Request Type: -d, Additional Options: -u admin:badger -H CONFIRM:true" that runs if state is "Passed"

* Looking at pipeline "curl-task-pass"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "defaultJob"

* Open console tab
* Verify console contains "Plugin with ID: cd.go.contrib.task.skeleton."
* Verify console contains "https://www.codeschool.com"

* On Pipeline Dashboard Page
* Looking at pipeline "curl-task-fail"
* Verify stage "1" is "Failed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "defaultJob"

* Open console tab
* Verify console contains "Plugin with ID: cd.go.contrib.task.skeleton."


* Force navigate to add new pipeline

* Set "pipeline_group[pipeline][name](text_field): some_pipeline"
* Set "pipeline_group[group](text_field): new_group"
* Go next from settings
* Select material type as "Git"
* Set "pipeline_group[pipeline][materials][GitMaterial][url](text_field): http://git.url"
* Go next from materials
* Select task type as "Curl"
* Save pipeline
* Verify validation message "Save failed, see errors below"
* Verify inline error message "URL cannot be empty, This field is required"

* Set "pipeline_group[pipeline][stage][jobs][][tasks][pluggable_task_cd_go_contrib_task_skeleton][Url](url_field): some_randomn_value"
* Verify inline error message "Incorrect url format."
* Set "pipeline_group[pipeline][stage][jobs][][tasks][pluggable_task_cd_go_contrib_task_skeleton][Url](url_field): http://claystation.in"
* Save pipeline "some_pipeline" successfully

* Verify created successfully





Teardown of contexts
____________________
* Capture go state "CurlTaskPlugin" - teardown
* With "1" live agents in directory "PluginAgents" - teardown
* Using pipeline "curl-task-pass,curl-task-fail" - teardown
* Login as "admin" - teardown
* Plugin configuration - teardown


