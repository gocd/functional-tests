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

StageDetailPipelineDependenciesTab
==================================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline-with-2-stages-run-till-file-exists, up, down1, down2, down3, down4, abyss" - setup
* With "3" live agents in directory "StageDetails" - setup
* Capture go state "StageDetailPipelineDependenciesTab" - setup

StageDetailPipelineDependenciesTab
----------------------------------

tags: 3688, pipeline, automate

* Looking at pipeline "pipeline-with-2-stages-run-till-file-exists"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"
* Navigate to pipeline dependencies for "pipeline-with-2-stages-run-till-file-exists" "1" "first" "1"

* Verify stage "second" does not have actions link - Already On Stage Detail Pipeline Dependencies Page
* Cancel "first" - Already On Stage Detail Pipeline Dependencies Page
* Trigger stage "second" - Already On Stage Detail Pipeline Dependencies Page
* Verify stage "first" does not have actions link - Already On Stage Detail Pipeline Dependencies Page
* Cancel "second" - Already On Stage Detail Pipeline Dependencies Page
* Verify stage "first" has action "Rerun" - Already On Stage Detail Pipeline Dependencies Page
* Verify stage "second" has action "Rerun" - Already On Stage Detail Pipeline Dependencies Page

* On Pipeline Dashboard Page
* Trigger pipelines "up" and wait for labels "1" to pass
* Trigger pipelines "down1,down2,down3" and wait for labels "1" to pass
* Trigger pipelines "down1,down2" and wait for labels "2" to pass
* Trigger pipelines "abyss" and wait for labels "1" to pass
* Navigate to pipeline dependencies for "up" "1" "defaultStage" "1"

* Current pipeline being "up"
* Verify pipeline has label "1"
* Verify pipeline depends on "phigar"
* Verfiy pipeline has dependencies "down1,down2,down3,down4"
* Verify dependency "down1" has runs "1,2"
* Verify dependency "down2" has runs "1,2"
* Verify dependency "down3" has runs "1"
* Verify dependency "down4" has no runs
* Navigate to pipeline dependencies for "down1" "2" "defaultStage" "1" - Already On Stage Detail Pipeline Dependencies Page

* Current pipeline being "down1"
* Verify pipeline has label "2"
* Verify pipeline depends on "up,itemmu"
* Verfiy pipeline has dependencies "abyss"
* Verify dependency "abyss" has runs "1"

* Removing pipeline "down3"
* Making pipeline "down2" not depend on "up"

* On Pipeline Dashboard Page
* Navigate to pipeline dependencies for "up" "1" "defaultStage" "1"

* Current pipeline being "up"
* Verfiy pipeline has dependencies "down1,down2,down4"




Teardown of contexts
* Capture go state "StageDetailPipelineDependenciesTab" - teardown
* With "3" live agents in directory "StageDetails" - teardown
* Using pipeline "pipeline-with-2-stages-run-till-file-exists, up, down1, down2, down3, down4, abyss" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


