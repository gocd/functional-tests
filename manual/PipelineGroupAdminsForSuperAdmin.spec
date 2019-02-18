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

PipelineGroupAdminsForSuperAdmin
================================

Setup of contexts
* Group admin security configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline1, pipeline2, pipeline3" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "PipelineGroupAdminsForSuperAdmin" - setup

PipelineGroupAdminsForSuperAdmin
--------------------------------

tags: 4138, group admin

This scenario tests the system for a Super Admin.


Pipeline Visibility
* PipelineVisibility 
     |Pipeline Name|Is Visible?|Can Operate?|
     |-------------|-----------|------------|
     |pipeline1    |true       |true        |
     |pipeline2    |true       |true        |
     |pipeline3    |true       |true        |


* Verify source xml is visible
* Verify server configuration is visible
* Verify user summary is visible
* Verify templates are visible
* Open "Pipelines" tab

* Verify groups "group1, group2, group3" are visible
* Verify "group1" has pipelines "pipeline1"
* Verify "group2" has pipelines "pipeline2"
* Verify "group3" has pipelines "pipeline3"

* Verify any operation on agents return code "200" - Using Agents Api





Teardown of contexts
____________________
* Capture go state "PipelineGroupAdminsForSuperAdmin" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "pipeline1, pipeline2, pipeline3" - teardown
* Login as "admin" - teardown
* Group admin security configuration - teardown


