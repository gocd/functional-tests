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

EditStageAddNewJobFlow
======================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "EditStageAddNewJobFlow" - setup

EditStageAddNewJobFlow
----------------------

tags: Clicky Admin, 4699, smoke, manual

* Click on pipeline "edit-pipeline" for editing

* Open stage "defaultStage" - Using Pipeline Navigation

* Open jobs

* Open add new job to this stage page

* Select as task type "More..."
* Click save - Already on Add Job Popup
* Verify "Name is a required field" message exists - Already on Add Job Popup
* Verify "Command cannot be empty" message exists - Already on Add Job Popup
* Verify "Save failed, see errors below" message exists - Already on Add Job Popup
* Enter "second-job" for job name
* Enter "windows" for resources
* Enter "ls" as command
* Add job

* Verify that job saved sucessfully - Already on Job listing Page
* Verify job "second-job" with resources as "windows" and run on all as "No"
* Open job "second-job"

* Open job settings
* Enter "windows,win-xp,linux,win7" for resources - Already on Job edit page
* Check run on all
* Click save - Already On Job Edit Page
* Verify that job saved sucessfully

* Open stage "defaultStage" - Using Pipeline Navigation

* Open jobs

Testing autocomplete on Resources

* Open add new job to this stage page

* Enter "third-Job" for job name
* Select as task type "More..."
* Enter "ls" as command
* Enter "win" for resources
* Verify dropdown contains "windows,win-xp,win7"
* Select "windows" from the dropdown
* Add job

* Verify job "second-job" with resources as "windows, win-xp, linux, win7" and run on all as "Yes"
* Verify job "third-Job" with resources as "windows" and run on all as "No"

* Open stage "defaultStage" - Using Pipeline Navigation

* Open jobs

* Delete job "defaultJob"
* Verify "second-job" job is present
* Verify job "defaultJob" is not present




Teardown of contexts
____________________
* Capture go state "EditStageAddNewJobFlow" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


