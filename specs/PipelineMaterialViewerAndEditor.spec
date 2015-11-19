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

PipelineMaterialViewerAndEditor
===============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "up, edit-pipeline" - setup
* Capture go state "PipelineMaterialViewerAndEditor" - setup

PipelineMaterialViewerAndEditor
-------------------------------

tags: Clicky Admin, 4598

* Click on pipeline "edit-pipeline" for editing

* Open material listing page

* Open new pipeline material creation popup

* Enter material name "pipeline_material" - Already on pipeline material creation popup
* Enter to get suggestion "u"
* Verify suggestion "${runtime_name:up}" "defaultStage" shows up
* Select "${runtime_name:up}" "defaultStage"
* Click save - Already on pipeline material creation popup

* Verify that material saved successfully
* Verify that "pipeline" with name "pipeline_material" is added with attributes "materialName>pipeline_material,stageName>defaultStage"
* Edit material "pipeline_material"

* Enter material name "pipeline_material" - Already on pipeline material creation popup
* Verify pipeline and stage are "${runtime_name:up}" "defaultStage"
* Enter to get suggestion "anot"
* Verify suggestion "${runtime_name:up}" "anotherStage" shows up
* Select "${runtime_name:up}" "anotherStage"
* Click save - Already on pipeline material creation popup

* Verify that material saved successfully
* Verify that "pipeline" with name "pipeline_material" is added with attributes "materialName>pipeline_material,stageName>anotherStage"





Teardown of contexts
* Capture go state "PipelineMaterialViewerAndEditor" - teardown
* Using pipeline "up, edit-pipeline" - teardown
* Basic configuration - teardown


