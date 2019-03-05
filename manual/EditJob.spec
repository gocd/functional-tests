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

EditJob
=======

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "EditJob" - setup

EditJob
-------

tags: Clicky Admin, 4595, 5005

* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open job settings
* Set job name as "some really bad name"
* Click save - Already On Job Edit Page
* Verify save failed - Already On Job Edit Page
* Verify error message "Invalid job name 'some really bad name'. This must be alphanumeric and may contain underscores and periods. The maximum allowed length is 255 characters." is shown - Already On Job Edit Page
* Set job name as "new-shiny-but-valid-job-name"
* Click save - Already On Job Edit Page
* Verify that job saved sucessfully
* Verify that job is named "new-shiny-but-valid-job-name"

* On Pipeline Configuration wizard
* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "new-shiny-but-valid-job-name"

* Open job settings
* Verify that job is named "new-shiny-but-valid-job-name"
* Open custom tabs

* Enter tab name "first-tab" with path "some-path"
* Click save - Already on Job Custom Tabs Page
* Verify saved successfully - Already on Job Custom Tabs Page
* Enter new tab name "first-tab" with path "name/was/repeated"
* Click save - Already on Job Custom Tabs Page
* Verify save failed - Already on Job Custom Tabs Page
* Verify error message "Tab name 'first-tab' is not unique." on name on tab "0"
* Verify error message "Tab name 'first-tab' is not unique." on name on tab "1"







Teardown of contexts
____________________
* Capture go state "EditJob" - teardown
* Using pipeline "edit-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


