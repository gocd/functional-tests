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

EditPipelineWizardStageListing
==============================

Setup of contexts
* Basic configuration - setup
* Setup command repo - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "EditPipelineWizardStageListing" - setup

EditPipelineWizardStageListing
------------------------------

tags: Clicky Admin, 4742

* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

* Open new add stage details page

* Click save - Already on Add Stage Popup
* Verify "Invalid stage name ''. This must be alphanumeric and can contain underscores and periods (however, it cannot start with a period). The maximum allowed length is 255 characters." message exists
* Verify "Name is a required field" message exists
* Verify "Save failed, see errors below" message exists
* Enter for stage name "defaultStage2"
* Enter for job name "job1"
* Select "More..." as task type
* Click save - Already on Add Stage Popup
* Verify "command" has error message "Command cannot be empty"
* Enter as command "ls"
* Add stage

Because of bug "On Success" message is being shown as success

* Verify that stage saved successfully
* Verify "defaultStage2" has "On Success" trigger option with "1" jobs
* Open new add stage details page

* Enter for stage name "taskSnippetStage"
* Enter for job name "job1"
* Select "More..." as task type
* Enter "TesT-snipPET" in command lookup autocomplete box - Already on Add Stage Popup
* Auto complete should show suggestions "snippet1,snippet2" - Already on Add Stage Popup
* Select option "2" from command lookup dropdown - Already on Add Stage Popup
* Verify command is set to "snippet2" with arguments "arg1_for_snippet2,arg2_for_snippet2" - Already on Add Stage Popup
* Add stage

* Verify that stage saved successfully
* Verify "taskSnippetStage" has "On Success" trigger option with "1" jobs


* Open stage "defaultStage2"

* Click save - Already on Edit Stage Page
* Select stage type "manual"
* Select clean working directory
* Select fetch materials
* Click save - Already on Edit Stage Page
* Verify that stage saved successfully - Already on edit stage page

* Open pipeline "edit-pipeline"

* Open stage listing page

* Verify "defaultStage2" has "Manual" trigger option with "1" jobs

* Delete stage "defaultStage2" and confirm deletion
* Verify "defaultStage" stage is present
* Verify "taskSnippetStage" stage is present
* Verify stage "defaultStage2" is not present





Teardown of contexts
* Capture go state "EditPipelineWizardStageListing" - teardown
* Using pipeline "edit-pipeline" - teardown
* Setup command repo - teardown
* Basic configuration - teardown


