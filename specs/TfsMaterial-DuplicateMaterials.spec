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

TfsMaterial-DuplicateMaterials
==============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "tfs-pipeline-with-duplicate-materials" - setup
* With "1" live agents in directory "TfsMaterial-DuplicateMaterials" - setup
* Capture go state "TfsMaterial-DuplicateMaterials" - setup

TfsMaterial-DuplicateMaterials
------------------------------

tags: #5799, 5799, tfs

* Looking at pipeline "tfs-pipeline-with-duplicate-materials"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"

* Click on pipeline "tfs-pipeline-with-duplicate-materials" for editing

* Open material listing page

* Edit material "tfs_mat_1"

* Enter url "integration_tests" - Already on tfs material creation popup
* Enter project path as "$/for_tests"
* Click save - Already on tfs material creation popup

* Looking at pipeline "tfs-pipeline-with-duplicate-materials"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "2"




Teardown of contexts
* Capture go state "TfsMaterial-DuplicateMaterials" - teardown
* With "1" live agents in directory "TfsMaterial-DuplicateMaterials" - teardown
* Using pipeline "tfs-pipeline-with-duplicate-materials" - teardown
* Basic configuration - teardown


