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

AdminPermissionsNewUsers
========================

Setup of contexts
* Basic configuration - setup
* Logout on exit - setup
* Capture go state "AdminPermissionsNewUsers" - setup

AdminPermissionsNewUsers
------------------------

tags: 6785, Admin


* Add security with password file only

Verify if user is admin
* VerifyIfUserIsAdmin 
     |login as user|admin|
     |-------------|-----|
     |pavan        |true |
     |raghu        |true |
     |admin        |true |


* Logout - On Any Page

* Login as "admin"

* Open "User Summary" tab

* Verify users "raghu,pavan,admin" are enabled









Teardown of contexts
* Capture go state "AdminPermissionsNewUsers" - teardown
* Logout on exit - teardown
* Basic configuration - teardown


