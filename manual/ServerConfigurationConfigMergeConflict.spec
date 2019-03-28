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

ServerConfigurationConfigMergeConflict
======================================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Capture go state "ServerConfigurationConfigMergeConflict" - setup

ServerConfigurationConfigMergeConflict
--------------------------------------

tags: 7294, enterprise

* Open "Server Configuration" tab

* Open "Server Configuration" tab

* Reload page - Already on Server Configuration Tab
* Remember current tab - Already on Server Configuration Tab
* Remember md5 - Already on Server Configuration Tab
* Set field "hostName" to "chimisc01.thoughtworks.com"
* Set field "port" to "25"
* Set field "username" to "pavan"
* Set field "from" to "ccedev@thoughtworks.com"
* Set field "adminMail" to "admin@thoughtworks.com"

* For template "simple-pass"
* For stage "defaultStage"
* For job "defaultJob"
* Add custom command "ls"

* Save configuration
* Verify message contains "Saved configuration successfully. The configuration was modified by someone else, but your changes were merged successfully."








Teardown of contexts
____________________
* Capture go state "ServerConfigurationConfigMergeConflict" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


