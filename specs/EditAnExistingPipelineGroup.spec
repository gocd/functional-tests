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

EditAnExistingPipelineGroup
===========================

Setup of contexts
* Group admin security configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline1,pipeline5" - setup
* Capture go state "EditPipelineGroups" - setup

EditAnExistingPipelineGroup
---------------------------

tags: 4369, Clicky Admin, pipeline group

* Open "Pipelines" tab

* Verify groups "group1,group4" are visible
* Click to edit pipeline group "group4"

* Enter "group5" as group name
* Click save - Already On Pipeline Group Edit Page
* Verify that the message "Saved successfully." shows up

* On Admin page
* Open "Pipelines" tab

* Verify groups "group1,group5" are visible
* Click to edit pipeline group "group5"

* Enter user "0" as "pavan" with permissions "admin"
* Click save - Already On Pipeline Group Edit Page
* Verify that the message "Saved successfully." shows up
* Enter role "0" as "role-that-does-not-exist" with permissions "view"
* Click save - Already On Pipeline Group Edit Page
* Verify shows error "Role \"role-that-does-not-exist\" does not exist." on role "0"


* Logout and login as "pavan"

* On Admin page
* Open "Pipelines" tab

* Verify groups "group5" are visible
* Click to edit pipeline group "group5"

* Enter user "1" as "admin" with permissions "operate"
* Enter user "2" as "Rajeshvaran" with permissions "view, operate"
* Enter user "3" as "shilpa" with permissions "admin"
* Enter role "0" as "viewer" with permissions "view"
* Enter role "1" as "operator" with permissions "operate"
* Enter role "2" as "admins" with permissions "admin"
* Click save - Already On Pipeline Group Edit Page
* Verify that the message "Saved successfully." shows up

* Verify pipeline group "group5" has "user" "shilpa" with "admins" permissions
* Verify pipeline group "group5" has "role" "admins" with "admins" permissions
* Verify pipeline group "group5" has "user" "admin" with "operate" permissions
* Verify pipeline group "group5" has "user" "Rajeshvaran" with "view,operate" permissions
* Verify pipeline group "group5" has "role" "operator" with "operate" permissions
* Verify pipeline group "group5" has "role" "viewer" with "view" permissions

* On Admin page
* Open "Pipelines" tab

* Click to edit pipeline group "group5"

* Delete user "shilpa"
* Delete role "viewer"
* Click save - Already On Pipeline Group Edit Page
* Verify that the message "Saved successfully." shows up

* Verify pipeline group "group5" does not have "user" "shilpa" with "admins" permissions
* Verify pipeline group "group5" does not have "role" "viewer" with "view" permissions














Teardown of contexts
* Capture go state "EditPipelineGroups" - teardown
* Using pipeline "pipeline1,pipeline5" - teardown
* Login as "admin" - teardown
* Group admin security configuration - teardown


