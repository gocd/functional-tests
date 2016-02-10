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

JobDetailsBreadCrumbs
=====================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-fast" - setup
* Capture go state "JobDetailsBreadCrumbs" - setup

JobDetailsBreadCrumbs
---------------------

tags: 4147, breadcrumbs, job-detail

* Looking at pipeline "basic-pipeline-fast"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "defaultJob"

* Verify breadcrumb contains pipeline "basic-pipeline-fast"
* Verify breadcrumb contains pipeline label "1"
* Verify breadcrumb contains stage run "defaultStage / 1"
* Click on bread crumb "defaultStage / 1"

* Verify that "${runtime_name:basic-pipeline-fast}/1/defaultStage/1" stage is displayed
* Cancel "defaultStage" - Already On Stage Detail Page
* Rerun stage "defaultStage"
* Click on stage bar run "2" of "2"
* Navigate to job "defaultJob"

* Verify breadcrumb contains pipeline "basic-pipeline-fast"
* Verify breadcrumb contains pipeline label "1"
* Verify breadcrumb contains stage run "defaultStage / 2"
* Verify breadcrumb contains link to value stream map on pipeline label "1" for pipeline "basic-pipeline-fast" for counter "1"
* Click on bread crumb "defaultStage / 2"

* Verify that "${runtime_name:basic-pipeline-fast}/1/defaultStage/2" stage is displayed
* Cancel "defaultStage" - Already On Stage Detail Page
* Go to jobs tab
* Navigate to job "defaultJob"

* Click on pipeline bread crumb "basic-pipeline-fast"

* Verify on pipeline history page for "basic-pipeline-fast"





Teardown of contexts
____________________
* Capture go state "JobDetailsBreadCrumbs" - teardown
* Using pipeline "basic-pipeline-fast" - teardown
* Basic configuration - teardown


