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

AdminRolesAndUsers
==================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "viewable-pipeline" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "AdminRolesAndUsers" - setup

AdminRolesAndUsers
------------------

tags: 1952, security, automate

* Making "pavan" an admin user
* Making "raghu" an admin user

Verify if user is admin
* VerifyIfUserIsAdmin 
     |login as user|admin|
     |-------------|-----|
     |view         |false|
     |operate      |false|
     |pavan        |true |
     |raghu        |true |
     |admin        |true |

* Logout - On Any Page

* Login as "admin"

* Removing "raghu" as an admin

* Logout - On Any Page

* VerifyIfUserIsAdmin 
     |login as user|admin|
     |-------------|-----|
     |raghu        |false|

* Login as "raghu"

* Verify pipeline "viewable-pipeline" is not visible

* Logout - On Any Page

* Login as "pavan"

* Looking at pipeline "viewable-pipeline"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1"





Teardown of contexts
____________________
* Capture go state "AdminRolesAndUsers" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "viewable-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


