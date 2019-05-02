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

JobRerunRunOnAllAgents
======================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-for-job-rerun-run-on-all" - setup
* Capture go state "JobRerunRunOnAllAgents" - setup

JobRerunRunOnAllAgents
----------------------

tags: 4137, stage-details, job-rerun, scheduling, rerun, run-on-all

* Looking at pipeline "pipeline-for-job-rerun-run-on-all" - Configure Pipeline
* Set run on all agents to "true" for job in pipeline "first"

* Enable an agent showing status "Disabled" - Using Agents API
* Add environment "env-job-rerun" to all agents - Using Agents API

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-for-job-rerun-run-on-all"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"


* Cancel "defaultStage" - Already On Stage Detail Page
* Go to jobs tab

* Verify job "first-runOnAll-1" has state "Completed" and result "Cancelled"
* Verify job "first-runOnAll-2" has state "Completed" and result "Cancelled"
* Remember that job "first-runOnAll-1" ran on agent "agent1"
* Remember that job "first-runOnAll-2" ran on agent "agent2"
* Rerun "first-runOnAll-1,first-runOnAll-2" jobs
* Verify job "first-runOnAll-1" ran on agent "agent1"
* Verify job "first-runOnAll-2" ran on agent "agent2"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-for-job-rerun-run-on-all"
* Navigate to stage "defaultStage" of run "1" having counter "2"

* Cancel "defaultStage" - Already On Stage Detail Page
* Go to jobs tab

* Looking at pipeline "pipeline-for-job-rerun-run-on-all" - Configure Pipeline
* Set run on all agents to "false" for job in pipeline "first"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-for-job-rerun-run-on-all"
* Navigate to stage "defaultStage" of run "1" having counter "2"

* Go to jobs tab

* Rerun "first-runOnAll-1" jobs
* Verify looking at "defaultStage" having counter "3"
* Verify job "first" has state "Scheduled" and result "Active"
* Verify job "first-runOnAll-2" has state "Completed" and result "Cancelled"
* Verify job "first" ran on agent "agent1"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-for-job-rerun-run-on-all"
* Navigate to stage "defaultStage" of run "1" having counter "3"

* Cancel "defaultStage" - Already On Stage Detail Page
* Go to jobs tab

* Rerun "first,first-runOnAll-2" jobs
* Verify rerun failed with cause "Cannot schedule multiple instances of job named 'first'."

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-for-job-rerun-run-on-all"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "2"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Cancel "defaultStage" - Already On Stage Detail Page

* Looking at pipeline "pipeline-for-job-rerun-run-on-all" - Configure Pipeline
* Set run on all agents to "true" for job in pipeline "first"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-for-job-rerun-run-on-all"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Go to jobs tab

* Rerun "first" jobs
* Verify looking at "defaultStage" having counter "2"
* Verify job "first-runOnAll-1" has state "Scheduled" and result "Active"
* Verify job "first-runOnAll-2" has state "Scheduled" and result "Active"





Teardown of contexts
____________________
* Capture go state "JobRerunRunOnAllAgents" - teardown
* Using pipeline "pipeline-for-job-rerun-run-on-all" - teardown
* Basic configuration - teardown


