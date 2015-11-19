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

FanInOffBehavior
================

Setup of contexts
* Fanin configuration - setup
* Using pipeline "C1, C2, C3, C4-auto-new, C5" - setup
* With "2" live agents in directory "StageDetails" - setup
* Capture go state "FanInOffBehavior" - setup

FanInOffBehavior
----------------

tags: diamond dependency, fanin, auto, server_restart_needed


* With material "git-one" for pipeline "C1"
* Checkin file "new-file-2" as user "go <go@po.com>" with message "Added new-file 2"
* Remember current version as "git-one-g2"


* With material "git-two" for pipeline "C1"
* Checkin file "new-file-2" as user "go <go@po.com>" with message "Added new-file 2"
* Remember current version as "git-two-g2"


* Trigger pipelines "C1" and wait for labels "1" to pass
* Trigger pipelines "C2" and wait for labels "1" to pass
* Trigger pipelines "C5" and wait for labels "1" to pass
* Trigger pipelines "C3" and wait for labels "1" to pass
* Looking at pipeline "C4-auto-new"
* Verify stage "1" is "Passed" on pipeline with label "1"


* Stop server
* Start server with fanin turned off


* With material "git-two" for pipeline "C1"
* Checkin file "new-file-3" as user "go <go@po.com>" with message "Added new-file 3"
* Remember current version as "git-two-g3"

* On Pipeline Dashboard Page
* Trigger pipelines "C5" and wait for labels "2" to pass


* Looking at pipeline "C4-auto-new"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"


* Looking at material of type "Pipeline" named "${runtime_name:C1}" for pipeline "C4-auto-new" with counter "2"
* Verify modification "0" has revision "${runtime_name:C1}/1/defaultStage/1"
* Verify material has not changed
* Looking at material of type "Pipeline" named "${runtime_name:C2}" for pipeline "C4-auto-new" with counter "2"
* Verify modification "0" has revision "${runtime_name:C2}/1/defaultStage/1"
* Verify material has not changed
* Looking at material of type "Pipeline" named "${runtime_name:C3}" for pipeline "C4-auto-new" with counter "2"
* Verify modification "0" has revision "${runtime_name:C3}/1/defaultStage/1"
* Verify material has not changed
* Looking at material of type "Pipeline" named "${runtime_name:C5}" for pipeline "C4-auto-new" with counter "2"
* Verify modification "0" has revision "${runtime_name:C5}/2/defaultStage/1"
* Verify material has changed
* Looking at material of type "Git" named "git-one" for pipeline "C4-auto-new" with counter "2"
* Verify modification "0" has revision "git-one-g2"
* Verify material has not changed

* With material "git-one" for pipeline "C1"
* Checkin file "new-file-3" as user "go <go@po.com>" with message "Added new-file 3"
* Remember current version as "git-one-g3"

* On Pipeline Dashboard Page
* Trigger pipelines "C2" and wait for labels "2" to pass

* Looking at pipeline "C4-auto-new"
* Verify pipeline is at label "2" and does not get triggered







Teardown of contexts
* Capture go state "FanInOffBehavior" - teardown
* With "2" live agents in directory "StageDetails" - teardown
* Using pipeline "C1, c2, c3, c4-auto-new, c5" - teardown
* Fanin configuration - teardown


