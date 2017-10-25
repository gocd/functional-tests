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

ClickyAdminDirtyPageCheck
=========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "ClickyAdminDirtyPageCheck" - setup

ClickyAdminDirtyPageCheck
-------------------------

tags: Clicky Admin, 4661

* Click on pipeline "edit-pipeline" for editing

Checking for textbox and checkbox
* Enter "${COUNT}-pipelinename" for label template
* Open parameters page
* Verify that dirty check is invoked
* Cancel dirty check prompt
* Click reset
* Select automatic pipeline locking
* Open stage listing page
* Verify that dirty check is invoked
* Proceed with dirty check prompt

* Open stage "defaultStage"

Checking for radio group
* Select stage type as "auto"
* Click reset - Already on edit stage page
* Select stage type as "auto"
* Select stage type as "manual"
* Go to environment variables page - Already on edit stage page
* Proceed with dirty check prompt - Already on edit stage page

* Verify heading "Environment Variables"




Teardown of contexts
____________________
* Capture go state "ClickyAdminDirtyPageCheck" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


