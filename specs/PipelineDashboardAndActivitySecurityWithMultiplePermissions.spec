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

PipelineDashboardAndActivitySecurityWithMultiplePermissions
===========================================================

Setup of contexts
* Permissions configuration - setup
* Login as "admin" - setup
* Using pipeline "P8, P9" - setup
* With "1" live agents in directory "Pipeline dashboard" - setup
* Capture go state "PipelineDashboardAndActivitySecurityWithMultiplePermissions" - setup

PipelineDashboardAndActivitySecurityWithMultiplePermissions
-----------------------------------------------------------

tags: 6786, Permissions

Verify that user 'group1view' gets union of the different permissions specified to him

* Trigger pipelines "P8" and wait for labels "1" to pass
* Trigger pipelines "P9" and wait for labels "1" to pass


* Logout - On Any Page

* Login as "group1View"


Pipeline Visibility
* PipelineVisibility 
     |Pipeline Name|Is Visible?|Can Operate Using Ui?|Can Operate?|Can Pause Using Ui?|Can Pause Using Api?|
     |-------------|-----------|---------------------|------------|-------------------|--------------------|
     |P8           |true       |true                 |true        |true               |true                |
     |P9           |true       |true                 |true        |true               |true                |


* verify user has operate permissions on "defaultStage" for pipeline "P8" on Pipeline Activity Page

* On Pipeline Dashboard Page
* verify user has operate permissions on "defaultStage" for pipeline "P9" on Pipeline Activity Page

* On Pipeline Dashboard Page
* verify user can operate stage "defaultStage" for pipeline "P8" from stage details page

* On Pipeline Dashboard Page
* verify user can operate stage "defaultStage" for pipeline "P9" from stage details page




Teardown of contexts
____________________
* Capture go state "PipelineDashboardAndActivitySecurityWithMultiplePermissions" - teardown
* With "1" live agents in directory "Pipeline dashboard" - teardown
* Using pipeline "P8, P9" - teardown
* Login as "admin" - teardown
* Permissions configuration - teardown


