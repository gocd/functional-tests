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

EnvironmentsScreenPermissions
=============================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline,admin-pipeline,environment-pipeline,basic-pipeline-slow" - setup
* Capture go state "EnvironmentsScreenPermissions" - setup

EnvironmentsScreenPermissions
-----------------------------

tags: 2808, environment, UI, automate


* Adding pipeline "basic-pipeline" to "uat" environment
* Adding pipeline "admin-pipeline" to "uat" environment

* On Environments Page
* Looking at "uat" environment
* Verify pipeline is visible "basic-pipeline"
* Verify pipeline is visible "admin-pipeline"
* Verify pipeline is visible "environment-pipeline"
* Verify pipeline is not visible "basic-pipeline-slow"
* Verify "basic-pipeline" status is "No historical data"

* Remove pipeline "basic-pipeline" from environment

* On Environments Page
* Looking at "uat" environment
* Verify pipeline is not visible "basic-pipeline"

* Logout - On Any Page

* Login as "view"

* On Environments Page
* Looking at "uat" environment
* Verify pipeline is visible "environment-pipeline"
* Verify pipeline is not visible "admin-pipeline"






Teardown of contexts
____________________
* Capture go state "EnvironmentsScreenPermissions" - teardown
* Using pipeline "basic-pipeline,admin-pipeline,environment-pipeline,basic-pipeline-slow" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


