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

ArtifactMD5Verification
=======================

Setup of contexts
* Basic configuration - setup
* Using pipeline "artifact-md5-up" - setup
* Capture go state "ArtifactMD5Verification" - setup
* With "1" live agents in directory "ArtifactsMD5" - setup

ArtifactMD5Verification
-----------------------

tags: #2408, Agent, Artifacts, MD5, stage1

* With material named "junit-failures-material" in pipeline "artifact-md5-up"
* Checkin file "abc.txt" as user "user" with message "Comment user"
* Commit file "new_artifact.txt" to directory "baz"
* Commit file "another_artifact.txt" to directory "baz"

* Trigger pipelines "artifact-md5-up" and wait for labels "1" to pass
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to jobs tab
* Navigate to job "defaultJob"

* Open console tab
* Verify in console that artifact "abc.txt" was uploaded to "[defaultRoot]"
* Verify artifacts tab contains file "abc.txt"
* Verify in console that artifact "baz" was uploaded to "[defaultRoot]"
* Verify artifacts tab contains file "new_artifact.txt" in dir "baz"
* Verify artifacts tab contains file "another_artifact.txt"
* Verify artifacts tab contains file "md5.checksum" in dir "cruise-output"

* Navigate to stage "defaultStage" of run "1" having counter "1"

* Trigger stage "secondStage"

* On Pipeline Dashboard Page
* Verify stage "2" is "Passed" on pipeline with label "1"

* Navigate to stage "secondStage" of run "1" having counter "1"

* Go to jobs tab
* Navigate to job "defaultJob"

* Open console tab
* Verify console contains "abc.txt] after verifying the integrity of its contents."
* Verify console contains "foo/bar] after verifying the integrity of its contents."
* Verify console does not contain "Go could not verify the integrity of its contents."




Teardown of contexts
____________________
* With "1" live agents in directory "ArtifactsMD5" - teardown
* Capture go state "ArtifactMD5Verification" - teardown
* Using pipeline "artifact-md5-up" - teardown
* Basic configuration - teardown


