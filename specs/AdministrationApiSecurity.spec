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

AdministrationApiSecurity
=========================

Setup of contexts
* Group admin security configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline1, pipeline2, pipeline3" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "AdministrationApiSecurity" - setup

AdministrationApiSecurity
-------------------------

tags: 4141, administration, api, group admin

This scenario ensures that a Pipeline group admin can make another user an admin of the group. Also, ensure that Super user can remove a pipeline group admin.

Setup

The users in the system

Role: group1AdminRole
Group Admins: group1Admin

Admins: admin

Users Not in Roles
		notInAnyRoleA

* Logout - On Any Page

* Login as "group1Admin"

* For pipeline group "group1"
* Making user "notInAnyRoleA" admin

* Logout - On Any Page

* Login as "notInAnyRoleA"

* For pipeline "pipeline1" - Using pipeline api
* Schedule should return code "202"

* Logout - On Any Page

* Login as "admin"

* For pipeline group "group2"
* Removing role "group2AdminRole" from being an admin

* Logout - On Any Page

* Login as "group2Admin"

* For pipeline "pipeline2" - Using pipeline api
* Schedule should return code "401"

* Logout - On Any Page




Teardown of contexts
____________________
* Capture go state "AdministrationApiSecurity" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "pipeline1, pipeline2, pipeline3" - teardown
* Login as "admin" - teardown
* Group admin security configuration - teardown


