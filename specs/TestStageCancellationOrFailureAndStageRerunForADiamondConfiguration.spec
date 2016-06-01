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

TestStageCancellationOrFailureAndStageRerunForADiamondConfiguration
===================================================================

Setup of contexts
* Fanin configuration - setup
* Using pipeline "Upstream1,Upstream2,Downstream1" - setup
* With "2" live agents in directory "StageDetails" - setup
* Capture go state "FanInTestStageCancellationOrFailureAndStageRerunForADiamondConfiguration " - setup

TestStageCancellationOrFailureAndStageRerunForADiamondConfiguration
-------------------------------------------------------------------

tags: Stage Cancellation, Stage Rerun, Fanin, #6438, 6438, Diamond Configuration, long_running

Let all pipelines run once
* Trigger stage "1" of pipeline "Upstream1" with a _ run _ till _ file _ exists _ job "1" and wait for label "1" to be "Passed"
* Trigger pipelines "Upstream2" and wait for labels "1" to pass
* Looking at pipeline "Downstream1"
* Verify stage "1" is "Passed" on pipeline with label "1"

Make a commit
* With material "git-one" for pipeline "Upstream1"
* Checkin file "new-file-1" as user "go <go@po.com>" with message "Added new-file 1"
* Remember current version as "git-one-c1"

* Trigger stage "1" of pipeline "Upstream1" with a _ run _ till _ file _ exists _ job "1" and wait for label "2" to be "Failed"

Make another commit
* With material "git-one" for pipeline "Upstream1"
* Checkin file "new-file-2" as user "go <go@po.com>" with message "Added new-file 2"
* Remember current version as "git-one-c2"

Let Upstream1 and Upstream 2 pass with incompatible revisions for git, verify Downstream1 does not get triggered
* Trigger pipelines "Upstream2" and wait for labels "2" to pass


On Pipeline Dashboard Page
looking at pipeline "Downstream1"
Verify pipeline is at label "1" and does not get triggered

* Looking at pipeline "Downstream1"
* Navigate to stage detail page for "Upstream1" "2" "defaultStage" "1" with stage history page size "1"
* Rerun stage "defaultStage"

* Stop "1" jobs that are waiting for file to exist

* On Pipeline Dashboard Page
* Looking at pipeline "Upstream1"
* Verify stage "1" is "Passed" on pipeline with label "2"

* Looking at pipeline "Downstream1"
* Verify pipeline is at label "1" and does not get triggered

Trigger Upstream2 with revision compatible with Upstream1, Downstream1 gets triggered
* Looking at pipeline "Upstream2"
* Open trigger with options

* Select revision "2" in search box for material number "1"
* Deploy
* Trigger pipelines "Upstream2" and wait for labels "3" to pass

* On Pipeline Dashboard Page
* Looking at pipeline "Downstream1"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"

Verify Build cause for Downstream1
* Looking at material of type "Pipeline" named "${runtime_name:Upstream1}" for pipeline "Downstream1" with counter "2"
* Verify modification "0" has revision "${runtime_name:Upstream1}/2/defaultStage/2"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:Upstream2}" for pipeline "Downstream1" with counter "2"
* Verify modification "0" has revision "${runtime_name:Upstream2}/3/defaultStage/1"
* Verify material has changed

Make a commit
* With material "git-one" for pipeline "Upstream1"
* Checkin file "new-file-3" as user "go <go@po.com>" with message "Added new-file 3"
* Remember current version as "git-one-c3"

Trigger Upsteam2|4 and cancel it

* For template "upstream_2_template"
* For stage "defaultStage"
* For job "defaultJob"
* Add custom command "sleep:300"

* On Pipeline Dashboard Page
* Looking at pipeline "Upstream2"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "4"
* Navigate to stage "defaultStage" of run "4"

* Cancel "defaultStage" - Already On Stage Detail Page
* Wait for stage result to show "Cancelled"

* For template "upstream_2_template"
* For stage "defaultStage"
* For job "defaultJob"
* Remove custom command "sleep:300"

* On Pipeline Dashboard Page
* Trigger stage "1" of pipeline "Upstream1" with a _ run _ till _ file _ exists _ job "1" and wait for label "3" to be "Passed"

On Pipeline Dashboard Page
looking at pipeline "Downstream1"
Verify pipeline is at label "2" and does not get triggered

* With material "git-one" for pipeline "Upstream1"
* Checkin file "new-file-4" as user "go <go@po.com>" with message "Added new-file 4"
* Remember current version as "git-one-c4"

* On Pipeline Dashboard Page
* Trigger stage "1" of pipeline "Upstream1" with a _ run _ till _ file _ exists _ job "1" and wait for label "4" to be "Passed"

* On Pipeline Dashboard Page
* Looking at pipeline "Downstream1"
* Verify pipeline is at label "2" and does not get triggered


Rerun stage for Upstream2|4, Verify pipeline Downstream1|3 gets triggered
* Navigate to stage detail page for "Upstream2" "4" "defaultStage" "1" with stage history page size "1"
* Rerun stage "defaultStage"

* On Pipeline Dashboard Page
* Looking at pipeline "Upstream2"
* Wait for labels "4" to pass
* Looking at pipeline "Downstream1"
* Verify stage "1" is "Passed" on pipeline with label "3"
* Open changes section for counter "3"


Verify Build cause for Downstream1
* Looking at material of type "Pipeline" named "${runtime_name:Upstream1}" for pipeline "Downstream1" with counter "3"
* Verify modification "0" has revision "${runtime_name:Upstream1}/3/defaultStage/1"
* Verify material has changed
* Looking at material of type "Pipeline" named "${runtime_name:Upstream2}" for pipeline "Downstream1" with counter "3"
* Verify modification "0" has revision "${runtime_name:Upstream2}/4/defaultStage/2"
* Verify material has changed






Teardown of contexts
____________________
* Capture go state "FanInTestStageCancellationOrFailureAndStageRerunForADiamondConfiguration " - teardown
* With "2" live agents in directory "StageDetails" - teardown
* Using pipeline "Upstream1,Upstream2,Downstream1" - teardown
* Fanin configuration - teardown


