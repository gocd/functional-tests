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

TfsPipelineLabelIncludesMaterialRevision
========================================

Setup of contexts
* Tfs configuration - setup
* Using pipeline "tfs_artifact_in_dest" - setup
* Capture go state "TfsPipelineLabelIncludesMaterialRevision" - setup

TfsPipelineLabelIncludesMaterialRevision
----------------------------------------

tags: #6193, Pipeline label, tfs

* Click on pipeline "tfs_artifact_in_dest" for editing

* Enter "${COUNT}-${tfs_mat}" for label template
* Click save - Already On General Options Page
* Verify saved successfully - Already On General Options Page

* Looking at pipeline "tfs_artifact_in_dest"
* Trigger pipeline
* Wait for current status of stage "defaultStage" to be "Building"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to jobs tab

* Verify job "defaultJob" has state "Scheduled" and result "Active"

* Navigate to materials for "tfs_artifact_in_dest" "1" "defaultStage" "1"

* Looking at material of type "Tfs" named "tfs_mat"
* Save material revision into variable "material_revision_1"

* Verify current pipeline has label with counter "1" concatenated with value in store with key "material_revision_1"





Teardown of contexts
____________________
* Capture go state "TfsPipelineLabelIncludesMaterialRevision" - teardown
* Using pipeline "tfs_artifact_in_dest" - teardown
* Tfs configuration - teardown


