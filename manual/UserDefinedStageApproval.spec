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

UserDefinedStageApproval
========================

Setup of contexts 
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* With "1" live agents in directory "UserDefinedStageApproval" - setup
* Capture go state "UserDefinedStageApproval" - setup

UserDefinedStageApproval
------------------------

tags: 5760, stageapproval


Add new stage to pipeline with user defined approval

* click on pipeline "edit-pipeline" for editing

* Open parameters page

* enter parameter "1" name "approval_param" and value "manual"
* click save
* Open stage listing page

* open new add stage details page

* enter for stage name "newStage"
* select user defined parameter for stage trigger
* enter user defined approval parameter as "{foo}"
* enter for job name "new_job"
* select "More..." as task type
* enter as command "echo"
* enter as arguments "Hello"
* add stage
* verify "customType" has error message "Parameter 'foo' is not defined. All pipelines using this parameter directly or via a template must define it."
* enter user defined approval parameter as "{approval_param}"
* add stage

Edit existing stage in pipeline to change approval to use user-defined-param

* open stage "defaultStage"

* select stage type as "customType"
* enter user defined approval parameter as "foo"
* click save
* verify error message "You have defined approval type as 'foo'. Approval can only be of the type 'manual', 'success' or a user defined parameter." is shown
* enter user defined approval parameter as "{approval_param}"
* click save

* trigger pipelines "edit-pipeline" and wait for labels "1" to pass
* navigate to stage "defaultStage" of run "1" having counter "1"

* trigger stage "newStage"

* verify stage "2" is "Passed" on pipeline with label "1"


Making edit-pipeline use a template which uses user defined approval

* open "Templates" tab

* edit template "autoStagePipelineTemplate"

* open stages tab

* open new add stage details page

* enter for stage name "newStage"
* select user defined parameter for stage trigger
* enter user defined approval parameter as "template_stage_approval"
* enter for job name "new_job"
* select "More..." as task type
* enter as command "echo"
* enter as arguments "Hello"
* add stage
* verify "customType" has error message "You have defined approval type as 'template_stage_approval'. Approval can only be of the type 'manual', 'success' or a parameter."
* enter user defined approval parameter as "{template_stage_approval}"
* add stage

* verify "defaultStage" stage is present
* open stage "defaultStage"

* select stage type as "customType"
* enter user defined approval parameter as "foo"
* click save
* verify error message "You have defined approval type as 'foo'. Approval can only be of the type 'manual', 'success' or a parameter." is shown
* enter user defined approval parameter as "{template_stage_approval}"
* click save

* click on pipeline "edit-pipeline" for editing

* Open stage listing page

* switch to use template
* select "autoStagePipelineTemplate" from templates dropdown
* click save
* click "Proceed" on confirmation
* verify global errors contain "Parameter 'template_stage_approval' is not defined. All pipelines using this parameter directly or via a template must define it."
* click reset
* open parameters page

* enter parameter "2" name "template_stage_approval" and value "manual"
* click save
* open stage listing page

* switch to use template
* select "autoStagePipelineTemplate" from templates dropdown
* click save
* click "Proceed" on confirmation
* verify that stage saved successfully
* verify that table "stages_of_templated_pipeline" has headers "Stage from autoStagePipelineTemplate,Trigger Type,Parameter"
* verify that stage "defaultStage" has parameter "{template_stage_approval}" with trigger_type "Manual"
* verify that stage "newStage" has parameter "{template_stage_approval}" with trigger_type "Manual"

* trigger pipelines "edit-pipeline" and wait for labels "2" to pass
* navigate to stage "defaultStage" of run "2" having counter "1"

* trigger stage "newStage"

* verify stage "2" is "Passed" on pipeline with label "2"






 




Teardown of contexts 
* Capture go state "UserDefinedStageApproval" - teardown
* With "1" live agents in directory "UserDefinedStageApproval" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


