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

NewAdminRoles
=============

Setup of contexts 
* Capture go state "NewAdminRoles" - setup

NewAdminRoles
-------------

tags: 1952, authentication, passwordfile support, security

Happy Path

tc1. Verify that role can be defined in config file, can be added into admin tag successfully, and those users included in role of admin tag can perform as an admin.

* create password file with users "adam, david, jez, qiaoliang, chris, hukai"
* create role "mingle_admin" with users "adam, david"
* create role "cruise_admin" with users "jez, david"
* add roles "mingle_admin, cruise_admin" to admin
* add admins "qiaoliang"
* add admins "jez"

Log In And Verify If Admin
* LogInAndVerifyIfAdmin
| username | is admin? | 
| adam | true | 
| david | true | 
| chris | false | 
| hukai | false | 
| qiaoliang | true | 
| jez | true | 


tc4. Verify that after a user is removed from a role while the role is added into admin tabs, then the user can not perform as admin

* remove user "adam" from role "mingle_admin"

Log In And Verify If Admin
* LogInAndVerifyIfAdmin
| username | is admin? | 
| adam | false | 
| david | true | 






Teardown of contexts 
* Capture go state "NewAdminRoles" - teardown


