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

FanInOffBehaviorBlacklistingForTriangleConfiguration
====================================================

Setup of contexts
* Fanin configuration - setup
* Using pipeline "cruise, acceptance" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "FaninOffBehaviorBlacklistingForTriangleConfiguration" - setup

FanInOffBehaviorBlacklistingForTriangleConfiguration
----------------------------------------------------

tags: Blacklisting for triangle configuration, #6436, 6436, Fanin off, server_restart_needed

* Stop server
* Start server with fanin turned off

* On Pipeline Dashboard Page
* Looking at pipeline "cruise"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Looking at pipeline "acceptance"
* Verify stage "1" is "Passed" on pipeline with label "1"

* With material "git-one" for pipeline "cruise"
* Checkin file "new-file-1" as user "go <go@po.com>" with message "Added new-file 1"
* Remember current version as "git-one-c1"

* On Pipeline Dashboard Page
* Looking at pipeline "cruise"
* Verify pipeline is at label "1" and does not get triggered
* Looking at pipeline "acceptance"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"

Verify Build cause for Acceptance
* Looking at material of type "Pipeline" named "${runtime_name:cruise}" for pipeline "acceptance" with counter "2"
* Verify modification "0" has revision "${runtime_name:cruise}/1/defaultStage/1"
* Verify material has not changed
* Looking at material of type "Git" named "git-one" for pipeline "acceptance" with counter "2"
* Verify modification "0" has revision "git-one-c1"
* Verify material has changed

* With material "git-two" for pipeline "cruise"
* Checkin file "new-file-1" as user "go <go@po.com>" with message "Commit to git two"
* Remember current version as "git-two-c1"

* On Pipeline Dashboard Page
* Looking at pipeline "cruise"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"

* Looking at material of type "Git" named "git-one" for pipeline "cruise" with counter "2"
* Verify modification "0" has revision "git-one-c1"
* Verify material has changed
* Looking at material of type "Git" named "git-two" for pipeline "cruise" with counter "2"
* Verify modification "0" has revision "git-two-c1"
* Verify material has changed

* On Pipeline Dashboard Page
* Looking at pipeline "acceptance"
* Verify stage "1" is "Passed" on pipeline with label "3"
* Open changes section for counter "3"

* Looking at material of type "Pipeline" named "${runtime_name:cruise}" for pipeline "acceptance" with counter "3"
* Verify modification "0" has revision "${runtime_name:cruise}/2/defaultStage/1"
* Verify material has changed
* Looking at material of type "Git" named "git-one" for pipeline "acceptance" with counter "3"
* Verify modification "0" has revision "git-one-c1"
* Verify material has not changed




Teardown of contexts
____________________
* Capture go state "FaninOffBehaviorBlacklistingForTriangleConfiguration" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "cruise, acceptance" - teardown
* Fanin configuration - teardown


