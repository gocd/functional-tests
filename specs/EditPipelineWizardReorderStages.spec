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

EditPipelineWizardReorderStages
===============================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline-with-3-stages" - setup
* Capture go state "EditPipelineWizardReorderStages" - setup

EditPipelineWizardReorderStages
-------------------------------

tags: Clicky Admin, 4742

* Click on pipeline "edit-pipeline-with-3-stages" for editing

* Open stage listing page

* Verify the stages are "defaultStage,second,third"
* Verify cannot move "defaultStage" up
* Verify cannot move "third" down
* Move "second" up
* Verify the stages are "second,defaultStage,third"
* Move "defaultStage" down
* Verify the stages are "second,third,defaultStage"




Teardown of contexts
* Capture go state "EditPipelineWizardReorderStages" - teardown
* Using pipeline "edit-pipeline-with-3-stages" - teardown
* Basic configuration - teardown


