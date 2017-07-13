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

ArtifactUploadFetch
===================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline-artifacts" - setup
* With "1" live agents in directory "Artifacts" - setup
* Capture go state "ArtifactUploadFetch" - setup

ArtifactUploadFetch
-------------------

tags: 2248, 3153, fetch artifact, svn support, diagnostics messages, publish artifact, scheduling, not-windows, automate, stage1, buildcommand

* Looking at pipeline "pipeline-artifacts"
* Trigger pipeline
* Wait for first stage to fail with pipeline label "1"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "Job1"

* Open console tab
* Verify in console that artifact "/subfolder/another.zip" was uploaded to "file-with-dot-in-subfolder"
* Verify in console that artifact "/something.jar" was uploaded to "jarfolder"
* Verify in console that artifact "/some.txt.thing" was uploaded to "zipfolder"
* Verify in console that artifact "/subfolder/another.zip" was uploaded to "all-in-subfolder"
* Verify in console that artifact "/subfolder/file" was uploaded to "all-in-subfolder"
* Verify in console that artifact "/something.zip" was uploaded to "allzip"
* Verify in console that artifact "/subfolder/another.zip" was uploaded to "allzip/subfolder"

* Looking at pipeline "pipeline-artifacts"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Trigger stage "second"

* On Pipeline Dashboard Page
* Wait for stage "second" status to be "Passed" with label "1"
* Navigate to stage "second" of run "1"

* Navigate to job "Job2"

* Open console tab
* Verify console contains "Fetching artifact [file-with-dot-in-subfolder] from"
* Verify console contains "Fetching artifact [jarfolder] from"
* Verify console contains "Fetching artifact [zipfolder] from"
* Verify console contains "Fetching artifact [all-in-subfolder] from"
* Verify console contains "Fetching artifact [allzip] from"




Teardown of contexts
____________________
* Capture go state "ArtifactUploadFetch" - teardown
* With "1" live agents in directory "Artifacts" - teardown
* Using pipeline "pipeline-artifacts" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


