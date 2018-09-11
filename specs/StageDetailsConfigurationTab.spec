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

StageDetailsConfigurationTab
============================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "edit-pipeline" - setup
* With "1" live agents in directory "StageDetailsConfigurationTab" - setup
* Capture go state "StageDetailsConfigurationTab" - setup

StageDetailsConfigurationTab
----------------------------

tags: 4489, stage-details, jgit

Make a harmless change to config
* Open "Server Configuration" tab

* Set field "siteUrl" to "http://localhost:8253"
* Save configuration
* Verify message "Saved configuration successfully." shows up

trigger once
* On Pipeline Dashboard Page
* Looking at pipeline "edit-pipeline"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Goto config tab

verify shown config
* Verify notification message has "This version of config was edited by admin"
* Verify config is visible
* Verify "ant" task "@target: longsleep, @workingdir: hg/dev" exists for "defaultJob" in "defaultStage" in "${runtime_name:edit-pipeline}" for the shown config

change task to fail the stage and add a manual stage
* On Pipeline Configuration wizard
* Click on pipeline "edit-pipeline" for editing

* Open stage "defaultStage" - Using Pipeline Navigation

* Open stage listing page - Already On Edit Stage Page

* Open new add stage details page

* Enter for stage name "defaultStage2"
* Mark stage manual
* Enter for job name "defaultJob2"
* Select "More..." as task type
* Enter as command "echo"
* Add stage

* Verify that stage saved successfully
* Verify "defaultStage2" has "Manual" trigger option with "1" jobs

* Open job "defaultJob" of "defaultStage"

* Delete task "1"
* Open new task form "More..."

* Set "task[command](text_field): invalid-command, task[workingDirectory](text_field): hello_world" - Already on Task edit popup
* Set "task[argListString](text_area): /tmp/foo" - Already on Task edit popup
* Save and verify saved successfully

* For pipeline "edit-pipeline"
* Verify "exec" task "@command: invalid-command, @workingdir: hello_world" with arg "/tmp/foo" exists for "defaultJob" in "defaultStage"

trigger again
* On Pipeline Dashboard Page
* Looking at pipeline "edit-pipeline"
* Trigger pipeline
* Verify stage "1" is "Failed" on pipeline with label "2"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Verify config changed marker after pipeline counter "1" stage counter "1" is a link
* Goto config tab

verify new config shown for the stage details
* Verify notification message has "This version of config was edited by admin"
* Verify config is visible
* Verify "ant" task "@target: all, @workingdir: hg/dev" does not exist for "defaultJob" in "defaultStage" in "${runtime_name:edit-pipeline}" for the shown config
* Verify "exec" task "@command: invalid-command, @workingdir: hello_world" exists for "defaultJob" in "defaultStage" in "${runtime_name:edit-pipeline}" for the shown config

manually trigger the second stage
* On Pipeline Dashboard Page
* Looking at pipeline "edit-pipeline"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Trigger stage "defaultStage2"
* On Pipeline Dashboard Page

* Navigate to stage "defaultStage2" of run "2" having counter "1"

* Verify no config changed marker is present
* Goto config tab

* Verify notification message has "This version of config was edited by admin"
* Verify config is visible
* Verify "ant" task "@target: all, @workingdir: hg/dev" does not exist for "defaultJob" in "defaultStage" in "${runtime_name:edit-pipeline}" for the shown config
* Verify "exec" task "@command: invalid-command, @workingdir: hello_world" exists for "defaultJob" in "defaultStage" in "${runtime_name:edit-pipeline}" for the shown config
* Verify "exec" task "@command: echo" exists for "defaultJob2" in "defaultStage2" in "${runtime_name:edit-pipeline}" for the shown config

* Logout and login as "view"

* Looking at pipeline "edit-pipeline"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Verify config changed marker after pipeline counter "1" stage counter "1" is not a link
* Goto config tab

* Verify notification message has "Historical configuration is available only for Go Administrators."





Teardown of contexts
____________________
* Capture go state "StageDetailsConfigurationTab" - teardown
* With "1" live agents in directory "StageDetailsConfigurationTab" - teardown
* Using pipeline "edit-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


