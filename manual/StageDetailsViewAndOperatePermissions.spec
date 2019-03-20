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

StageDetailsViewAndOperatePermissions
=====================================

Setup of contexts
* Stage security configuration - setup
* Login as "admin" - setup
* Using pipeline "p1, p2, p3, p4" - setup
* Capture go state "StageDetailsViewAndOperatePermissions" - setup

StageDetailsViewAndOperatePermissions
-------------------------------------

tags: 6786, stage-details

* trigger pipeline "p1" and cancel it
* trigger pipeline "p2" and cancel it
* trigger pipeline "p3" and cancel it
* trigger pipeline "p4" and cancel it
* Looking at pipeline "p4"
* Navigate to stage "defaultStage" of run "1"

* Verify user can view config tab contents
* Trigger stage "stage2"
* Cancel "stage2" - Already On Stage Detail Page

//User - view

* Logout and login as "view"


* Looking at pipeline "p1"
* Navigate to stage "defaultStage" of run "1"
* Verify user cannot view config tab contents
* Verify rerun is disabled for stage "defaultStage"
* Go to jobs tab
* Verify rerun button is disabled

* On Pipeline Dashboard Page
* verify user can operate stage "defaultStage" for pipeline "p2" from stage details page

* On Pipeline Dashboard Page
* verify user can operate stage "defaultStage" for pipeline "p3" from stage details page

* On Pipeline Dashboard Page
* verify user can operate stage "defaultStage" for pipeline "p4" from stage details page

* On Pipeline Dashboard Page
* verify user cannot operate stage "stage2" for pipeline "p4" from stage details page


//User - operate

* Logout and login as "operate"


* Looking at pipeline "p1"
* Navigate to stage "defaultStage" of run "1"
* Verify user cannot view config tab contents
* Verify rerun is enabled for stage "defaultStage"
* Go to jobs tab
* Verify rerun button is enabled

* On Pipeline Dashboard Page
* verify user can operate stage "defaultStage" for pipeline "p2" from stage details page

* On Pipeline Dashboard Page
* verify user cannot operate stage "defaultStage" for pipeline "p4" from stage details page

* On Pipeline Dashboard Page
* verify user can operate stage "stage2" for pipeline "p4" from stage details page


* Logout and login as "group1View"

* On Pipeline Dashboard Page
* verify user cannot operate stage "defaultStage" for pipeline "p1" from stage details page

* On Pipeline Dashboard Page
* verify user cannot operate stage "defaultStage" for pipeline "p2" from stage details page

* On Pipeline Dashboard Page
* Looking at pipeline "p3"
* Navigate to stage "defaultStage" of run "1"
* Verify user cannot view config tab contents
* Verify rerun is enabled for stage "defaultStage"
* Go to jobs tab
* Verify rerun button is enabled

* On Pipeline Dashboard Page
* verify user can operate stage "defaultStage" for pipeline "p4" from stage details page

* On Pipeline Dashboard Page
* verify user cannot operate stage "stage2" for pipeline "p4" from stage details page




Teardown of contexts
____________________
* Capture go state "StageDetailsViewAndOperatePermissions" - teardown
* Using pipeline "p1, p2, p3, p4" - teardown
* Login as "admin" - teardown
* Stage security configuration - teardown


