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

TimerWithPipelineDependencies
=============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-fast, downstream-pipeline" - setup
* With "1" live agents in directory "TimerWithPipelineDependencies" - setup
* Capture go state "TimerWithPipelineDependencies" - setup

TimerWithPipelineDependencies
-----------------------------

tags: 2272, timer, automate, long_running


* Setting first stage of "downstream-pipeline" to auto approval

verify that if uptream pipeline has  a timer it triggers the downstream pipeline when upstream finishes

Upstream pipeline completed
* Looking at pipeline "basic-pipeline-fast"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Looking at pipeline "downstream-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"

* Looking at pipeline "basic-pipeline-fast" - Configure Pipeline
* Configure timer to trigger pipeline "2" minutes from now

* On Pipeline Dashboard Page
* Verify stage "1" is "Passed" on pipeline with label "2"

* Looking at pipeline "basic-pipeline-fast" - Configure Pipeline
* Remove timer

* On Pipeline Dashboard Page
* Looking at pipeline "downstream-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"

* Looking at material of type "Pipeline" named "upstream-material" for pipeline "downstream-pipeline" with counter "2"
* Verify modification "0" has revision "${runtime_name:basic-pipeline-fast}/2/defaultStage/1"

verify that if downstream pipeline has a timer it get triggered seperately

* Looking at pipeline "downstream-pipeline" - Configure Pipeline
* Configure timer to trigger pipeline "2" minutes from now

* On Pipeline Dashboard Page
* Looking at pipeline "downstream-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "3"
* Verify pipeline "downstream-pipeline" is triggered by "timer"




Teardown of contexts
____________________
* Capture go state "TimerWithPipelineDependencies" - teardown
* With "1" live agents in directory "TimerWithPipelineDependencies" - teardown
* Using pipeline "basic-pipeline-fast, downstream-pipeline" - teardown
* Basic configuration - teardown


