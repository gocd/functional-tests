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

NewTurnOnSecurity
=================

Setup of contexts 
* UserIsLogout - setup
* Capture go state "NewTurnOnSecurity" - setup

NewTurnOnSecurity
-----------------

tags: security, smoke, authentication, passwordfile support, dashboard


Test this using both password file and with LDAP
* user "userA" is visiting pipeline wizard page
* security is turned on by admin
* then user "userA" should be redirected to login page automatically
* login as "userA"
* then user "userA" should be redirected to pipeline wizard page





Teardown of contexts 
* Capture go state "NewTurnOnSecurity" - teardown
* UserIsLogout - teardown


