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

NewExecTask
===========

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-ls" - setup
* Capture go state "NewExecTask" - setup

NewExecTask
-----------

tags: 4597, task, clicky, admin, exec

* Click on pipeline "pipeline-ls" for editing

* Open job "defaultJob" of "defaultStage"

* Open new task form "More..."

try bad data
* Verify has "task[command](text_field): NULL, task[argListString](text_area): NULL, task[workingDirectory](text_field): NULL"
* Verify has "task[runIfConfigsPassed](check_box): true, task[runIfConfigsFailed](check_box): false, task[runIfConfigsAny](check_box): false"
* Verify has "task[hasCancelTask](check_box): false"
* Set "task[argListString](text_area): /tmp/foo, task[workingDirectory](text_field): ../foo" - Already on Task edit popup
* Set "task[runIfConfigsAny](check_box): true" - Already on Task edit popup
* Set "task[hasCancelTask](check_box): true" - Already on Task edit popup
* Select task "More..."
* Set "[execOnCancel][argListString](text_area): /var/log/messages, [execOnCancel][workingDirectory](text_field): foo_bar" - Already on Task edit popup
* Save and verify save failed
* Verify has "task[command](text_field): NULL, task[argListString](text_area): /tmp/foo, task[workingDirectory](text_field): ../foo"
* Verify has "[execOnCancel][command](text_field): NULL, [execOnCancel][argListString](text_area): /var/log/messages, [execOnCancel][workingDirectory](text_field): foo_bar"
* Verify validation failed for "task[command](text_field): Command cannot be empty, [execOnCancel][command](text_field): Command cannot be empty"
* Verify validation failed for "task[workingDirectory](text_field): The path of the working directory for the custom command in job 'defaultJob' in stage 'defaultStage' of pipeline '${runtime_name:pipeline-ls}' is outside the agent sandbox."

address all the validation issues
* Set "task[command](text_field): cat, [execOnCancel][command](text_field): tail, task[workingDirectory](text_field): hello_world" - Already on Task edit popup
* Save and verify saved successfully

* For pipeline "pipeline-ls"
* Verify "exec" task "@command: cat, @workingdir: hello_world" with arg "/tmp/foo" exists for "defaultJob" in "defaultStage"
* Verify on cancel "exec" task "@command: tail, @workingdir: foo_bar" with arg "/var/log/messages" exists for task "2" of "defaultJob" in "defaultStage"
* Verify task "2" of "defaultJob" in "defaultStage" runs if "any"







Teardown of contexts
____________________
* Capture go state "NewExecTask" - teardown
* Using pipeline "pipeline-ls" - teardown
* Basic configuration - teardown


