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

A_Pipeline_Stage_Job_AgentJobRun_HistoryApis
==========================================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline-fast,run.till.file.exists,pipeline-with-failing-stage" - setup
* With "1" live agents in directory "HistoryApis" - setup
* Capture go state "Pipeline_Stage_Job_AgentJobRun_HistoryApis" - setup

A_Pipeline_Stage_Job_AgentJobRun_HistoryApis
------------------------------------------

tags: 3351, automate, 3086, schedule, trigger, restful api, 3453


* Looking at pipeline "basic-pipeline-fast"
* Trigger the pipeline "11" times starting at counter "0"

// verify passed + pagination

* Verify "11" instances of "basic-pipeline-fast" "defaultStage" "defaultJob" "Passed"
* Verify "11" instance of "basic-pipeline-fast" "defaultStage" "defaultJob" "Passed"

* Verify "11" instances of "basic-pipeline-fast" "defaultStage" "defaultJob" "Passed" - Using Stage Api
* Verify "11" instance of "basic-pipeline-fast" "defaultStage" "defaultJob" "Passed" - Using Stage Api

* Verify "11" instances of "basic-pipeline-fast" "defaultStage" "defaultJob" "Passed" - Using Job Api

* Verify "11" instances of "basic-pipeline-fast" "defaultStage" "defaultJob" "Passed" - Using Agents Api

* Looking at pipeline "pipeline-with-failing-stage"
* Trigger pipeline
* Verify stage "1" is "Failed" on pipeline with label "1"

// verify failed

* Verify "1" instances of "pipeline-with-failing-stage" "defaultStage" "defaultJob" "Failed"
* Verify "1" instance of "pipeline-with-failing-stage" "defaultStage" "defaultJob" "Failed"

* Verify "1" instances of "pipeline-with-failing-stage" "defaultStage" "defaultJob" "Failed" - Using Stage Api
* Verify "1" instance of "pipeline-with-failing-stage" "defaultStage" "defaultJob" "Failed" - Using Stage Api

* Verify "1" instances of "pipeline-with-failing-stage" "defaultStage" "defaultJob" "Failed" - Using Job Api

* Verify last job "pipeline-with-failing-stage" "defaultStage" "defaultJob" "Failed"

* Looking at pipeline "run.till.file.exists"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"
* Navigate to stage "default.stage" of run "1"

// verify building

* Verify "1" instances of "run.till.file.exists" "default.stage" "default.job" "Unknown"
* Verify "1" instance of "run.till.file.exists" "default.stage" "default.job" "Unknown"

* Verify "1" instances of "run.till.file.exists" "default.stage" "default.job" "Unknown" - Using Stage Api
* Verify "1" instance of "run.till.file.exists" "default.stage" "default.job" "Unknown" - Using Stage Api

* Verify "1" instances of "run.till.file.exists" "default.stage" "default.job" "Unknown" - Using Job Api

* Cancel "default.stage" - Already On Stage Detail Page
* Verify stage result shows "Cancelled"

// verify cancelled

* Verify "1" instances of "run.till.file.exists" "default.stage" "default.job" "Cancelled"
* Verify "1" instance of "run.till.file.exists" "default.stage" "default.job" "Cancelled"

* Verify "1" instances of "run.till.file.exists" "default.stage" "default.job" "Cancelled" - Using Stage Api
* Verify "1" instance of "run.till.file.exists" "default.stage" "default.job" "Cancelled" - Using Stage Api

* Verify "1" instances of "run.till.file.exists" "default.stage" "default.job" "Cancelled" - Using Job Api

* Verify last job "run.till.file.exists" "default.stage" "default.job" "Cancelled"





Teardown of contexts
____________________
* Capture go state "HistoryApis" - teardown
* With "1" live agents in directory "HistoryApis" - teardown
* Using pipeline "basic-pipeline-fast,run.till.file.exists,pipeline-with-failing-stage" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


