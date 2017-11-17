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

TfsMaterialSchedulingAndMaterialModification
============================================

Setup of contexts
* Tfs configuration - setup
* Using pipeline "tfs-pipeline-with-multiple-stages" - setup
* With "1" live agents in directory "TfsMaterialSchedulingAndMaterialModification" - setup
* Capture go state "TfsMaterialSchedulingAndMaterialModification" - setup

TfsMaterialSchedulingAndMaterialModification
--------------------------------------------

tags: tfs, regression, internal

* With material named "tfs_mat" in pipeline "tfs-pipeline-with-multiple-stages"
* Modify file "AnotherHelloWorld.txt" as "luser"
* Checkin as "luser" with message "interesting comment by luser"

* Looking at pipeline "tfs-pipeline-with-multiple-stages"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "stage1" of run "1"

* Trigger stage "stage2"

* Click on pipeline "tfs-pipeline-with-multiple-stages" for editing

* Open material listing page

* Edit material "tfs_mat"

* Enter url "integration_tests" - Already on tfs material creation popup
* Enter project path as "$/for_tests"
* Click save - Already on tfs material creation popup

now trigger pipeline again with new material change
* On Pipeline Dashboard Page
* Looking at pipeline "tfs-pipeline-with-multiple-stages"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "2"

verify first instance built with older material
* Navigate to materials for "tfs-pipeline-with-multiple-stages" "1" "stage1" "1"

* Looking at material of type "Tfs" named "tfs_mat"
* Verify material has changed - Already On Build Cause Section
* Verify tfs modification "0" is checked in by authorized user with comment "interesting comment by luser"

verify second instance built with newer material
* Navigate to materials for "tfs-pipeline-with-multiple-stages" "2" "stage1" "1"

* Looking at material of type "Tfs" named "tfs_mat"
* Verify material has changed - Already On Build Cause Section
* Verify modification "0" is checked in by "Modified by: go.tfs.user@gmail.com" with comment "Comment: Updated SecondAdd.txt"




Teardown of contexts
____________________
* Capture go state "TfsMaterialSchedulingAndMaterialModification" - teardown
* With "1" live agents in directory "TfsMaterialSchedulingAndMaterialModification" - teardown
* Using pipeline "tfs-pipeline-with-multiple-stages" - teardown
* Tfs configuration - teardown


