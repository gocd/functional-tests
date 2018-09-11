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

FanInBehaviorForInconsistentRevisions
=====================================

Setup of contexts
* Fanin configuration - setup
* Using pipeline "FIR1, FIR2, FIR3" - setup
* With "2" live agents in directory "FanInBehaviorForInconsistentRevisions" - setup
* Capture go state "FanInBehaviorForInconsistentRevisions" - setup

FanInBehaviorForInconsistentRevisions
-------------------------------------

tags: diamond dependency, fanin, auto, 6451

* With material "git-one" for pipeline "FIR1"
* Checkin file "new-file-1" as user "go <go@po.com>" with message "Added new-file 1"
* Remember current version as "g1"


* Trigger pipelines "FIR1" and wait for labels "1" to pass
* Trigger pipelines "FIR2" and wait for labels "1" to pass
* Looking at pipeline "FIR3"
* Wait for labels "1" to pass

* With material "git-one" for pipeline "FIR1"
* Checkin file "new-file-2" as user "go <go@po.com>" with message "Added new-file 2"
* Remember current version as "g2"

* On Pipeline Dashboard Page
* Trigger pipelines "FIR1" and wait for labels "2" to pass
* Trigger pipelines "FIR2" and wait for labels "2" to pass
* Looking at pipeline "FIR3"
* Wait for labels "2" to pass

* With material "git-one" for pipeline "FIR1"
* Checkin file "new-file-3" as user "go <go@po.com>" with message "Added new-file 3"
* Remember current version as "g3"

* On Pipeline Dashboard Page
* Trigger pipelines "FIR1" and wait for labels "3" to pass
* Trigger pipelines "FIR2" and wait for labels "3" to pass
* Looking at pipeline "FIR3"
* Wait for labels "3" to pass

* On Pipeline Dashboard Page
* Looking at pipeline "FIR3"
* Pause pipeline with reason "wait for FIR1 and FIR2 to complete"
* Trigger pipelines "FIR1" and wait for labels "4" to pass
* Trigger pipelines "FIR2" and wait for labels "4" to pass
* Looking at pipeline "FIR3"
* Unpause pipeline
* Wait for labels "4" to pass

* On Pipeline Dashboard Page
* Looking at pipeline "FIR2"
* Open trigger with options

* Using material "git-one"
* Search for "Added new-file 1"
* Select revision "1" in search box
* Trigger

* On Pipeline Dashboard Page
* Looking at pipeline "FIR2"
* Wait for labels "5" to pass
* Looking at pipeline "FIR3"
* Wait for labels "5" to pass
* Open changes section for counter "5"

* Looking at material of type "Pipeline" named "${runtime_name:FIR2}" for pipeline "FIR3" with counter "5"
* Verify modification "0" has revision "${runtime_name:FIR2}/5/stage1/1"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:FIR1}" for pipeline "FIR3" with counter "5"
* Verify modification "0" has revision "${runtime_name:FIR1}/1/stage1/1"
* Verify material has not changed

* On Pipeline Dashboard Page
* Looking at pipeline "FIR1"
* Open trigger with options

* Using material "git-one"
* Search for "Added new-file 3"
* Select revision "1" in search box
* Trigger

* On Pipeline Dashboard Page
* Looking at pipeline "FIR1"
* Wait for labels "5" to pass
* Looking at pipeline "FIR3"
* Verify pipeline is at label "5" and does not get triggered




Teardown of contexts
____________________
* Capture go state "FanInBehaviorForInconsistentRevisions" - teardown
* With "2" live agents in directory "FanInBehaviorForInconsistentRevisions" - teardown
* Using pipeline "FIR1, fIR2, fIR3" - teardown
* Fanin configuration - teardown


