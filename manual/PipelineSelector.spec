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

PipelineSelector
================

Setup of contexts
* Basic configuration - setup
* Using pipeline "up, edit-pipeline, autoFirst, child-of-autoFirst, artifact-md5-up, artifact-md5-skip" - setup
* Logout on exit - setup
* Capture go state "PipelineSelector" - setup

PipelineSelector
----------------

This spec is moved to ruby functional tests
tags: 3843, UI, 5424, New Item, ignore

* Turn on autoRefresh - On Pipeline Dashboard Page
* Open pipelines selector

Make selections when security is disabled
* Verify show newly created pipelines option status is "checked"
* Verify groups "basic, dependency-group, artifact_md5_verification" are visible - Already on pipelines selector section
* Deselect all pipelines
* Select group "basic"
* Verify all pipelines in group "basic" are selected
* Select group "artifact_md5_verification"
* Deselect pipeline "artifact-md5-up" and verify "artifact_md5_verification" is deselected
* Apply selections

Verify visiblility after pipeline selection
* Turn on autoRefresh - Already On Pipeline Dashboard Page
* Verify group "basic" is visible
* Verify pipeline "up" is visible - Already On Pipeline Dashboard Page
* Verify pipeline "edit-pipeline" is visible - Already On Pipeline Dashboard Page
* Verify group "artifact_md5_verification" is visible
* Verify pipeline "artifact-md5-skip" is visible - Already On Pipeline Dashboard Page
* Verify pipeline "artifact-md5-up" is not visible - Already On Pipeline Dashboard Page
* Verify group "dependency-group" is not visible - Already On Pipeline Dashboard Page

Enable security
* Add security with password file and users "admin, pavan" as admin

* Login as "admin"

* Turn on autoRefresh - On Pipeline Dashboard Page
* Open pipelines selector

'admin' makes selections
* Verify show newly created pipelines option status is "checked"
* Set show newly created pipelines option status as "unchecked"
* Deselect all pipelines
* Select group "artifact_md5_verification"
* Select group "dependency-group"
* Apply selections

create new pipeline in group 'basic'
* On Admin page
* Clone pipeline "edit-pipeline" to "admin-test-basic" in pipeline group "basic"

create new pipeline in group 'dependency-group'
* On Admin page
* Clone pipeline "edit-pipeline" to "admin-test-dependency" in pipeline group "dependency-group"


* Logout - On Any Page

* Login as "pavan"

Verify that user 'pavan' sees the selections made when security was disabled + the newly added pipelines by 'admin'
* Turn on autoRefresh - On Pipeline Dashboard Page
* Verify group "basic" is visible - On Pipeline Dashboard Page
* Verify pipeline "up" is visible
* Verify pipeline "edit-pipeline" is visible
* Verify pipeline "admin-test-basic" is visible
* Verify group "artifact_md5_verification" is visible - On Pipeline Dashboard Page
* Verify pipeline "artifact-md5-skip" is visible
* Verify pipeline "artifact-md5-up" is not visible
* Verify group "dependency-group" is visible - On Pipeline Dashboard Page
* Verify pipeline "admin-test-dependency" is visible
* Verify pipeline "autoFirst" is not visible
* Verify pipeline "child-of-autoFirst" is not visible
* Open pipelines selector

'pavan' makes selections
* Verify show newly created pipelines option status is "checked"
* Deselect all pipelines
* Select group "basic"
* Apply selections


create new pipeline in group 'basic'
* On Admin page
* Clone pipeline "edit-pipeline" to "pavan-test-basic" in pipeline group "basic"

create new pipeline in group 'dependency-group'
* On Admin page
* Clone pipeline "edit-pipeline" to "pavan-test-dependency" in pipeline group "dependency-group"


Verify 'admin' sees his own selections
* Logout - On Any Page

* Login as "admin"

Verify 'admin' sees his own selections + newly created pipelines by 'admin' + don't see any pipelines created by pavan
* Verify group "artifact_md5_verification" is visible - On Pipeline Dashboard Page
* Verify pipeline "artifact-md5-up" is visible
* Verify pipeline "artifact-md5-skip" is visible
* Verify group "dependency-group" is visible - On Pipeline Dashboard Page
* Verify pipeline "admin-test-dependency" is visible
* Verify pipeline "pavan-test-dependency" is not visible
* Verify pipeline "autoFirst" is visible
* Verify pipeline "child-of-autoFirst" is visible
* Verify group "basic" is visible - On Pipeline Dashboard Page
* Verify pipeline "admin-test-basic" is visible
* Verify pipeline "pavan-test-basic" is not visible
* Verify pipeline "up" is not visible
* Verify pipeline "edit-pipeline" is not visible


* Logout - On Any Page

* Login as "pavan"

Verify 'pavan' sees his own selections + newly created pipelines by 'admin' and 'pavan'
* Verify group "artifact_md5_verification" is not visible
* Verify group "dependency-group" is visible - On Pipeline Dashboard Page
* Verify pipeline "pavan-test-dependency" is visible
* Verify pipeline "admin-test-dependency" is not visible
* Verify pipeline "autoFirst" is not visible
* Verify pipeline "child-of-autoFirst" is not visible
* Verify group "basic" is visible - On Pipeline Dashboard Page
* Verify pipeline "up" is visible
* Verify pipeline "edit-pipeline" is visible
* Verify pipeline "pavan-test-basic" is visible
* Verify pipeline "admin-test-basic" is visible

* On Admin page
* Open "Pipelines" tab

* Delete "child-of-autoFirst"
* Move pipeline "artifact-md5-up" from group "artifact_md5_verification" to group "basic"

Verify 'admin' sees his own selections except the deleted pipeline
* Logout - On Any Page

* Login as "admin"

* Verify group "artifact_md5_verification" is visible - On Pipeline Dashboard Page
* Verify pipeline "artifact-md5-skip" is visible
* Verify group "dependency-group" is visible - On Pipeline Dashboard Page
* Verify pipeline "autoFirst" is visible
* Verify pipeline "child-of-autoFirst" is not visible
* Verify pipeline "admin-test-dependency" is visible
* Verify pipeline "pavan-test-dependency" is not visible
* Verify group "basic" is visible - On Pipeline Dashboard Page
* Verify pipeline "artifact-md5-up" is visible
* Verify pipeline "admin-test-basic" is visible
* Verify pipeline "pavan-test-basic" is not visible
* Verify pipeline "up" is not visible
* Verify pipeline "edit-pipeline" is not visible


Verify 'pavan' sees his own selections
* Logout - On Any Page

* Login as "pavan"

* Verify group "artifact_md5_verification" is not visible
* Verify group "dependency-group" is visible - On Pipeline Dashboard Page
* Verify pipeline "pavan-test-dependency" is visible
* Verify pipeline "admin-test-dependency" is not visible
* Verify pipeline "autoFirst" is not visible
* Verify pipeline "child-of-autoFirst" is not visible
* Verify group "basic" is visible - On Pipeline Dashboard Page
* Verify pipeline "up" is visible
* Verify pipeline "edit-pipeline" is visible
* Verify pipeline "pavan-test-basic" is visible
* Verify pipeline "admin-test-basic" is visible
* Verify pipeline "artifact-md5-up" is not visible
* Open pipelines selector

verify that even if 1 pipeline is deselected the group checkbox appears deselected
* Verify group "basic" is deselected






Teardown of contexts
____________________
* Capture go state "PipelineSelector" - teardown
* Logout on exit - teardown
* Using pipeline "up, edit-pipeline, autoFirst, child-of-autoFirst, artifact-md5-up, artifact-md5-skip" - teardown
* Basic configuration - teardown


