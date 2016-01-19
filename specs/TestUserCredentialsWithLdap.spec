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

TestUserCredentialsWithLdap
===========================

Setup of contexts
* Valid ldap configuration - setup
* Capture go state "TestUserCredentialsWithLdap" - setup

TestUserCredentialsWithLdap
---------------------------

tags: 6441, ldap, internal

user should get logged in if valid ldap credentials are provided
* Login with username "user1" and password "pass_user1"
* User should get logged in as "User_user1 LastName_user1"


user should see error message when incorrect username is provided
* Login with username "does_not_exist" and password "password"
* Assert login error "Bad credentials Help Topic: Authentication"


user should see error message when incorrect password is provided
* Login with username "user1" and password "incorrect_password"
* Assert login error "Bad credentials Help Topic: Authentication"


Teardown of contexts
* Capture go state "TestUserCredentialsWithLdap" - teardown
* Valid ldap configuration - teardown


