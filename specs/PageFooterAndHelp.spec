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

PageFooterAndHelp
=================

     |user name|
     |---------|
     |admin    |
     |view     |
Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline" - setup
* Capture go state "PageFooterAndHelp" - setup

PageFooterAndHelp
-----------------

tags: 3246, automate

* Logout - On Any Page

* Login as <user name>

* Looking at pipeline "basic-pipeline"
* Trigger pipeline
* Wait for stage "defaultStage" status to be "Building" with label "1"
* Navigate to stage "defaultStage" of run "1"

* Wait for jobs to show "In Progress: 1" with jobs "defaultJob"

* On Pipeline Dashboard Page
* Verify cruise footer

* Verify cruise footer - On Agents Page

* Verify cruise footer - On preferences page

* On Pipeline Dashboard Page
* Verify cruise footer

* Verify cruise footer - On Server Detail Page

* Navigate to pipeline dependencies for "basic-pipeline" "1" "defaultStage" "1"

* Verify cruise footer - Already On Stage Detail Pipeline Dependencies Page

* On Pipeline Dashboard Page
* Navigate to stage "defaultStage" of run "1"

* Verify cruise footer - Already On Stage Detail Page

* Verify cruise footer - On Environment Page




Teardown of contexts
____________________
* Capture go state "PageFooterAndHelp" - teardown
* Using pipeline "basic-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


