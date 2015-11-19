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

JobsOnMatchingAgents-Manual
===========================

Setup of contexts 
* Capture go state "JobsOnMatchingAgents-Manual" - setup

JobsOnMatchingAgents-Manual
---------------------------

tags: 3102, job template, manual, 3307

* verify a stage that contains jobs and job run-ons run both these
* verify that the configuration file does not convert job run-ons into jobs to show in the config file


* verify when a job is run on all agents it picks agents which has resources in the configuration
* verify when a job is run on all agents it picks only agents from the same environmentin which the pipeline exist containing the job with run-on
* verify that if there are 5 matching agents there are 5 jobs created with different integers in job name and is run on that agent only




Teardown of contexts 
* Capture go state "JobsOnMatchingAgents-Manual" - teardown


