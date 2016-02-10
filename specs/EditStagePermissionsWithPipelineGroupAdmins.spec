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

EditStagePermissionsWithPipelineGroupAdmins
===========================================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "EditStagePermissionsWithPipelineGroupAdmins" - setup

EditStagePermissionsWithPipelineGroupAdmins
-------------------------------------------

tags: Clicky Admin, 4690

Verify that an admin or a pipeline group admin can be explicitly added as an operate user

* Open "User Summary" tab

* Search for "pavan" and add user "1" in the search result
* Search for "raghu" and add user "1" in the search result

* Adding "pavan" as a group admin of "basic"

* On Pipeline Configuration wizard
* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Navigate to "Permissions" tab
* Select "Specify locally"
* Enter "pavan" as user name
* Save - Already On Edit Stage Page
* Verify that user "pavan" is authorized to operate on the stage
* Enter "admin" as user name
* Save - Already On Edit Stage Page
* Verify that user "admin" is authorized to operate on the stage


Teardown of contexts
____________________
* Capture go state "EditStagePermissionsWithPipelineGroupAdmins" - teardown
* Using pipeline "edit-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


