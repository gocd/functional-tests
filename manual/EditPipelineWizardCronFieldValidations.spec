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

EditPipelineWizardCronFieldValidations
======================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "EditPipelineWizardCronFieldValidations" - setup

EditPipelineWizardCronFieldValidations
--------------------------------------

tags: 4737, Clicky Admin

* Click on pipeline "edit-pipeline" for editing

Removed the cron field validations since it did not makes sense to check the quartz cron format as part of E2E tests


* Open parameters page

* Enter parameter "1" name "cron_time_specifier" and value "0 30 10-13 ? * WED-FRI"
* Enter parameter "2" name "partial_cron_time_specifier" and value "0 30 10-13 ? *"
* Click save
* Verify "Saved successfully." message is displayed - Already on parameters page
* Open general options page


Cron Field Validations with parameter substituion

* CronFieldValidation 
     |Cron Field Value                                     |valid|
     |-----------------------------------------------------|-----|
     |#{cron_time_specifier}                               |true |
     |#{partial_cron_time_specifier} WED-FRI               |true |
     |#(cron_time_specifier} #{partial_cron_time_specifier}|false|
     |${env_cron_time_specifier}                           |false|
     |${env_partial_cron_specifier}                        |false|


* Go to environment variables page

* Enter environment variable "1" name "env_cron_specifier" and value "0 30 10-13 ? * WED-FRI"
* Enter environment variable "2" name "env_partial_cron_specifier" and value "0 30 10-13 ? * "
* Click save - Already On Environment Variables Page
* Open general options page - Already on environment variables page

Cron time specifier validation with environment variables


* CronFieldValidation 
     |Cron Field Value             |valid|
     |-----------------------------|-----|
     |${env_cron_specifier}        |false|
     |${env_partial_cron_specifier}|false|



Teardown of contexts
____________________
* Capture go state "EditPipelineWizardCronFieldValidations" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


