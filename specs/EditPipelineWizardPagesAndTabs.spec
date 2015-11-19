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

EditPipelineWizardPagesAndTabs
==============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "EditPipelineWizardPagesAndTabs" - setup

EditPipelineWizardPagesAndTabs
------------------------------

tags: Clicky Admin

* Click on pipeline "edit-pipeline" for editing

* Verify reset button exists
* Enter "${COUNT}-pipelinename" for label template
* Enter "0 0 22 ? * MON-FRI" for cron time specifier
* Click save - Already On General Options Page
* Verify "Saved successfully." message is displayed
* Go to project management page

* Verify reset button exists - Already on project management page
* Enter "https://mingle09.thoughtworks.com" for mingle uRL
* Enter "go" for mingle project identifier
* Enter "select from CARD" for mQA grouping conditions
* Click save - Already On Project Management Page
* Verify "Saved successfully." message is displayed - Already on project management page
* Go to materials page

* Verify "Add Material" link is present
* Go to environment variables page - Already on material listing page

* Verify reset button exists - Already on environment variables page
* Enter environment variable "1" name "os" and value "windows"
* Click save - Already On Environment Variables Page
* Verify "Saved successfully." message is displayed - Already On Environment Variables Page
* Open parameters page - Already on environment variables page

* Verify reset button exists - Already on parameters page
* Enter parameter "1" name "foo" and value "bar"
* Click save
* Verify "Saved successfully." message is displayed - Already on parameters page
* Open stage listing page - Already on parameters page

there is a bug here below statement should have been verify defaultstage has type Auto

* Verify "defaultStage" has "Manual" trigger option with "1" jobs
* Open new add stage details page

* Enter for stage name "defaultStage2"
* Select "success" for stage trigger
* Enter for job name "job1"
* Select "More..." as task type
* Enter as command "ls"
* Add stage

* Verify that stage saved successfully

* For pipeline "edit-pipeline"
* Verify that stage "defaultStage2" exists





Teardown of contexts
* Capture go state "EditPipelineWizardPagesAndTabs" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


