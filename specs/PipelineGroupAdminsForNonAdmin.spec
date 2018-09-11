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

PipelineGroupAdminsForNonAdmin
==============================

Setup of contexts
* Group admin security configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline1, pipeline2,pipeline3" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "PipelineGroupAdminsForNonAdmin" - setup

PipelineGroupAdminsForNonAdmin
------------------------------

tags: 4138, group admin

The user "notInAnyRoleA" does not have admin permissions anywhere in config.
He has view permission for group2 and operate permission for group3.

* Logout - On Any Page


* Login as "notInAnyRoleA"

Pipeline Visibility
* PipelineVisibility 
     |Pipeline Name|Is Visible?|Can Operate?|
     |-------------|-----------|------------|
     |pipeline1    |false      |false       |
     |pipeline2    |true       |false       |
     |pipeline3    |false      |true        |


* Verify any operation on agents return code "403" - Using Agents Api

Teardown of contexts
____________________
* Capture go state "PipelineGroupAdminsForNonAdmin" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "pipeline1, pipeline2,pipeline3" - teardown
* Login as "admin" - teardown
* Group admin security configuration - teardown


