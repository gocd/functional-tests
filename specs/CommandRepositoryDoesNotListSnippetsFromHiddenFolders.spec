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

CommandRepositoryDoesNotListSnippetsFromHiddenFolders
=====================================================

Setup of contexts
* Basic configuration - setup
* Setup command repo - setup
* Using pipeline "basic-auto-pipeline" - setup
* Capture go state "CommandRepositoryDoesNotListSnippetsFromHiddenFolders" - setup

CommandRepositoryDoesNotListSnippetsFromHiddenFolders
-----------------------------------------------------

tags: admin-page, configuration, task-repository-location, 6713

* Click on pipeline "basic-auto-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open tasks

* Open new task form "More..."

verify that valid files from non hidden parent folder are listed
* Enter "not_hidden" in "Basic Settings" command lookup autocomplete box
* Auto complete should show suggestions "not_hidden" - Already on Task edit popup
* Enter "invalid_snippet_under_parent" in "Basic Settings" command lookup autocomplete box
* Verify "invalid_snippet_under_parent" does not show up in "Basic Settings" command lookup dropdown

verify that none of the files valid/invalid placed under hidden folder are listed
* Enter "invalid_snippet_under_hidden_folder" in "Basic Settings" command lookup autocomplete box
* Verify "invalid_snippet_under_hidden_folder" does not show up in "Basic Settings" command lookup dropdown
* Enter "valid_hidden" in "Basic Settings" command lookup autocomplete box
* Verify "valid_hidden" does not show up in "Basic Settings" command lookup dropdown

verify that invalid files from hidden folder do not get listed as invalid
* Verify that message "1 invalid commands found." is shown





Teardown of contexts
* Capture go state "CommandRepositoryDoesNotListSnippetsFromHiddenFolders" - teardown
* Using pipeline "basic-auto-pipeline" - teardown
* Setup command repo - teardown
* Basic configuration - teardown


