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

EditPipelineWizardLabelTemplateValidations
==========================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "EditPipelineWizardLabelTemplateValidations" - setup

EditPipelineWizardLabelTemplateValidations
------------------------------------------

tags: Clicky Admin

* Click on pipeline "edit-pipeline" for editing


Label Template Validations

* LabelTemplateValidations 
     |field value     |valid|
     |----------------|-----|
     |#               |false|
     |${COUNT}        |true |
     |##app${COUNT}   |true |
     |#{param}        |false|
     |${svn_material} |false|
     |${COUNT}${COUNT}|true |


* On Pipeline Configuration wizard
* Click on pipeline "edit-pipeline" for editing

* Open parameters page

* Enter parameter "1" name "label_prefix" and value "cruise"
* Click save
* Verify "Saved successfully." message is displayed - Already on parameters page
* Open general options page

Label Template Validations

* LabelTemplateValidations 
     |field value                   |valid|
     |------------------------------|-----|
     |#{label_prefix}               |false|
     |#{label_prefix}${COUNT}       |true |
     |###{label_prefix} app ${COUNT}|false|

* On Pipeline Configuration wizard
* Click on pipeline "edit-pipeline" for editing

* Open material listing page

* Open new subversion material creation popup

* Enter material name "svn_material"
* Enter url "svn.thoughtworks.com"
* Enter destination directory "svn"
* Click save - Already on Subversion Material Add Popup


* Verify that material saved successfully
* Open general options page - Already on material listing page

label template validations with material name

* LabelTemplateValidations 
     |field value                                                                          |valid|
     |-------------------------------------------------------------------------------------|-----|
     |${svn_material}                                                                      |true |
     |${svn_material}${COUNT}                                                              |true |
     |${svn_material}${COUNT}#{label_prefix}-revision-run##                                |true |
     |${COUNT}#{label_prefix}${svn_material} ##                                            |false|
     |${COUNT}--this_is_longest_check_boundary_value_validation_need_to_check_if_this_works|true |






Teardown of contexts
____________________
* Capture go state "EditPipelineWizardLabelTemplateValidations" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


