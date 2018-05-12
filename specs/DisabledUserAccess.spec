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

DisabledUserAccess
==================

Setup of contexts
* With no users - setup
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline" - setup
* With "1" live agents in directory "disabled_users" - setup
* Capture go state "DisabledUserAccess" - setup

DisabledUserAccess
------------------

tags: #3238, Admin, Security

* Add user "operate" - Using user API
* Add user "group1View" - Using user API

* Open "User Summary" tab

* Verify users "operate, group1View" are enabled

* Logout and login as "operate"

* For pipeline "basic-pipeline" - Using pipeline api
* Schedule should return code "202"

* Looking at pipeline "basic-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"

* Logout and login as "admin"

* On Admin page
* Open "User Summary" tab

* Disable users "operate"
* Verify users "operate" are disabled

* On Login Page
* Login as "operate" should fail with message "Your account has been disabled by the administrator Help Topic: Authentication"

* Logged in as "operate"

* For pipeline "basic-pipeline" - Using pipeline api
* Schedule should return code "403"






Teardown of contexts
____________________
* Capture go state "DisabledUserAccess" - teardown
* With "1" live agents in directory "disabled_users" - teardown
* Using pipeline "basic-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown
* With no users - teardown


