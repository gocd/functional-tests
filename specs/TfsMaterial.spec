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

TfsMaterial
===========

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-tfs-pipeline" - setup
* With "2" live agents in directory "TfsMaterial" - setup
* Capture go state "TfsMaterial" - setup

TfsMaterial
-----------

tags: tfs, 6194

* Type new resource "tfs" for agent "2" and press return
* Type new resource "tfs" for agent "3" and press return

* Click on pipeline "basic-tfs-pipeline" for editing

* Open material listing page

* Edit material "tfs_mat"

* Make autoupdate to be "true" - Already on edit material popup
* Save changes - Already on edit material popup

* Open stage listing page - Already on material listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open job settings
* Enter "tfs" for resources - Already on Job edit page
* Click save - Already On Job Edit Page
* Verify that job saved sucessfully

* Looking at pipeline "basic-tfs-pipeline"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"

* With material named "tfs_mat" in pipeline "basic-tfs-pipeline"
* Modify file "IgnoredFile.ignore.txt" as "ignoreuser"
* Checkin as "ignoreuser" with message "should be ignored"

* Verify pipeline is at label "1" and does not get triggered

* With material named "tfs_mat" in pipeline "basic-tfs-pipeline"
* Modify file "Folder with space/tobeignored.txt" as "ignoreuser"
* Checkin as "ignoreuser" with message "should be ignored again"


* Verify pipeline is at label "1" and does not get triggered

* Click on pipeline "basic-tfs-pipeline" for editing

* Open material listing page

* Edit material "tfs_mat"

* Make autoupdate to be "false" - Already on edit material popup
* Save changes - Already on edit material popup

* With material named "tfs_mat" in pipeline "basic-tfs-pipeline"
* Modify file "HelloWorld.txt" as "luser"
* Checkin as "luser" with message "interesting comment by luser"

* Trigger pipelines "basic-tfs-pipeline" and wait for labels "2" to pass
* Open changes section for counter "2"

* Looking at material of type "Team Foundation Server" named "tfs_mat" for pipeline "basic-tfs-pipeline" with counter "2"
* Verify modification "0" has comment containing "interesting comment by luser"

* Looking at pipeline "basic-tfs-pipeline"
* Navigate to materials for "basic-tfs-pipeline" "2" "defaultStage" "1"

* Looking at material of type "Tfs" named "tfs_mat"
* Verify material has changed - Already On Build Cause Section
* Verify modification "0" is checked in by "CORPORATE\\cruise_builder" with comment "interesting comment by luser"

* Click on pipeline "basic-tfs-pipeline" for editing

* Open material listing page

* Edit material "tfs_mat"

* Enter destination directory "" - Already on Tfs Material Creation Popup
* Click save - Already on tfs material creation popup

* Verify that material saved successfully

* Looking at pipeline "basic-tfs-pipeline"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "3"

* Click on pipeline "basic-tfs-pipeline" for editing

* Open material listing page

* Edit material "tfs_mat"

* Enter destination directory "new_dest" - Already on Tfs Material Creation Popup
* Click save - Already on tfs material creation popup

* Verify that material saved successfully

* Looking at pipeline "basic-tfs-pipeline"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "4"

* Click on pipeline "basic-tfs-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Select clean working directory
* Click save - Already on Edit Stage Page
* Verify that stage saved successfully - Already on edit stage page

* Looking at pipeline "basic-tfs-pipeline"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "5"
* Navigate to stage "defaultStage" of run "5" having counter "1"
* Navigate to job "defaultJob-runOnAll-1"
* Open console tab
* Verify console contains "Cleaning working directory"




Teardown of contexts
* Capture go state "TfsMaterial" - teardown
* With "2" live agents in directory "TfsMaterial" - teardown
* Using pipeline "basic-tfs-pipeline" - teardown
* Basic configuration - teardown


