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

ComparePipelineTimelineView
===========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "ComparePipelineTimelineView" - setup

ComparePipelineTimelineView
---------------------------

tags: #4568, compare_pipeline

* Trigger and cancel pipeline "edit-pipeline" "12" times
* Navigate to stage "defaultStage" of run "12" having counter "1"

* Click compare link for pipeline counter "12"

Basic functionality
* Verify that "to" textbox is populated with "12"
* Verify that "from" textbox is populated with "12"
* Click on "from" textbox
* Verify "from" sugggestion box has "Search for a pipeline instance by label, commiter, date, etc. or Browse the timeline"
* Click on "to" textbox
* Verify "to" sugggestion box has "Search for a pipeline instance by label, commiter, date, etc. or Browse the timeline"
* Click to browse the timeline on "to" suggestion box

* Verify selected pipeline is "12"
* Verify that page has pipeline range "12" to "3"
* Click on page "2"
* Verify that page has pipeline range "2" to "1"
* Choose pipeline with label "1"
* Confirm selection

* Verify that "to" textbox is populated with "1"
* Verify that "from" textbox is populated with "12"
* Click on "from" textbox
* Click to browse the timeline on "from" suggestion box

* Verify selected pipeline is "12"
* Choose pipeline with label "3"
* Confirm selection

* Verify that "to" textbox is populated with "1"
* Verify that "from" textbox is populated with "3"




Teardown of contexts
* Capture go state "ComparePipelineTimelineView" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


