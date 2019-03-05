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

CommandRepositoryValidations
============================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Setup command repo - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "CommandRepositoryValidations" - setup

CommandRepositoryValidations
----------------------------

tags: 6664, admin-page, configuration, task-repository-location, 6669, server_restart_needed

* Create a directory "dirWithoutPermissions" without permissions in the command _ repository location "custom"

* Open "Server Configuration" tab

* Using "custom" as command repository location
* Save configuration
* Verify message "Saved configuration successfully." shows up
* Add valid snippet file with name "should-show-after-reload" into command _ repository location "custom"
* Reload command snippet cache
* Add valid snippet file with name "should-show-after-reload-using-api" into command _ repository location "custom"

* Reload cache as "view"
* Verify user is not authorized to reload cache
* Reload cache as "admin"
* Verify reload was successful

* Open "Server Configuration" tab

* Add valid snippet file with name "should-not-show-after-reload" into command _ repository location "custom"


* Verify warning "detail" contains "Failed to access command repository directory: db/command_repository/custom/dirWithoutPermissions."

* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open tasks

* Open new task form "More..."

* Enter "should-show-after-reload" in "Basic Settings" command lookup autocomplete box
* Auto complete should show suggestions "should-show-after-reload,should-show-after-reload-using-api" - Already on Task edit popup
* Enter "should-not-show-after-reload" in "Basic Settings" command lookup autocomplete box
* Auto complete should not show up "should-not-show-after-reload"
* Enter "gradle" in "Basic Settings" command lookup autocomplete box
* Auto complete should show suggestions "GRADLE-with-Compile,gradle-with-CLEAN" - Already on Task edit popup
* Select option "1" from command lookup dropdown - Already on Task edit popup
* Verify command is set to "gradle" with arguments "clean,compile" - Already on Task edit popup
* Verify that message "2 invalid commands found." is shown
* Click on details
* Verify that invalid snippet "invalid1" appears with error message "Reason: Command attribute cannot be blank in a command snippet."
* Verify that invalid snippet "invalid2" appears with error message "Reason: Invalid content was found starting with element 'foo'. One of '{arg}' is expected."

Verifying server health message for an unreadable command snippet file
* Cleanup directory "dirWithoutPermissions" in the command _ repository location "custom"

* On Admin page
* Open "Server Configuration" tab

* Make snippet with name "should-show-after-reload" in command _ repository location "custom" unreadable
* Reload command snippet cache

* Verify warning "detail" contains "Failed to access command snippet XML file located in Go Server Directory at db/command_repository/custom/should-show-after-reload.xml. Go does not have sufficient permissions to access it."

Verifying server health message for a directory which does not exist being set as the custom command repo location
* Open "Server Configuration" tab

* Using "dirDoesNotExist" as command repository location
* Save configuration
* Verify message "Saved configuration successfully." shows up
* Reload command snippet cache


* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open tasks

* Open new task form "More..."

* Verify warning "detail" contains "Failed to access command repository located in Go Server Directory at db/command_repository/dirDoesNotExist. The directory does not exist or Go does not have sufficient permissions to access it."

* Stop server
* Start server





Teardown of contexts
____________________
* Capture go state "CommandRepositoryValidations" - teardown
* Using pipeline "edit-pipeline" - teardown
* Setup command repo - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


