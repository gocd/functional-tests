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

StageWithCleanWorkingDIrAttribute
=================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-clean-working-dir" - setup
* With "1" live agents in directory "CleanDir" - setup
* Capture go state "StageWithCleanWorkingDIrAttribute" - setup

StageWithCleanWorkingDIrAttribute
---------------------------------

tags: 4232

* With material named "basic-hg-material" in pipeline "pipeline-clean-working-dir"
* Checkin file "new_file.txt" as user "gouser" with message "Comment abc"

* Looking at pipeline "pipeline-clean-working-dir"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "setup" of run "1" having counter "1"


* Trigger stage "first"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-clean-working-dir"
* Verify stage "2" is "Passed" on pipeline with label "1"
* Navigate to stage "first" of run "1" having counter "1"
* Navigate to job "firstJob"
* Open console tab
* Verify console contains "Start to prepare" and not "Cleaning working directory"
* Verify console contains "abc.txt"
* Verify console contains "xyz.txt"
* Verify console contains "bar"
* Verify console contains "new_file.txt"
* Click on bread crumb "first / 1"


* Trigger stage "second"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-clean-working-dir"
* Verify stage "3" is "Passed" on pipeline with label "1"
* Navigate to stage "second" of run "1" having counter "1"
* Navigate to job "secondJob"
* Open console tab
* Verify console contains "Cleaning working directory"
* Verify console does not contain "abc.txt"
* Verify console does not contain "xyz.txt"
* Verify console does not contain "bar"
* Verify console contains "new_file.txt"






Teardown of contexts
____________________
* Capture go state "StageWithCleanWorkingDIrAttribute" - teardown
* With "1" live agents in directory "CleanDir" - teardown
* Using pipeline "pipeline-clean-working-dir" - teardown
* Basic configuration - teardown


