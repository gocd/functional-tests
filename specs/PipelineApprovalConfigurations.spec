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

PipelineApprovalConfigurations
==============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline, autoFirst" - setup
* Capture go state "PipelineApprovalConfigurations" - setup

PipelineApprovalConfigurations
------------------------------

tags: 7078

Automatic pipeline scheduling for general pipelines
* Click on pipeline "edit-pipeline" for editing

* Verify auto scheduling is set to "false"
* Update auto scheduling to "true"
* Click save - Already On General Options Page
* Verify saved successfully - Already On General Options Page
* Open stage listing page

* Open stage "defaultStage"

* Verify aproval type is set to "auto"
* Change approval type to "manual"
* Click save - Already on Edit Stage Page
* Verify that stage saved successfully - Already on edit stage page

* On Pipeline Configuration wizard
* Click on pipeline "edit-pipeline" for editing

* Verify auto scheduling is set to "false"

Automatic pipeline scheduling for pipeline using template

* Click on pipeline "autoFirst" for editing

* Verify auto scheduling is set to "true"
* Verify auto scheduling checkbox is disabled

* On Admin page
* Open "Templates" tab

* Edit template "autoStagePipelineTemplate"

* Open stages tab

* Open stage "defaultStage"

* Verify aproval type is set to "auto"
* Change approval type to "manual"
* Click save - Already on Edit Stage Page
* Verify that stage saved successfully - Already on edit stage page

* On Pipeline Configuration wizard
* Click on pipeline "autoFirst" for editing

* Verify auto scheduling is set to "false"
* Verify auto scheduling checkbox is disabled




Teardown of contexts
* Capture go state "PipelineApprovalConfigurations" - teardown
* Using pipeline "edit-pipeline, autoFirst" - teardown
* Basic configuration - teardown


