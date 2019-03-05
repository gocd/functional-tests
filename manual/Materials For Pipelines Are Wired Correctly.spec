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

Materials For Pipelines Are Wired Correctly
===========================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "junit-tests" - setup
* With "1" live agents in directory "StageDetailsShineIntegration" - setup
* Capture go state "Materials for pipelines are wired correctly" - setup

Materials For Pipelines Are Wired Correctly
-------------------------------------------

tags: materials, shine

Smoke test to check if the query to get the users on the changesets that triggered pipelines are returned correctly.

* With material named "junit-tests-material" in pipeline "junit-tests"
* Commit file "junit-failures/fail-a-fail-b/TEST-cruise.testing.JUnit.xml" to directory "junit-output" as user "ted"
* Commit file "junit-failures/fail-a-pass-b/TEST-cruise.testing.JUnit.xml" to directory "junit-output" as user "bill"

* Looking at pipeline "junit-tests"
* Trigger pipeline

* Fail "1" jobs that are waiting for file to exist

* On Pipeline Dashboard Page
* Looking at pipeline "junit-tests"
* Wait for first stage to fail with pipeline label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to tests tab
* Wait for shine feed to update
* Verify total runs "2" failures "1" errors "0"
* Verfy pipeline "1" modified by "bill"







Teardown of contexts
____________________
* Capture go state "Materials for pipelines are wired correctly" - teardown
* With "1" live agents in directory "StageDetailsShineIntegration" - teardown
* Using pipeline "junit-tests" - teardown
* Basic configuration - teardown


