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

FanInBehaviorOnUpdateOfMaterial
===============================

Setup of contexts
* Fanin configuration - setup
* Using pipeline "d-up-left, d-up-right, d-down, basic-auto-pipeline" - setup
* With "1" live agents in directory "Stage details" - setup
* Capture go state "FanInBehaviorOnChangeOfMaterials" - setup

FanInBehaviorOnUpdateOfMaterial
-------------------------------

tags: diamond dependency, fanin, material-change

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


* Trigger pipelines "d-up-left" and wait for labels "1" to pass
* Trigger pipelines "d-up-right" and wait for labels "1" to pass
* Looking at pipeline "d-down"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Verify pipeline "d-down" is triggered by "changes"
* Navigate to the pipeline history page for pipeline "d-down"

* Looking at pipeline with label "1"
* Verify pipeline is triggered by "changes"

* On Pipeline Configuration wizard
* Click on pipeline "d-up-left" for editing

* Open material listing page

* Edit material "git-second"

* Copy over material url from material named "git" in pipeline "${runtime_name:basic-auto-pipeline}" to current pipeline
* Check connectivity should be successful - Already on Git Material Creation Popup
* Click save - Already on git material creation popup
* Verify saved successfully - Already on Git Material Creation Popup

* On Pipeline Configuration wizard
* Click on pipeline "d-up-right" for editing

* Open material listing page

* Edit material "git-second"

* Copy over material url from material named "git" in pipeline "${runtime_name:basic-auto-pipeline}" to current pipeline
* Check connectivity should be successful - Already on Git Material Creation Popup
* Click save - Already on git material creation popup
* Verify saved successfully - Already on Git Material Creation Popup

* With material "git" for pipeline "basic-auto-pipeline"
* Checkin file "new-file-2" as user "go <go@po.com>" with message "Added new-file 2"
* Remember current version as "g2"

* On Pipeline Dashboard Page
* Trigger pipelines "d-up-left" and wait for labels "2" to pass
* Looking at pipeline "d-down"
* Verify pipeline is at label "1" and does not get triggered

* Trigger pipelines "d-up-right" and wait for labels "2" to pass
* Looking at pipeline "d-down"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"

* Looking at material of type "Pipeline" named "${runtime_name:d-up-left}" for pipeline "d-down" with counter "2"
* Verify modification "0" has revision "${runtime_name:d-up-left}/2/defaultStage/1"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:d-up-right}" for pipeline "d-down" with counter "2"
* Verify modification "0" has revision "${runtime_name:d-up-right}/2/defaultStage/1"
* Verify material has changed






Teardown of contexts
____________________
* Capture go state "FanInBehaviorOnChangeOfMaterials" - teardown
* With "1" live agents in directory "Stage details" - teardown
* Using pipeline "d-up-left, d-up-right, d-down, basic-auto-pipeline" - teardown
* Fanin configuration - teardown


