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

AdminTaskListing
================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-ls" - setup
* Capture go state "AdminTaskListing" - setup

AdminTaskListing
----------------

tags: 4597, task, clicky, admin, exec, stage1

* Click on pipeline "pipeline-ls" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open tasks

* Verify task "1" is "Custom Command" task without on cancel and properties "Command: ls" that runs if state is "Passed"
* Open new task form "More..."


* Set "task[command](text_field): cat, task[argListString](text_area): /tmp/foo, task[workingDirectory](text_field): foo_bar" - Already on Task edit popup
* Set "task[runIfConfigsPassed](check_box): true, task[runIfConfigsFailed](check_box): true" - Already on Task edit popup
* Set "task[hasCancelTask](check_box): true" - Already on Task edit popup
* Select task "More..."
* Set "[execOnCancel][command](text_field): echo" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "1" is "Custom Command" task without on cancel and properties "Command: ls" that runs if state is "Passed"
* Verify task "2" is "Custom Command" task with cancel "Custom Command" and properties "Command: cat, Arguments: /tmp/foo, Working Directory: foo_bar" that runs if state is "Failed, Passed"
* Delete task "1"
* Verify no task having "Command: ls" exists

* Verify no "exec" task with "command: ls" exists in "defaultJob" under "defaultStage"




Teardown of contexts
____________________
* Capture go state "AdminTaskListing" - teardown
* Using pipeline "pipeline-ls" - teardown
* Basic configuration - teardown


