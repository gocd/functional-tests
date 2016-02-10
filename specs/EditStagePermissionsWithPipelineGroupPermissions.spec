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

EditStagePermissionsWithPipelineGroupPermissions
================================================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "EditStagePermissionsWithPipelineGroupPermissions" - setup

EditStagePermissionsWithPipelineGroupPermissions
------------------------------------------------

tags: Clicky Admin, 4690

* Open "User Summary" tab

* Search for "pavan" and add user "1" in the search result
* Search for "raghu" and add user "1" in the search result

* Adding "pavan" as a "view" user for group "basic"
* Adding "viewer" as a "view" role for group "basic"
* Adding "raghu" as a "operate" user for group "basic"
* Adding "operator" as a "operate" role for group "basic"

* On Pipeline Configuration wizard
* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

Make sure that pipeline group operate users and roles are shown as inheritted

* Open stage "defaultStage"

* Navigate to "Permissions" tab
* Verify that "Inherit from the pipeline group" is selected
* Verify that user "raghu" is inheritted
* Verify that role "operator" is inheritted


Make sure that only users who operate permissions (and not view users) on the pipeline group can be added into stage authorization

* Select "Specify locally"
* Verify that user "raghu" is already added
* Verify that role "operator" is already added
* Save - Already On Edit Stage Page
* Verify that user "raghu" is authorized to operate on the stage
* Verify that role "operator" is authorized to operate on the stage

* Enter "operatorUser" as user name
* Save - Already On Edit Stage Page
* Verify that user "operatorUser" is authorized to operate on the stage

* Enter "pa" as user name
* Auto complete should not show up "pavan" - Already On Edit Stage Page
* Enter "pavan" as user name
* Save - Already On Edit Stage Page
* On the field "user" with value "pavan"
* Verify message "User \"pavan\" who is not authorized to operate pipeline group can not be authorized to approve stage" shows up - Already On Edit Stage Page

* Enter "view" as role name
* Auto complete should not show up "viewer" - Already On Edit Stage Page
* Enter "viewer" as role name
* Save - Already On Edit Stage Page
* On the field "role" with value "viewer"
* Verify message "Role \"viewer\" who is not authorized to operate pipeline group can not be authorized to approve stage" shows up - Already On Edit Stage Page





Teardown of contexts
____________________
* Capture go state "EditStagePermissionsWithPipelineGroupPermissions" - teardown
* Using pipeline "edit-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


