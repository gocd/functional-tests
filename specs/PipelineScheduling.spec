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

PipelineScheduling
==================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline-2-manual-stages-that-run-till-file-exists" - setup
* With "1" live agents in directory "PipelineScheduling" - setup
* Capture go state "PipelineScheduling" - setup

PipelineScheduling
------------------

tags: restful api, #2482, svn support, scheduling, diagnostics messages, rerun, 1646, scheduling, svn support, ant support, automate, stage1

* Looking at pipeline "pipeline-2-manual-stages-that-run-till-file-exists"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"
* Navigate to stage detail page for "pipeline-2-manual-stages-that-run-till-file-exists" "1" "defaultStage" "1" with stage history page size "1"

* Verify stage "defaultStage" has action "Cancel"
* Verify stage "defaultStage" does not have action "Rerun"

* For pipeline named "pipeline-2-manual-stages-that-run-till-file-exists"
* Schedule should return code "422"

* Stop "1" jobs that are waiting for file to exist

* On Pipeline Dashboard Page
* Navigate to stage detail page for "pipeline-2-manual-stages-that-run-till-file-exists" "1" "defaultStage" "1" with stage history page size "1"

* Wait for stage result to show "Passed"
* Trigger stage "secondStage"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-2-manual-stages-that-run-till-file-exists"
* Verify stage "2" is "Building" on pipeline with label "1"
* Navigate to stage detail page for "pipeline-2-manual-stages-that-run-till-file-exists" "1" "secondStage" "1" with stage history page size "1"

* Verify stage "secondStage" has action "Cancel"
* Verify stage "secondStage" does not have action "Rerun"

* For pipeline named "pipeline-2-manual-stages-that-run-till-file-exists"
* Schedule should return code "422"

* Stop "1" jobs that are waiting for file to exist




Teardown of contexts
____________________
* Capture go state "PipelineScheduling" - teardown
* With "1" live agents in directory "PipelineScheduling" - teardown
* Using pipeline "pipeline-2-manual-stages-that-run-till-file-exists" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


