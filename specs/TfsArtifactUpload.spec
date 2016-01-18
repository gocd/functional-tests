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

TfsArtifactUpload
=================

Setup of contexts
* Tfs configuration - setup
* Using pipeline "tfs_artifact_in_dest,tfs_artifact_in_child_directory,tfs_generated_file_gets_deleted" - setup
* With "1" live agents in directory "TfsArtifactUpload" - setup
* Capture go state "TfsArtifactUpload" - setup

TfsArtifactUpload
-----------------

tags: tfs, regression, internal

Rajeshvaran's Regression Suite - 7.1.1 - artifacts are generated in the working folder of TFS on agent
* Looking at pipeline "tfs_artifact_in_dest"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"

Rajeshvaran's Regression Suite - 7.1.2 - artifacts are generated in the working folder and clean working directory is used to cleanup the entire stuff
* Click on pipeline "tfs_artifact_in_dest" for editing

* Open stage listing page

* Open stage "defaultStage"

* Select clean working directory
* Click save - Already on Edit Stage Page

* On Pipeline Dashboard Page
* Looking at pipeline "tfs_artifact_in_dest"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "2"

Rajeshvaran's Regression Suite - 7.1.3 - artifacts are generated within a self contained folder within the working folder of TFS
* Looking at pipeline "tfs_artifact_in_child_directory"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"

Rajeshvaran's Regression Suite - 7.1.4 - deletion of generated artifacts before upgrade
* Looking at pipeline "tfs_generated_file_gets_deleted"
* Trigger pipeline
* Verify stage "1" is "Failed" on pipeline with label "1"




Teardown of contexts
* Capture go state "TfsArtifactUpload" - teardown
* With "1" live agents in directory "TfsArtifactUpload" - teardown
* Using pipeline "tfs_artifact_in_dest,tfs_artifact_in_child_directory,tfs_generated_file_gets_deleted" - teardown
* Tfs configuration - teardown


