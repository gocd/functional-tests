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

SearchAndAddUserWhileLdapIsNotWorking
=====================================

Setup of contexts 
* With no users - setup
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline" - setup
* Capture go state "SearchAndAddUserWhileLdapIsNotWorking" - setup

SearchAndAddUserWhileLdapIsNotWorking
-------------------------------------

tags: 4169, security, Admin, 4171

* logged in as "admin"

User serach should show a warning when either password search fails.
* changing ldap uri to "ldap://invalid.com"

* Login as "admin"

* open "User Summary" tab

* goto add users
* search for user "admin"
* wait for search to return results
* verify message on search popup is "Ldap search failed, please contact the administrator."





Teardown of contexts 
* Capture go state "SearchAndAddUserWhileLdapIsNotWorking" - teardown
* Using pipeline "basic-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown
* With no users - teardown


