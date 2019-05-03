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

ArtifactMD5VerificationSkip
===========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "artifact-md5-skip" - setup
* Capture go state "ArtifactMD5VerificationSkip" - setup
* With "1" live agents in directory "ArtifactsMD5Skip" - setup

ArtifactMD5VerificationSkip
---------------------------

tags: #2408, Agent, Artifacts, MD5

* Trigger pipelines "artifact-md5-skip" and wait for labels "1" to pass

* For pipeline "artifact-md5-skip" label "1" stage "defaultStage" counter "1" job "defaultJob"
* Create artifact "new_artifact.txt"
* Verify return code is "201" - Using Artifact Api

* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to jobs tab
* Navigate to job "defaultJob"

* Verify artifacts tab contains file "new_artifact.txt"

* On Pipeline Dashboard Page
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Trigger stage "secondStage"

* On Pipeline Dashboard Page
* Wait for stage "secondStage" status to be "Passed" with label "1"

* On Pipeline Dashboard Page
* Navigate to stage "secondStage" of run "1" having counter "1"

* Go to jobs tab
* Navigate to job "defaultJob"

* Open console tab
* Verify console contains "[WARN] The md5checksum property file was not found on the server. Hence, Go can not verify the integrity of the artifacts."
* Verify console contains "new_artifact.txt] without verifying the integrity of its contents"
* Verify console does not contain "new_artifact.txt] after verifying the integrity of its contents."


Teardown of contexts

____________________
* With "1" live agents in directory "ArtifactsMD5Skip" - teardown
* Capture go state "ArtifactMD5VerificationSkip" - teardown
* Using pipeline "artifact-md5-skip" - teardown
* Basic configuration - teardown


