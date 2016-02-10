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

StageSecurity
=============

     |userName    |
     |------------|
     |operate     |
     |operatorUser|
Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline-with-stage-security" - setup
* With "1" live agents in directory "StageSecurity" - setup
* Capture go state "StageSecurity" - setup

StageSecurity
-------------

tags: 2400, passwordfile support, authorisation, authentication, svn support, 1547, security, restful api, automate

* Looking at pipeline "pipeline-with-stage-security"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"

* Stop "1" jobs that are waiting for file to exist

* Looking at pipeline "pipeline-with-stage-security" - Already On Pipeline Dashboard Page
* Verify stage "1" is "Passed" on pipeline with label "1" - Already On Pipeline Dashboard Page

* Logout - On Any Page

* Login as "view"

* Logged in as "view"

* Looking at pipeline "pipeline-with-stage-security"
* Verify cannot trigger pipeline
* Verify trigger with option is disabled
* Navigate to stage "first" of run "1"

* Verify stage "first" does not have any action

* Logout - On Any Page

* Login as <userName>

* Logged in as <userName>

* Looking at pipeline "pipeline-with-stage-security"
* Verify can trigger pipeline
* Verify trigger with option is enabled
* Navigate to stage "first" of run "1"

* Verify stage "first" has action "Rerun"
* Rerun stage "first"
* Verify stage "first" has action "Cancel"
* Cancel "first" - Already On Stage Detail Page




Teardown of contexts
____________________
* Capture go state "StageSecurity" - teardown
* With "1" live agents in directory "StageSecurity" - teardown
* Using pipeline "pipeline-with-stage-security" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


