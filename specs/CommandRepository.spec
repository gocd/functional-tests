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

CommandRepository
=================

Setup of contexts
* Basic configuration - setup
* Setup command repo - setup
* Using pipeline "basic-auto-pipeline" - setup
* Capture go state "ServerConfigurationCommandRepository" - setup

CommandRepository
-----------------

tags: 6664, admin-page, configuration, task-repository-location, 6640, stage1


* Verify command repository location is "default"

* Click on pipeline "basic-auto-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open tasks

* Open new task form "More..."

* Enter "sniPPet" in "Basic Settings" command lookup autocomplete box
* Auto complete should show suggestions "snippet1,snippet2,snippet_3_without_comment" - Already on Task edit popup
* Select option "3" from command lookup dropdown - Already on Task edit popup
* Verify command is set to "snippet3" with arguments "arg1_for_snippet3,arg2_for_snippet3" - Already on Task edit popup
* Enter "TesT-snipPET" in "Basic Settings" command lookup autocomplete box
* Auto complete should show suggestions "snippet1,snippet2" - Already on Task edit popup
* Select option "2" from command lookup dropdown - Already on Task edit popup
* Verify command is set to "snippet2" with arguments "arg1_for_snippet2,arg2_for_snippet2" - Already on Task edit popup
* Verify snippet details in "Basic Settings" are shown with name "snippet2" description "Snippet 2 description." author "Go Team" with authorlink "http://go.team/" and more info "http://more.info/"
* Save and verify saved successfully

* Verify task "2" is "Custom Command" task without on cancel and properties "Command: snippet2, Arguments: arg1_for_snippet2 arg2_for_snippet2" that runs if state is "Passed"
* Open task "2"


* Enter "SNIPPET" in "Basic Settings" command lookup autocomplete box
* Auto complete should show suggestions "snippet1,snippet2,snippet_3_without_comment" - Already on Task edit popup
* Select option "3" from command lookup dropdown - Already on Task edit popup
* Verify command is set to "snippet3" with arguments "arg1_for_snippet3,arg2_for_snippet3" - Already on Task edit popup
* Set "task[workingDirectory](text_field): workdir" - Already on Task edit popup
* Set "task[hasCancelTask](check_box): true" - Already on Task edit popup
* Select task "More..."
* Enter "snIPpET" in "Advanced Options" command lookup autocomplete box
* Auto complete should show suggestions "snippet1,snippet2,snippet_3_without_comment" - Already on Task edit popup
* Select option "2" from command lookup dropdown in advanced options
* Verify command is set to "snippet2" with arguments "arg1_for_snippet2,arg2_for_snippet2" in advanced options
* Verify snippet details in "Advanced Options" are shown with name "snippet2" description "Snippet 2 description." author "Go Team" with authorlink "http://go.team/" and more info "http://more.info/"
* Save and verify saved successfully


* Verify task "2" is "Custom Command" task with cancel "Custom Command" and properties "Command: snippet3, Arguments: arg1_for_snippet3 arg2_for_snippet3,  Working Directory: workdir " that runs if state is "Passed"
* Delete task "2"
* Verify no task having "Command: snippet2" exists

* Open "Server Configuration" tab

* Verify command repository location is set to "default"
* Using "" as command repository location
* Save configuration
* Verify message "Failed to save the server configuration. Reason: Command Repository Location cannot be empty" shows up
* Using "custom" as command repository location
* Save configuration
* Verify message "Saved configuration successfully." shows up

* Verify command repository location is "custom"

* Click on pipeline "basic-auto-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open tasks

* Open new task form "More..."

* Enter "gradle" in "Basic Settings" command lookup autocomplete box
* Auto complete should show suggestions "GRADLE-with-Compile,gradle-with-CLEAN" - Already on Task edit popup
* Select option "1" from command lookup dropdown - Already on Task edit popup
* Verify command is set to "gradle" with arguments "clean,compile" - Already on Task edit popup
* Select option "2" from command lookup dropdown - Already on Task edit popup
* Verify command is set to "gradle" with arguments "clean" - Already on Task edit popup





Teardown of contexts
____________________
* Capture go state "ServerConfigurationCommandRepository" - teardown
* Using pipeline "basic-auto-pipeline" - teardown
* Setup command repo - teardown
* Basic configuration - teardown


