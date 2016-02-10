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

AgentDetails
============

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "normal-pipeline, basic,failing-pipeline" - setup
* With "1" live agents in directory "AgentDetails" - setup
* Capture go state "AgentDetails" - setup

AgentDetails
------------

tags: 2090, agents

* Trigger pipelines "normal-pipeline" and wait for labels "1" to pass
* Trigger pipelines "basic" and wait for label "1" to pass for stage "2"

* Trigger pipeline "failing-pipeline"
* Wait for labels "1" to fail
* Navigate to stage "failingStage" of run "1" having counter "1"

make sure the agent has completed the job
* Verify the lock status is "Click to unlock"
* Unlock the pipeline

* Click on the live agent to go to the details page

* Verify presence of tabs "Details, Job Run History"
* Verify presence of labels "Free Space:, Sandbox:, IP Address:, OS:, Resources:, Environments:"
* Navigate to job run history

sorted by completed date by default
* Verify presence of sortable columns "Pipeline, Stage, Job, Result, Completed"
* Verify presence of unsortable columns "Duration"
* Verify row "1" has pipeline "${runtime_name: failing-pipeline}" stage "failingStage" job "failingJob" for pipeline counter "1" and stage counter "1" with result "Failed"
* Verify row "2" has pipeline "${runtime_name: basic}" stage "secondStage" job "secondJob" for pipeline counter "1" and stage counter "1" with result "Passed"
* Verify row "3" has pipeline "${runtime_name: basic}" stage "defaultStage" job "defaultJob" for pipeline counter "1" and stage counter "1" with result "Passed"
* Verify row "4" has pipeline "${runtime_name: normal-pipeline}" stage "a-stage" job "z-job" for pipeline counter "1" and stage counter "1" with result "Passed"

sort by pipeline name
* Sort by "Pipeline"
* Verify row "1" has pipeline "${runtime_name: basic}" stage "defaultStage" job "defaultJob" for pipeline counter "1" and stage counter "1" with result "Passed"
* Verify row "2" has pipeline "${runtime_name: basic}" stage "secondStage" job "secondJob" for pipeline counter "1" and stage counter "1" with result "Passed"
* Verify row "3" has pipeline "${runtime_name: failing-pipeline}" stage "failingStage" job "failingJob" for pipeline counter "1" and stage counter "1" with result "Failed"
* Verify row "4" has pipeline "${runtime_name: normal-pipeline}" stage "a-stage" job "z-job" for pipeline counter "1" and stage counter "1" with result "Passed"

sort by result
* Sort by "Result"
* Verify row "1" has pipeline "${runtime_name: failing-pipeline}" stage "failingStage" job "failingJob" for pipeline counter "1" and stage counter "1" with result "Failed"
* Verify row "2" has pipeline "${runtime_name: normal-pipeline}" stage "a-stage" job "z-job" for pipeline counter "1" and stage counter "1" with result "Passed"
* Verify row "3" has pipeline "${runtime_name: basic}" stage "defaultStage" job "defaultJob" for pipeline counter "1" and stage counter "1" with result "Passed"
* Verify row "4" has pipeline "${runtime_name: basic}" stage "secondStage" job "secondJob" for pipeline counter "1" and stage counter "1" with result "Passed"

sort by stage
* Sort by "Stage"
* Verify row "1" has pipeline "${runtime_name: normal-pipeline}" stage "a-stage" job "z-job" for pipeline counter "1" and stage counter "1" with result "Passed"
* Verify row "2" has pipeline "${runtime_name: basic}" stage "defaultStage" job "defaultJob" for pipeline counter "1" and stage counter "1" with result "Passed"
* Verify row "3" has pipeline "${runtime_name: failing-pipeline}" stage "failingStage" job "failingJob" for pipeline counter "1" and stage counter "1" with result "Failed"
* Verify row "4" has pipeline "${runtime_name: basic}" stage "secondStage" job "secondJob" for pipeline counter "1" and stage counter "1" with result "Passed"

sort by job
* Sort by "Job"
* Verify row "1" has pipeline "${runtime_name: basic}" stage "defaultStage" job "defaultJob" for pipeline counter "1" and stage counter "1" with result "Passed"
* Verify row "2" has pipeline "${runtime_name: failing-pipeline}" stage "failingStage" job "failingJob" for pipeline counter "1" and stage counter "1" with result "Failed"
* Verify row "3" has pipeline "${runtime_name: basic}" stage "secondStage" job "secondJob" for pipeline counter "1" and stage counter "1" with result "Passed"
* Verify row "4" has pipeline "${runtime_name: normal-pipeline}" stage "a-stage" job "z-job" for pipeline counter "1" and stage counter "1" with result "Passed"

* On Pipeline Dashboard Page

* Looking at pipeline "failing-pipeline"
* Navigate to stage "failingStage" of run "1" having counter "1"

* Go to jobs tab

* Click on agent for job "failingJob"

verify it navigated to agent details page
* Navigate to job run history

* On Pipeline Dashboard Page

* Looking at pipeline "failing-pipeline"
* Navigate to stage "failingStage" of run "1" having counter "1"

* Navigate to job "failingJob"

* Click on the agent the job ran on

verify it navigated to agent details page
* Navigate to job run history

verify that view and operate user cannot see job history tab
* Logout and login as "group1Admin"

* On Agents Page

* Click on the live agent to go to the details page

* Verify presence of tabs "Details"
* Verify absence of tabs "Job Run History"

verify that a groupadmin cannot see job history tab
* Logout and login as "group2Admin"

* On Agents Page

* Click on the live agent to go to the details page

* Verify presence of tabs "Details"
* Verify absence of tabs "Job Run History"




Teardown of contexts
____________________
* Capture go state "AgentDetails" - teardown
* With "1" live agents in directory "AgentDetails" - teardown
* Using pipeline "normal-pipeline, basic,failing-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


