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

PipelineUsingTimerWithOnlyOnChangesOption
=========================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-fast,downstream-pipeline" - setup
* With "1" live agents in directory "PipelinesUsingTimer" - setup
* Capture go state "PipelineUsingTimerWithOnlyOnChangesOption" - setup

PipelineUsingTimerWithOnlyOnChangesOption
-----------------------------------------

tags: 7079, timer, automate, onlyOnChanges


Changing pipeline config appropriately to use in this test
* Click on pipeline "downstream-pipeline" for editing

* Update auto scheduling to "false"
* Click save - Already On General Options Page
* Open material listing page

* Edit material "downstream-hg-material"

* Enter black list "" - Already on Mercurial Material Creation Popup
* Click save - Already on Mercurial Material Creation Popup

* On Pipeline Dashboard Page
* Looking at pipeline "basic-pipeline-fast"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Looking at pipeline "downstream-pipeline"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"

* On Pipeline Configuration wizard
* Click on pipeline "downstream-pipeline" for editing

* Verify only on changes checkbox is disabled
* Enter "0/30 * * * * ?" for cron time specifier
* Verify only on changes checkbox is enabled
* Click save - Already On General Options Page
* Open material listing page

* On Pipeline Dashboard Page
* Looking at pipeline "downstream-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Verify pipeline "downstream-pipeline" is triggered by "timer"

* On Pipeline Configuration wizard
* Click on pipeline "downstream-pipeline" for editing

* Select onlyOnChanges flag to trigger pipeline only on new material
* Click save - Already On General Options Page

* On Pipeline Dashboard Page
* Looking at pipeline "downstream-pipeline"
* Verify pipeline is at label "2" and does not get triggered

* With material named "downstream-hg-material" in pipeline "downstream-pipeline"
* Checkin file "new_file.txt" as user "cceuser4" with message "Comment abc"

* Looking at pipeline "downstream-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "3"
* Verify pipeline "downstream-pipeline" is triggered by "timer"
* Looking at pipeline "basic-pipeline-fast"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "2"
* Looking at pipeline "downstream-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "4"
* Verify pipeline "downstream-pipeline" is triggered by "timer"










Teardown of contexts
____________________
* Capture go state "PipelineUsingTimerWithOnlyOnChangesOption" - teardown
* With "1" live agents in directory "PipelinesUsingTimer" - teardown
* Using pipeline "basic-pipeline-fast,downstream-pipeline" - teardown
* Basic configuration - teardown


