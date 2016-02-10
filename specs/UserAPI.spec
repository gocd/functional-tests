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

UserAPI
=======

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Capture go state "UserAPI" - setup

UserAPI
-------

tags: 7478, api, users

* Open "User Summary" tab

* Add and verify "operate"
* Add and verify "operatorUser"
* Add and verify "notInAnyRoleA"

* Attempt to delete "operate" user and should return "400"
* Attempt to delete "operatorUser" user and should return "400"

* Disable users "operate, operatorUser"
* Verify users "operate, operatorUser" are disabled

* Attempt to delete "operate" user and should return "200"
* Attempt to delete "operatorUser" user and should return "200"
* Attempt to delete "non_existent-user" user and should return "404"

* Logout and login as "view"

* Attempt to delete "notInAnyRoleA" user and should return "401"

* Logout - On Any Page




Teardown of contexts
____________________
* Capture go state "UserAPI" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


