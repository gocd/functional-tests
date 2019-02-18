Copyright 2018 ThoughtWorks, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

PipelineGroupPermissionsForUserWithOperateViewAndAdmin
======================================================

Setup of contexts
* Group admin security configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline1, pipeline2, pipeline3" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "PipelineGroupPermissionsForUserWithOperateViewAndAdmin" - setup

PipelineGroupPermissionsForUserWithOperateViewAndAdmin
------------------------------------------------------

tags: 4138, group admin


This scenario tests the system when an user (group1Admin) is a Pipeline Group Admin for group 1  and an Operate only user for group 2 and is View only user for group 3.
In this case, the user should have all group admin permissions on group 1 , should only be able to operate pipelines in group 2 and should only be able to view pipelines in group 3.


* Logout - On Any Page

* Login as "group1Admin"

Pipeline Visibility
* PipelineVisibility 
     |Pipeline Name|Is Visible?|Can Operate?|
     |-------------|-----------|------------|
     |pipeline1    |true       |true        |
     |pipeline2    |false      |true        |
     |pipeline3    |true       |false       |


* Verify source xml is not visible
* Verify server configuration is not visible
* Verify user summary is not visible
* Verify templates are visible
* Open "Pipelines" tab

* Verify pipeline groups "group2" are not visible
* Verify pipeline groups "group1" are visible
* Verify pipelines "pipeline1" are visible
* Edit pipeline "pipeline1"

* Enter "crap-${COUNT}-label" for label template
* Click save - Already On General Options Page
* Verify saved successfully - Already On General Options Page

* Verify any operation on agents return code "403" - Using Agents Api

Implement these once the sahi issue is sorted out.

edit pipeline "pipeline1"
rename job to "renamedJob"
save
verify the save was successful





Teardown of contexts
____________________
* Capture go state "PipelineGroupPermissionsForUserWithOperateViewAndAdmin" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "pipeline1, pipeline2, pipeline3" - teardown
* Login as "admin" - teardown
* Group admin security configuration - teardown


