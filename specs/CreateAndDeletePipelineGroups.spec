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

CreateAndDeletePipelineGroups
=============================

Setup of contexts
* Group admin security configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline1,pipeline2,pipeline4,pipeline-with-group-and-stage-security,pipeline-down" - setup
* Capture go state "CreateAndDeletePipelineGroups" - setup

CreateAndDeletePipelineGroups
-----------------------------

tags: Clicky Admin, pipeline group, 4944, stage1

* Open "Pipelines" tab

* Add new group

* Save - Already On New Pipeline Group Popup
* Verify error message "Invalid group name ''. This must be alphanumeric and can contain underscores and periods (however, it cannot start with a period). The maximum allowed length is 255 characters." - Already On New Pipeline Group Popup
* Enter pipeline group name "some_funky_group" - Already On New Pipeline Group Popup
* Save for success - Already On New Pipeline Group Popup

* Verify groups "group1,group2,groupWithStageSecurity" are visible
* Verify "group1" has pipelines "pipeline1,pipeline4"
* Verify "group2" has pipelines "pipeline2,pipeline-down"
* Verify "groupWithStageSecurity" has pipelines "pipeline-with-group-and-stage-security"
* Verify delete link is disabled for "group1"
* Verify delete link is disabled for "group2"
* Verify delete link is disabled for "groupWithStageSecurity"
* Delete "pipeline-with-group-and-stage-security"
* Delete group "groupWithStageSecurity"
* Verify groups "group2,group1" are visible
* Verify groups "groupWithStageSecurity" is not visible






Teardown of contexts
* Capture go state "CreateAndDeletePipelineGroups" - teardown
* Using pipeline "pipeline1,pipeline2,pipeline4,pipeline-with-group-and-stage-security,pipeline-down" - teardown
* Login as "admin" - teardown
* Group admin security configuration - teardown


