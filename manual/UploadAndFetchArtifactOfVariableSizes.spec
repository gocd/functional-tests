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

UploadAndFetchArtifactOfVariableSizes
=====================================

Setup of contexts 
* Basic configuration - setup
* Using pipeline "pipeline_uploading_large_artifact, pipeline_downloading_large_artifact" - setup
* With "1" live agents in directory "UploadAndFetchArtifactOfVariableSizes" - setup
* Capture go state "UploadAndFetchArtifactOfVariableSizes" - setup

UploadAndFetchArtifactOfVariableSizes
-------------------------------------

tags: #6350, Fetch Artifact, jre7

* looking at pipeline "pipeline_uploading_large_artifact"
* trigger pipeline
* verify stage "1" is "Passed" on pipeline with label "1"
* looking at pipeline "pipeline_downloading_large_artifact"
* trigger pipeline
* verify stage "1" is "Passed" on pipeline with label "1"




Teardown of contexts 
* Capture go state "UploadAndFetchArtifactOfVariableSizes" - teardown
* With "1" live agents in directory "UploadAndFetchArtifactOfVariableSizes" - teardown
* Using pipeline "pipeline_uploading_large_artifact, pipeline_downloading_large_artifact" - teardown
* Basic configuration - teardown


