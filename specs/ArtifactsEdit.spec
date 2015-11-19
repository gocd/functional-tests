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

ArtifactsEdit
=============

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "ArtifactsEdit" - setup

ArtifactsEdit
-------------

* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open artifacts

* Enter artifact detail "1" "first_source" "first_destination" "Test Artifact"
* Click save - Already on Artifacts Listing Page
* Verify saved successfully - Already on Artifacts Listing Page
* Enter artifact detail "2" "second_source" "second destination" "Build Artifact"
* Click save - Already on Artifacts Listing Page
* Verify saved successfully - Already on Artifacts Listing Page


* For pipeline "edit-pipeline"
* Verify "test" artifact with "@src:first_source, @dest:first_destination" exists for "defaultJob" in "defaultStage"
* Verify "artifact" artifact with "@src:second_source, @dest:second destination" exists for "defaultJob" in "defaultStage"





Teardown of contexts
* Capture go state "ArtifactsEdit" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


