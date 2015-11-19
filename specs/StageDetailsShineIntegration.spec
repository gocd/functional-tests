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

StageDetailsShineIntegration
============================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "junit-failures, shine-message" - setup
* With "1" live agents in directory "StageDetailsShineIntegration" - setup
* Capture go state "StageDetailsShineIntegration" - setup

StageDetailsShineIntegration
----------------------------

tags: 3609, stage-details, automate, shine

* With material named "junit-failures-material" in pipeline "junit-failures"
* Commit file "junit-failures/fail-a-pass-b/TEST-cruise.testing.JUnit.xml" to directory "junit-output"
* Remember current version as "fail-a-pass-b"

* Looking at pipeline "junit-failures"
* Trigger pipeline
* Wait for stage "defaultStage" status to be "Failed" with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to tests tab
* Wait for shine feed to update
* Verify total runs "2" failures "1" errors "0"
* Verify pipeline "1" has "1" failures "0" errors
* Verfy pipeline "1" modified by "twist"
* Verify pipeline "1" having label "1" has version "fail-a-pass-b"
* Verify failure details for job "defaultJob" suite "cruise.testing.JUnit" test "a" contains "Just failed" with stacktrace "junit.framework.AssertionFailedError: Just failed,at cruise.testing.JUnit.a(JUnit.java:10)"

* Commit file "junit-failures/fail-a-fail-b/TEST-cruise.testing.JUnit.xml" to directory "junit-output"
* Checkin file "file1" as user "user1" with message "Added file1"
* Remember current version as "fail-a-fail-b"

port needs to be correct, we do not discover our port while honoring hostname used by the requester
* Adding "http://foo.bar:8253" as site uRL
* Adding "https://bar.baz:8254" as secure site uRL

* On Pipeline Dashboard Page
* Looking at pipeline "junit-failures"
* Trigger pipeline
* Wait for stage "defaultStage" status to be "Failed" with label "2"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Go to tests tab
* Wait for shine feed to update
* Verify total runs "2" failures "2" errors "0"
* Verify pipeline "2" has "1" failures "0" errors
* Verify pipeline "2" has test "b" with "Failure" type
* Verify pipeline "2" having label "2" has version "fail-a-fail-b"
* Verify pipeline "1" has "1" failures "0" errors
* Verify pipeline "1" has test "a" with "Failure" type
* Verify pipeline "1" having label "1" has version "fail-a-pass-b"
* Verfy pipeline "1" modified by "twist"
* Verfy pipeline "2" modified by "user1, twist"
* Verify failure details for job "defaultJob" suite "cruise.testing.JUnit" test "b" contains "Just failed" with stacktrace "junit.framework.AssertionFailedError: Just failed,at cruise.testing.JUnit.b(JUnit.java:20)"
* Verify failure details for job "defaultJob" suite "cruise.testing.JUnit" test "a" contains "Just failed" with stacktrace "junit.framework.AssertionFailedError: Just failed,at cruise.testing.JUnit.a(JUnit.java:10)"
* Verify job "junit-failures" "2" "defaultStage" "1" "defaultJob" links to the job detail page

* On Pipeline Dashboard Page
* Looking at pipeline "junit-failures"
* Navigate to failed build history for "junit-failures" "2" "defaultStage" "1"

* For pipeline "junit-failures" - Using pipeline api
* Using remembered revision "fail-a-pass-b" for material "junit-failures-material"
* Schedule should return code "202"

* Wait for pipeline with label "3" to appear
* Verify total runs "3" failures "2" errors "0"
* Verify pipeline "3" having label "3" has version "fail-a-pass-b"

* On Pipeline Dashboard Page
* Looking at pipeline "shine-message"
* Trigger pipeline
* Wait for stage "defaultStage" status to be "Passed" with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to tests tab
* Verify message "There are no tests configured in this stage." shows up - Already On Stage Detail Page

* Adding test artifact with source "junit-output" and destination "junit-output" to job "shine-message-job"

* With material named "shine-message-material" in pipeline "shine-message"
* Commit file "junit-failures/pass-a-pass-b/TEST-cruise.testing.JUnit.xml" to directory "junit-output"

* On Pipeline Dashboard Page
* Trigger pipeline "shine-message"
* Wait for stage "defaultStage" status to be "Passed" with label "2"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Go to tests tab
* Wait for shine feed to update
* Verify message "The stage passed" shows up - Already On Stage Detail Page
* Verify total runs "2" failures "0" errors "0"




Teardown of contexts
* Capture go state "StageDetailsShineIntegration" - teardown
* With "1" live agents in directory "StageDetailsShineIntegration" - teardown
* Using pipeline "junit-failures, shine-message" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


