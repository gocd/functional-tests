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

FanInBehaviorWithDifferentConfiguration
=======================================

Setup of contexts
* Fanin configuration - setup
* Using pipeline "C1, C2, C3, C4-man, C5, C6-auto-s" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "FanInBehaviorWithDifferentConfiguration" - setup

FanInBehaviorWithDifferentConfiguration
---------------------------------------

tags: diamond dependency, fanin, auto, 6201

* With material "git-one" for pipeline "C1"
* Checkin file "new-file-2" as user "go <go@po.com>" with message "Added new-file 2"
* Remember current version as "git-one-g2"


* With material "git-two" for pipeline "C1"
* Checkin file "new-file-2" as user "go <go@po.com>" with message "Added new-file 2"
* Remember current version as "git-two-g2"

* Trigger pipelines "C1" and wait for labels "1" to pass
* Trigger pipelines "C2" and wait for labels "1" to pass
* Trigger pipelines "C3" and wait for labels "1" to pass
* Trigger pipelines "C4-man" and wait for labels "1" to pass
* Trigger pipelines "C5" and wait for labels "1" to pass

* Looking at pipeline "C6-auto-s"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Open changes section for counter "1"

* Looking at material of type "Pipeline" named "${runtime_name:C4-man}" for pipeline "C6-auto-s" with counter "1"
* Verify modification "0" has revision "${runtime_name:C4-man}/1/defaultStage/1"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:C1}" for pipeline "C6-auto-s" with counter "1"
* Verify modification "0" has revision "${runtime_name:C1}/1/defaultStage/1"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:C5}" for pipeline "C6-auto-s" with counter "1"
* Verify modification "0" has revision "${runtime_name:C5}/1/defaultStage/1"
* Verify material has changed
* Looking at material of type "Git" named "git-one" for pipeline "C6-auto-s" with counter "1"
* Verify modification "0" has revision "git-one-g2"
* Verify material has changed

* With material "git-two" for pipeline "C1"
* Checkin file "new-file-3" as user "go <go@po.com>" with message "Added new-file 3"
* Remember current version as "git-two-g3"

* On Pipeline Dashboard Page
* Trigger pipelines "C1" and wait for labels "2" to pass
* Trigger pipelines "C5" and wait for labels "2" to pass
* Trigger pipelines "C4-man" and wait for labels "2" to pass
* Looking at pipeline "C4-man"
* Open changes section for counter "2"


* Looking at material of type "Pipeline" named "${runtime_name:C1}" for pipeline "C4-man" with counter "2"
* Verify modification "0" has revision "${runtime_name:C1}/2/defaultStage/1"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:C2}" for pipeline "C4-man" with counter "2"
* Verify modification "0" has revision "${runtime_name:C2}/1/defaultStage/1"
* Verify material has not changed
* Looking at material of type "Pipeline" named "${runtime_name:C3}" for pipeline "C4-man" with counter "2"
* Verify modification "0" has revision "${runtime_name:C3}/1/defaultStage/1"
* Verify material has not changed

* On Pipeline Dashboard Page
* Looking at pipeline "C6-auto-s"
* Verify pipeline is at label "1" and does not get triggered


* Trigger pipelines "C3" and wait for labels "2" to pass
* Trigger pipelines "C4-man" and wait for labels "3" to pass


* Looking at pipeline "C6-auto-s"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"


* Looking at material of type "Pipeline" named "${runtime_name:C4-man}" for pipeline "C6-auto-s" with counter "2"
* Verify modification "0" has revision "${runtime_name:C4-man}/3/defaultStage/1"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:C1}" for pipeline "C6-auto-s" with counter "2"
* Verify modification "0" has revision "${runtime_name:C1}/2/defaultStage/1"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:C5}" for pipeline "C6-auto-s" with counter "2"
* Verify modification "0" has revision "${runtime_name:C5}/2/defaultStage/1"
* Verify material has changed
* Looking at material of type "Git" named "git-one" for pipeline "C6-auto-s" with counter "2"
* Verify modification "0" has revision "git-one-g2"
* Verify material has not changed




Teardown of contexts
* Capture go state "FanInBehaviorWithDifferentConfiguration" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "C1, c2, c3, c4-man, c5, c6-auto-s" - teardown
* Fanin configuration - teardown


