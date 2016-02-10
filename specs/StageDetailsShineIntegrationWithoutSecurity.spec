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

StageDetailsShineIntegrationWithoutSecurity
===========================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "junit-failures, shine-message" - setup
* With "1" live agents in directory "StageDetailsShineIntegration" - setup
* Capture go state "StageDetailsShineIntegrationWithoutSecurity" - setup

StageDetailsShineIntegrationWithoutSecurity
-------------------------------------------

tags: 3609, stage-details, automate, shine, failing

* With material named "junit-failures-material" in pipeline "junit-failures"
* Commit file "junit-failures/fail-a-pass-b/TEST-cruise.testing.JUnit.xml" to directory "junit-output"

* Looking at pipeline "junit-failures"
* Trigger pipeline
* Wait for stage "defaultStage" status to be "Failed" with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to tests tab
* Wait for shine feed to update
* Verify total runs "2" failures "1" errors "0"
* Verify pipeline "1" has "1" failures "0" errors
* Verfy pipeline "1" modified by "twist"





Teardown of contexts
____________________
* Capture go state "StageDetailsShineIntegrationWithoutSecurity" - teardown
* With "1" live agents in directory "StageDetailsShineIntegration" - teardown
* Using pipeline "junit-failures, shine-message" - teardown
* Basic configuration - teardown


