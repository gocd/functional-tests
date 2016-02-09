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

EditPipelineWizardProjectManagementPageValidations
==================================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "EditPipelineWizardProjectManagementPageValidations" - setup

EditPipelineWizardProjectManagementPageValidations
--------------------------------------------------

tags: Clicky Admin

* Click on pipeline "edit-pipeline" for editing

* Go to project management page

* Select custom option for tracking tool

* Select custom option for tracking tool
* Enter "***" tracking tool regex
* Enter "http://mingle09.thoughtworks.com/${ID}" for tracking tool uRL
* Click save - Already On Project Management Page
* Verify "Saved successfully." message is displayed - Already on project management page
* Verify custom as option for tracking tool
* Verify "***" as tracking tool regex
* Verify "http://mingle09.thoughtworks.com/${ID}" as tracking tool uRL

Validations for Tracking Tool Regex and Tracking Tool URL
* TrackingToolConfigurationValidation 
     |URL  Value                            |Regex Value|Message For Save Status      |Message For URL                                                                                                                   |Message For Regex                                                                                                            |
     |--------------------------------------|-----------|-----------------------------|----------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
     |                                      |##         |Save failed, see errors below|Link should be populated                                                                                                          |                                                                                                                             |
     |http://mingle09.thoughtworks.com/${ID}|           |Save failed, see errors below|                                                                                                                                  |Regex should be populated                                                                                                    |
     |http://mingle09.thoughtworks.com      |           |Save failed, see errors below|Link must be a URL containing '${ID}'. Go will replace the string '${ID}' with the first matched group from the regex at run-time.|Regex should be populated                                                                                                    |
     |mingle09.thoughtworks.com/${ID}       |##         |Saved successfully.          |                                                                                                                                  |                                                                                                                             |
     |#                                     |#          |Save failed, see errors below|Error when processing params for '#' used in field 'link', # must be followed by a parameter pattern or escaped by another #      |Error when processing params for '#' used in field 'regex', # must be followed by a parameter pattern or escaped by another #|



* Open parameters page - Already on project management page

* Enter parameter "1" name "tracking_tool_url" and value "http://mingle09.thoughtworks.com/${ID}"
* Enter parameter "2" name "tracking_tool_regex" and value "##"
* Enter parameter "3" name "partial_tracking_tool_url" and value "http://mingle09.thoughtworks.com/"
* Enter parameter "4" name "partial_tracking_tool_regex" and value "#"
* Click save
* Verify "Saved successfully." message is displayed - Already on parameters page
* Go to project management page - Already on parameters page


* Select custom option for tracking tool

Validations for tracking tool with parameters
* TrackingToolConfigurationValidation 
     |URL  Value                                       |Regex Value                                   |Message For Save Status      |Message For URL                                                                                                                   |Message For Regex                                                                                                                                  |
     |-------------------------------------------------|----------------------------------------------|-----------------------------|----------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
     |#{tracking_tool_url}                             |#{tracking_tool_regex}                        |Saved successfully.          |                                                                                                                                  |                                                                                                                                                   |
     |#{partial_tracking_tool_url}${ID}                |#{partial_tracking_tool_regex}                |Saved successfully.          |                                                                                                                                  |                                                                                                                                                   |
     |#{partial_tracking_tool_url}                     |#{tracking_tool_regex}#                       |Save failed, see errors below|Link must be a URL containing '${ID}'. Go will replace the string '${ID}' with the first matched group from the regex at run-time.|Error when processing params for '#{tracking_tool_regex}#' used in field 'regex', # must be followed by a parameter pattern or escaped by another #|
     |#{partial_tracking_tool_url}${ID}/home/index.html|#{tracking_tool_regex}###{tracking_tool_regex}|Saved successfully.          |                                                                                                                                  |                                                                                                                                                   |


* Go to environment variables page - Already on project management page

* Enter environment variable "1" name "evn.tracking_tool_url" and value "http://mingle09.thoughtworks.com/${ID}"
* Enter environment variable "2" name "evn.tracking_tool_regex" and value "##"
* Click save - Already On Environment Variables Page
* Verify "Saved successfully." message is displayed - Already On Environment Variables Page
* Go to project management page - Already on environment variables page

* Select custom option for tracking tool

Validation for tracking tool details with environment variables
* TrackingToolConfigurationValidation 
     |URL  Value                    |Regex Value               |Message For Save Status      |Message For URL                                                                                                                   |Message For Regex|
     |------------------------------|--------------------------|-----------------------------|----------------------------------------------------------------------------------------------------------------------------------|-----------------|
     |${evn.tracking_tool_url}/${ID}|${evn.tracking_tool_regex}|Saved successfully.          |                                                                                                                                  |                 |
     |${evn.tracking_tool_url}      |${evn.tracking_tool_regex}|Save failed, see errors below|Link must be a URL containing '${ID}'. Go will replace the string '${ID}' with the first matched group from the regex at run-time.|                 |


