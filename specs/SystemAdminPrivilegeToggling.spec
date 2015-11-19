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

SystemAdminPrivilegeToggling
============================

Setup of contexts
* With no users - setup
* Secure configuration - setup
* Login as "admin" - setup
* Capture go state "SystemAdminPrivilegeToggling" - setup

SystemAdminPrivilegeToggling
----------------------------

tags: 4404, admin, role

* Open "User Summary" tab

* Search for "pavan" and add user "1" in the search result
* Search for "raghu" and add user "1" in the search result

* Toggle selection of users "pavan"
* Select role states "admins:add"
* Apply changes to roles and verify message "Role(s)/Admin-Privilege modified for"
* Verify users "pavan" are administrators

* Toggle selection of users "pavan"
* Verify role state "Go System Administrator" is disabled with message "The selected users have administrative privilege via other roles. To remove this privilege, remove them from all administrative roles."
* Toggle selection of users "pavan,raghu"
* Verify role state "Go System Administrator" is enabled
* Select role states "Go System Administrator:add, viewer:add"
* Apply changes to roles and verify message "Role(s)/Admin-Privilege modified for"
* Verify users "raghu" are assigned role "viewer"
* Verify users "pavan,raghu" are administrators

* Toggle selection of users "pavan,raghu"
* Verify role state "Go System Administrator" is disabled with message "The selected users have administrative privilege via other roles. To remove this privilege, remove them from all administrative roles."
* Toggle selection of users "pavan"
* Verify role state "Go System Administrator" is enabled
* Select role states "Go System Administrator:remove"
* Apply changes to roles and verify message "Role(s)/Admin-Privilege modified for"
* Verify users "raghu" are assigned role "viewer"
* Verify users "pavan" are administrators
* Verify users "raghu" are not administrators

* Toggle selection of users "pavan"
* Verify role state "Go System Administrator" is disabled with message "The selected users have administrative privilege via other roles. To remove this privilege, remove them from all administrative roles."
* Select role states "admins:remove, viewer:add"
* Apply changes to roles and verify message "Role(s)/Admin-Privilege modified for"
* Verify users "pavan,raghu" are assigned role "viewer"
* Verify users "pavan,raghu" are not administrators

* Toggle selection of users "pavan,raghu"
* Verify role state "Go System Administrator" is enabled
* Select role states "admins:add, viewer:remove"
* Apply changes to roles and verify message "Role(s)/Admin-Privilege modified for"
* Verify users "pavan,raghu" are administrators





Teardown of contexts
* Capture go state "SystemAdminPrivilegeToggling" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown
* With no users - teardown


