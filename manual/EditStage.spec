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

EditStage
=========

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "EditStage" - setup

EditStage
---------

tags: Clicky Admin, 4595, 5005

* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Set stage name as "some really horrible stage name"
* Click save - Already on Edit Stage Page
* Verify save failed
* Verify error message "Invalid stage name 'some really horrible stage name'. This must be alphanumeric and can contain underscores and periods (however, it cannot start with a period). The maximum allowed length is 255 characters." is shown
* Set stage name as "new-shiny-but-valid-stage-name"
* Click save - Already on Edit Stage Page
* Verify that stage saved successfully - Already on edit stage page
* Verify that stage is named "new-shiny-but-valid-stage-name"

* On Pipeline Configuration wizard
* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

* Open stage "new-shiny-but-valid-stage-name"

* Verify that stage is named "new-shiny-but-valid-stage-name"





Teardown of contexts
____________________
* Capture go state "EditStage" - teardown
* Using pipeline "edit-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