* Open parameters page - Already on project management page


* Go to project management page - Already on parameters page


* Select mingle option for tracking tool


Validation for mingle card activity

* MingleCardActivityConfigurationValidation 
     |Mingle URL                       |Mingle Project Identifier|grouping Condition|Response On Save             |Err on Url                                                                                                          |Err on Project Id                                                                                                          |Err on MQL                                                                                                                 |
     |---------------------------------|-------------------------|------------------|-----------------------------|--------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
     |https://mingle09.thoughtworks.com|go                       |                  |Saved successfully.          |                                                                                                                    |                                                                                                                           |                                                                                                                           |
     |http://mingle09.thoughtworks.com |                         |#                 |Save failed, see errors below|Should be a URL starting with https://                                                                              |Should be a valid mingle identifier.                                                                                       |Error when processing params for '#' used in field 'mql', # must be followed by a parameter pattern or escaped by another #|
     |                                 |go                       |                  |Save failed, see errors below|Should be a URL starting with https://                                                                              |                                                                                                                           |                                                                                                                           |
     |#{mingle_url}                    |#{mingle_identifier}     |#{mql_condition}  |Save failed, see errors below|Parameter 'mingle_url' is not defined. All pipelines using this parameter directly or via a template must define it.|Parameter 'mingle_identifier' is not defined. All pipelines using this parameter directly or via a template must define it.|Parameter 'mql_condition' is not defined. All pipelines using this parameter directly or via a template must define it.    |
     |                                 |                         |select from cards |Save failed, see errors below|Should be a URL starting with https://                                                                              |Should be a valid mingle identifier.                                                                                       |                                                                                                                           |



* Open parameters page - Already on project management page

* Enter parameter "5" name "mingle_url" and value "https://mingle09.thoughtworks.com"
* Enter parameter "6" name "mingle_project_identifier" and value "go"
* Enter parameter "7" name "mql_condition" and value "{{   table     query: SELECT number, name, type, status WHERE 'Features - Story' = THIS CARD ORDER BY status }}"
* Enter parameter "8" name "partial_mingle_url" and value "mingle09.thoughtworks.com"
* Enter parameter "9" name "partial_mingle_identifier" and value "g"
* Enter parameter "10" name "partial_mql_condition" and value ""
* Click save
* Verify "Saved successfully." message is displayed - Already on parameters page
* Go to project management page - Already on parameters page


* Select mingle option for tracking tool


Validations for mingle url using parameters
* MingleCardActivityConfigurationValidation 
     |Mingle URL                   |Mingle Project Identifier     |grouping condition                      |Response On Save             |Err on Url                            |Err on Project Id                   |Err on MQL                                                                                                                 |
     |-----------------------------|------------------------------|----------------------------------------|-----------------------------|--------------------------------------|------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
     |#{mingle_url}                |#{mingle_project_identifier}  |#{mql_condition}                        |Saved successfully.          |                                      |                                    |                                                                                                                           |
     |https://#{partial_mingle_url}|#{partial_project_identifier}o|#{partial_mql_condition}#{mql_condition}|                             |                                      |                                    |                                                                                                                           |
     |#{partial_mingle_url}        |                              |#                                       |Save failed, see errors below|Should be a URL starting with https://|Should be a valid mingle identifier.|Error when processing params for '#' used in field 'mql', # must be followed by a parameter pattern or escaped by another #|


* Go to environment variables page - Already on project management page


* Enter environment variable "1" name "env.mingle_url" and value "https://mingle09.thoughtworks.com/${ID}"
* Enter environment variable "2" name "env.mingle_project_identifier" and value "##"
* Enter environment variable "3" name "env.mingle_mql_condition" and value "{{   table     query: SELECT number, name, type, status WHERE 'Features - Story' = THIS CARD ORDER BY status }}"
* Click save - Already On Environment Variables Page
* Verify "Saved successfully." message is displayed - Already On Environment Variables Page
* Go to project management page - Already on environment variables page

* Select mingle option for tracking tool

Validations for mingle url using environments. Environment variables should not be substituted
* MingleCardActivityConfigurationValidation 
     |Mingle URL       |Mingle Project Identifier       |grouping condition         |Response On Save             |Err on Url                            |Err on Project Id|Err on MQL|
     |-----------------|--------------------------------|---------------------------|-----------------------------|--------------------------------------|-----------------|----------|
     |${env.mingle_url}|${env.mingle_project_identifier}|${env.mingle_mql_condition}|Save failed, see errors below|Should be a URL starting with https://|                 |          |







Teardown of contexts
____________________
* Capture go state "EditPipelineWizardProjectManagementPageValidations" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


