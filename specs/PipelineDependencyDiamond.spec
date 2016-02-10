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

PipelineDependencyDiamond
=========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "d-up-left, d-up-right, d-down" - setup
* With "2" live agents in directory "StageDetails" - setup
* Capture go state "PipelineDependencyDiamond" - setup

PipelineDependencyDiamond
-------------------------

tags: diamond dependency, 3695, 3957, automate, fanin, New Item

                          git [g1, g2, g3, g4, g5, g6, g7]  ----------------------------------------+
                                 /                              \                                                          |
                               /                                 \                                                         |
                             /                                    \                                                        |
                           V                                      \                                                       |
left [l1(g1), l2(g3), l3(g5), l3(g7)                  V                                                     |
                    \                 right [r1(g1), r2(g2), r3(g4), r4(g5), r5(g6), r6(g3)]       |
                     \                                               /                                                    |
                      \                                            /                                                      |
                       \                                         /                                                        |
                        V                                      V                                                        |
                     down[d1(l1, r1, g1), d2(l3, r4, g5), d3(l2, r6, g3)] <------------------+

* Trigger pipelines "d-up-left,d-up-right" and wait for labels "1" to pass
* Looking at pipeline "d-down"
* Verify stage "1" is "Passed" on pipeline with label "1"

* With material "git-second" for pipeline "d-up-left"
* Checkin file "new-file-2" as user "go <go@po.com>" with message "Added new-file 2"
* Remember current version as "g2"

* Trigger pipelines "d-up-right" and wait for labels "2" to pass

On Pipeline Dashboard Page
looking at pipeline "d-down"
verify pipeline is at label "1" and does not get triggered

* With material "git-second" for pipeline "d-up-left"
* Checkin file "new-file-3" as user "go <go@po.com>" with message "Added new-file 3"
* Remember current version as "g3"

* Trigger pipelines "d-up-left" and wait for labels "2" to pass

On Pipeline Dashboard Page
looking at pipeline "d-down"
verify pipeline is at label "1" and does not get triggered

* With material "git-second" for pipeline "d-up-left"
* Checkin file "new-file-4" as user "go <go@po.com>" with message "Added new-file 4"
* Remember current version as "g4"

* Trigger pipelines "d-up-right" and wait for labels "3" to pass

* Looking at pipeline "d-down"
* Verify pipeline is at label "1" and does not get triggered

* With material "git-second" for pipeline "d-up-left"
* Checkin file "new-file-5" as user "go <go@po.com>" with message "Added new-file 5"
* Remember current version as "g5"

* Looking at pipeline "d-down"
* Pause pipeline with reason "waiting for new revisions to appear"
* Trigger pipelines "d-up-right" and wait for labels "4" to pass
* Trigger pipelines "d-up-left" and wait for labels "3" to pass

* With material "git-second" for pipeline "d-up-left"
* Checkin file "new-file-6" as user "go <go@po.com>" with message "Added new-file 6"
* Remember current version as "g6"

* Trigger pipelines "d-up-right" and wait for labels "5" to pass

* With material "git-second" for pipeline "d-up-left"
* Checkin file "new-file-7" as user "go <go@po.com>" with message "Added new-file 7"
* Remember current version as "g7"

* Trigger pipelines "d-up-left" and wait for labels "4" to pass

* Looking at pipeline "d-down"
* Unpause pipeline
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"

* Looking at material of type "Pipeline" named "${runtime_name:d-up-left}" for pipeline "d-down" with counter "2"
* Verify modification "0" has revision "${runtime_name:d-up-left}/3/defaultStage/1"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:d-up-right}" for pipeline "d-down" with counter "2"
* Verify modification "0" has revision "${runtime_name:d-up-right}/4/defaultStage/1"
* Verify material has changed
* Looking at material of type "Git" named "git-second" for pipeline "d-down" with counter "2"
* Verify modification "0" has revision "g5"
* Verify modification "1" has revision "g4"
* Verify modification "2" has revision "g3"
* Verify modification "3" has revision "g2"
* Verify material has changed

* On Pipeline Dashboard Page
* Looking at pipeline "d-up-right"
* Open trigger with options

* Select revision "5" in search box for material number "1"
* Deploy

* On Pipeline Dashboard Page
* Looking at pipeline "d-up-right"
* Verify stage "1" is "Passed" on pipeline with label "6"

* Looking at pipeline "d-down"
* Verify stage "1" is "Passed" on pipeline with label "3"
* Open changes section for counter "3"

* Looking at material of type "Pipeline" named "${runtime_name:d-up-left}" for pipeline "d-down" with counter "3"
* Verify modification "0" has revision "${runtime_name:d-up-left}/2/defaultStage/1"
* Verify material has not changed
* Looking at material of type "Pipeline" named "${runtime_name:d-up-right}" for pipeline "d-down" with counter "3"
* Verify modification "0" has revision "${runtime_name:d-up-right}/6/defaultStage/1"
* Verify material has changed
* Looking at material of type "Git" named "git-second" for pipeline "d-down" with counter "3"
* Verify modification "0" has revision "g3"
* Verify material has not changed

* On Pipeline Dashboard Page
* Looking at pipeline "d-down"
* Verify pipeline is at label "3" and does not get triggered





Teardown of contexts
____________________
* Capture go state "PipelineDependencyDiamond" - teardown
* With "2" live agents in directory "StageDetails" - teardown
* Using pipeline "d-up-left, d-up-right, d-down" - teardown
* Basic configuration - teardown


