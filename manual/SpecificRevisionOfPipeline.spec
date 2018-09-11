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

SpecificRevisionOfPipeline
==========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-fast, manual-downstream-pipeline" - setup
* With "2" live agents in directory "specific_revision_deployment" - setup
* Capture go state "SpecificRevisionOfPipeline" - setup

SpecificRevisionOfPipeline
--------------------------

tags: 3388, restful api, 3407, automate, 3453

* Adding pipeline "manual-downstream-pipeline" to "uat" environment
* Add environment "uat" to any "1" Idle agents - Using Agents API

This sets up 2 pipeline instances of each pipeline
* Trigger pipeline "basic-pipeline-fast"
* Wait for first stage to pass with pipeline label "1"

* On Pipeline Dashboard Page
* Looking at pipeline "manual-downstream-pipeline"
* Wait for first stage to pass with pipeline label "1"



Operate page assertions are moved to a manual scenario as operate page has been removed as a part of #3494, which does deploy latest button for pipelines in environment
REFER -> SpecificRevisionOfPipelineWithOperateScreenCheck.scn





Teardown of contexts
____________________
* Capture go state "SpecificRevisionOfPipeline" - teardown
* With "2" live agents in directory "specific_revision_deployment" - teardown
* Using pipeline "basic-pipeline-fast, manual-downstream-pipeline" - teardown
* Basic configuration - teardown


