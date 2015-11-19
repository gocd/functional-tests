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

PipelineGroupAdminsWithStageSecurity
====================================

Setup of contexts
* Group admin security configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline-with-group-and-stage-security" - setup
* With "1" live agents in directory "not-used" - setup
* Capture go state "PipelineGroupAdminsWithStageSecurity" - setup

PipelineGroupAdminsWithStageSecurity
------------------------------------

tags: group admin, 4138

Stage first has Operate permission enabled only for user operate. User pavan is the pipeline Group Admin. He does not have operate permission on the stage defined explicitly.
He should still be able to operate on the stage.

* Logout - On Any Page

* Login as "pavan"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-group-and-stage-security"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"
* Stop "1" jobs waiting for file to exist - On Pipeline Dashboard Page
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "first" of run "1"

* Rerun stage "first"
* Verify stage "first" has action "Cancel"

* On Pipeline Dashboard Page
* Stop "1" jobs waiting for file to exist - On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-group-and-stage-security"
* Verify stage "1" is "Passed" on pipeline with label "1"

* Logout - On Any Page

* Login as "operate"

* For pipeline "pipeline-with-group-and-stage-security" - Using pipeline api
* Schedule should return code "202"




Teardown of contexts
* Capture go state "PipelineGroupAdminsWithStageSecurity" - teardown
* With "1" live agents in directory "not-used" - teardown
* Using pipeline "pipeline-with-group-and-stage-security" - teardown
* Login as "admin" - teardown
* Group admin security configuration - teardown


