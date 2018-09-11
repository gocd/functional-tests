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

PipelineSelectorWithSecurity
============================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using group "basic, admin-only, viewable, group.with.dot,operable" - setup
* Capture go state "PipelineSelectorWithSecurity" - setup

PipelineSelectorWithSecurity
----------------------------

This spec is moved to ruby functional tests
tags: 3843, UI, 5424, linux, ignore

* Turn on autoRefresh - On Pipeline Dashboard Page
* Open pipelines selector

* Verify groups "basic, admin-only, viewable, group.with.dot,operable" are visible - Already on pipelines selector section
* Verify all pipelines are selected
* Deselect all pipelines
* Verify no pipelines are selected
* Select all pipelines
* Verify all pipelines are selected
* Deselect group "basic"
* Verify all pipelines in group "basic" are deselected
* Deselect pipeline "run.till.file.exists" and verify "group.with.dot" is deselected
* Select pipeline "run.till.file.exists" and verify "group.with.dot" is selected
* Apply selections

* Turn on autoRefresh - Already On Pipeline Dashboard Page
* Verify group "basic" is not visible - Already On Pipeline Dashboard Page
* Verify pipeline "basic-pipeline-run-till-file-exists" is not visible - Already On Pipeline Dashboard Page
* Verify group "admin-only" is visible
* Verify group "viewable" is visible
* Verify group " group.with.dot" is visible
* Verify group "operable" is visible

* Logout - On Any Page

* Login as "view"

* Turn on autoRefresh - On Pipeline Dashboard Page
* Verify group "basic" is visible - On Pipeline Dashboard Page
* Verify group "viewable" is visible - On Pipeline Dashboard Page
* Verify group "group.with.dot" is visible - On Pipeline Dashboard Page
* Verify group "admin-only" is not visible
* Verify group "operable" is not visible
* Open pipelines selector

* Verify groups "basic, viewable, group.with.dot" are visible - Already on pipelines selector section
* Verify groups "admin-only,operable" are not visible - Already on pipelines selector section
* Deselect group "group.with.dot"
* Apply selections

* Turn on autoRefresh - Already On Pipeline Dashboard Page
* Verify group "basic" is visible
* Verify group "viewable" is visible
* Verify group "group.with.dot" is not visible - Already On Pipeline Dashboard Page
* Verify group "admin-only" is not visible - Already On Pipeline Dashboard Page
* Verify group "operable" is not visible - Already On Pipeline Dashboard Page
* Verify pipeline "basic-pipeline-run-till-file-exists" is visible - Already On Pipeline Dashboard Page
* Verify pipeline "run.till.file.exists" is not visible - Already On Pipeline Dashboard Page

* Logout - On Any Page

* Login as "admin"

* Verify group "basic" is not visible
* Verify group "group.with.dot" is visible - On Pipeline Dashboard Page
* Verify group "viewable" is visible - On Pipeline Dashboard Page
* Verify group "admin-only" is visible - On Pipeline Dashboard Page
* Verify group "operable" is visible - On Pipeline Dashboard Page
* Open pipelines selector


* Verify groups "basic, admin-only, viewable, group.with.dot,operable" are visible - Already on pipelines selector section

* Logout and login as "operate"

* Turn on autoRefresh - On Pipeline Dashboard Page
* Verify group "basic" is visible - On Pipeline Dashboard Page
* Verify group "viewable" is visible - On Pipeline Dashboard Page
* Verify group "group.with.dot" is visible - On Pipeline Dashboard Page
* Verify group "operable" is visible - On Pipeline Dashboard Page
* Verify group "admin-only" is not visible
* Open pipelines selector

* Verify groups "basic, viewable, group.with.dot,operable" are visible - Already on pipelines selector section
* Verify groups "admin-only" are not visible - Already on pipelines selector section

* Logout and login as "operatorUser"

* Turn on autoRefresh - On Pipeline Dashboard Page
* Verify group "basic" is visible - On Pipeline Dashboard Page
* Verify group "viewable" is visible - On Pipeline Dashboard Page
* Verify group "group.with.dot" is visible - On Pipeline Dashboard Page
* Verify group "admin-only" is not visible
* Verify group "operable" is not visible
* Open pipelines selector

* Verify groups "basic, viewable, group.with.dot" are visible - Already on pipelines selector section
* Verify groups "admin-only,operable" are not visible - Already on pipelines selector section





Teardown of contexts
____________________
* Capture go state "PipelineSelectorWithSecurity" - teardown
* Using group "basic, admin-only, viewable, group.with.dot,operable" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


