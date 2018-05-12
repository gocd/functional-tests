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

AllowOnlyKnownUsersToLogin
==========================

Setup of contexts
* With no users - setup
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline" - setup
* Capture go state "AllowOnlyKnownUsersToLogin" - setup

AllowOnlyKnownUsersToLogin
--------------------------

tags: 4168

* Allow only known users to login

* Logout - On Any Page

* Login as "view" should fail with message "Please ask the administrator to add you to GoCD. Help Topic: Authentication"
* Login as "admin"

* Allow unknown users to login

* Logout and login as "view"





Teardown of contexts
____________________
* Capture go state "AllowOnlyKnownUsersToLogin" - teardown
* Using pipeline "basic-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown
* With no users - teardown


