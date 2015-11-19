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

CCTrayFeed
==========

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline-for-cctray, admin-pipeline" - setup
* With "1" live agents in directory "CCTrayFeed" - setup
* Capture go state "CCTrayFeed" - setup

CCTrayFeed
----------

tags: 2318, cctray integration, personal notification, configuration, dashboard, svn support, scheduling, restful api, diagnostics messages, automate, stage1

* Looking at pipeline "basic-pipeline-for-cctray"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"

* Fail "1" jobs that are waiting for file to exist

* Looking at pipeline "basic-pipeline-for-cctray"
* Verify stage "1" is "Failed" on pipeline with label "1"

CCTray feed should contain the breakers info
* Looking at pipeline "basic-pipeline-for-cctray" - CC Tray Feed Xml
* Verify cctray feed contains stage "defaultStage" with current activity "Sleeping" with label "1" and last status "Failure" with broken by authors of last commit for material with dest folder "hg"
* Verify cctray feed contains stage "defaultStage" job "basic-pipeline-for-cctray-job" with current activity "Sleeping" with label "1" and last status "Failure" with broken by authors of last commit for material with dest folder "hg"
* Verify cctray feed contains stage "defaultStage" job "passing-job" with current activity "Sleeping" with label "1" and last status "Success"
* Verify cctray feed contains stage "defaultStage" with relative weburl "/1/defaultStage/1"
* Verify cctray feed contains stage "defaultStage" and job "basic-pipeline-for-cctray-job" with relative weburl "/1/defaultStage/1/basic-pipeline-for-cctray-job"
* Verify cctray feed contains stage "defaultStage" and job "passing-job" with relative weburl "/1/defaultStage/1/passing-job"

* Looking at pipeline "basic-pipeline-for-cctray"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "2"

* Looking at pipeline "basic-pipeline-for-cctray" - CC Tray Feed Xml
* Verify cctray feed contains stage "defaultStage" with current activity "Building" with label "1" and last status "Failure"
* Verify cctray feed contains stage "defaultStage" job "basic-pipeline-for-cctray-job" with current activity "Building" with label "1" and last status "Failure"
* Verify cctray feed contains stage "defaultStage" job "passing-job" with current activity "Building" with label "1" and last status "Success"

* Stop "1" jobs that are waiting for file to exist

* Looking at pipeline "basic-pipeline-for-cctray"
* Verify stage "1" is "Passed" on pipeline with label "2"

* Looking at pipeline "basic-pipeline-for-cctray" - CC Tray Feed Xml
* Verify cctray feed contains stage "defaultStage" with current activity "Sleeping" with label "2" and last status "Success"
* Verify cctray feed contains stage "defaultStage" job "basic-pipeline-for-cctray-job" with current activity "Sleeping" with label "2" and last status "Success"
* Verify cctray feed contains stage "defaultStage" job "passing-job" with current activity "Sleeping" with label "2" and last status "Success"

* Looking at pipeline "basic-pipeline-for-cctray"
* Navigate to stage "defaultStage" of run "2"

* Rerun stage "defaultStage"

* On Pipeline Dashboard Page
* Looking at pipeline "basic-pipeline-for-cctray"
* Verify stage "1" is "Building" on pipeline with label "2"

* Fail "1" jobs that are waiting for file to exist

* Looking at pipeline "basic-pipeline-for-cctray"
* Verify stage "1" is "Failed" on pipeline with label "2"

CCTray feed should not contain the breakers info, because the buildcause hasn't changed.
* Looking at pipeline "basic-pipeline-for-cctray" - CC Tray Feed Xml
* Verify cctray feed contains stage "defaultStage" with current activity "Sleeping" with label "2 :: 2" and last status "Failure" with broken by authors of last commit for material with dest folder "hg"
* Verify cctray feed contains stage "defaultStage" job "basic-pipeline-for-cctray-job" with current activity "Sleeping" with label "2 :: 2" and last status "Failure" with broken by authors of last commit for material with dest folder "hg"
* Verify cctray feed contains stage "defaultStage" job "passing-job" with current activity "Sleeping" with label "2 :: 2" and last status "Success"
* Verify cctray feed contains stage "defaultStage" with relative weburl "/2/defaultStage/2"
* Verify cctray feed contains stage "defaultStage" and job "basic-pipeline-for-cctray-job" with relative weburl "/2/defaultStage/2/basic-pipeline-for-cctray-job"
* Verify cctray feed contains stage "defaultStage" and job "passing-job" with relative weburl "/2/defaultStage/2/passing-job"

* Looking at pipeline "admin-pipeline"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"

* Looking at pipeline "admin-pipeline" - CC Tray Feed Xml
* Verify cctray feed contains stage "defaultStage" with current activity "Sleeping" with label "1" and last status "Success"

* Logout - On Any Page

* Login as "view"

* Looking at pipeline "admin-pipeline" - CC Tray Feed Xml
* Verify cctray does not contain this pipeline with stage "defaultStage"
* Looking at pipeline "basic-pipeline-for-cctray" - CC Tray Feed Xml
* Verify cctray feed contains stage "defaultStage" with current activity "Sleeping" with label "2 :: 2" and last status "Failure"

* Logout - On Any Page

* Looking at pipeline "admin-pipeline" - CC Tray Feed Xml

verify cctray feed is not accessible




Teardown of contexts
* Capture go state "CCTrayFeed" - teardown
* With "1" live agents in directory "CCTrayFeed" - teardown
* Using pipeline "basic-pipeline-for-cctray, admin-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


