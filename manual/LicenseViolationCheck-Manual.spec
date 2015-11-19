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

LicenseViolationCheck-Manual
============================

LicenseViolationCheck-Manual
----------------------------

tags: 5187, timer, manual, license, expiry

* given license is about to expire in "1" minute from now
* the server is running with "1" remote agent
* when user loads any page
* then no "license is expired" server health message is snown
* given "6" minutes have passed
* when user loads any page
* then "Your license allows you to use 0 remote agents, but you are trying to use 1. Please disable agents to comply with your license, or contact our sales team to buy more agents." server health message should be shown

* given license allows for "1" agents
* when user loads any page
* server health error message "You have exceeded license limit of 2 users. There are currently 3 users enabled. Pipeline scheduling has been stopped. Please disable users to comply with the license limit, or contact our sales team to upgrade your license."
* server health warning message "Your license allows you to use 1 remote agents, but you are trying to use 3. Please disable agents to comply with your license, or contact our sales team to buy more agents."
* given user disables all agents except "1"
* when user loads any page
* no server health message should be seen



