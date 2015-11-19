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

HungJobManualScenarios
======================

HungJobManualScenarios
----------------------

tags: 4584, Hung Job

Scenario:  Job runs normally and produces console output

Job has been triggerd within a stage within a pipeline and running

No warning message related to ‘Hanging Job’ should be shown

Scenario: Timeout value for a particular job has not been set

For a particular job timeout value has not been set 

The job is running and has no console output for the past 5 minutes 

Warning message stating “Job [jobname|link to job] is currently running but has not shown any activity for xx minutes. This job may be hung.” should be shown. The job should be timed out automatically after 60 minutes if there is no console output for 60 minutes. The on cancel task should be triggered.

Scenario

For a particular job timeout attribute has not been set during job configuration. Hence the job is should display default hung job behavior. Default config is 50 minutes

The job is running and has no console output for the past 50 minutes 

1. Warning message stating “Job [jobname|link to job] is currently running but has not shown any activity for xx minutes. This job may be hung.” should be shown before timeout 
2. The job should be purged automatically and purged reason should be recorded in the Job history
3. The on cancel task for the job should be triggered .
4. The job console log should have the following message. “Job was canceled automatically by Go because it was non-responsive for 50 minutes.” 

Scenario

A job has started running ( The job might take a while to run)

The user changes / removes the associated timeout period  

The job should run with setting which was already configured when the job started running.
All the future jobs created for new pipeline instances should pick up the newly configured values.

Scenario

A job has a defined timeout period of 0

The job runs for more than 5 minutes

The job should not be disabled. 
It should run as expected for completion

Scenario: RullOnAllAgents has few missing agents

* There is a pipeline which has a job that executes on all agents that could be matched
* Few of the agents are missing
* There is a global timeout period of 10 minutes that has been setted up globally

* The stage containing the particular job for a pipeline in context gets triggered

* The stage should get cancelled after 10 minutes automaticallty
* oncancel task if any should be executed



