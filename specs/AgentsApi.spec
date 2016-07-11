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

AgentsApi
=========

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline-fast, admin-pipeline" - setup
* With "3" live agents in directory "AgentsApi" - setup
* Capture go state "AgentsApi" - setup

AgentsApi
---------

tags: 1621, agents, restful api, automate, 3450, 6996, 7033, OOM


Developer Note: We should move the following 'verfy' methods to the 'Using Agents API' fixture
when we have a proper API for agents. Going through the web UI here because we don't have a
better API yet.

 Verify data in agent listing api is same as data on agents tab - This needs relook
* Disabling a "Idle" agent should return "200"
* Disabling a "Disabled" agent should return "200"
* Enabling a "Idle" agent should return "200"
* Deleting a "Idle" agent should return "406"
* Verify "PENDING" agent count is "0"
* Verify "ENABLED" agent count is "3"
* Verify "DISABLED" agent count is "2"
* Enabling a "Disabled" agent should return "200"
* Verify "PENDING" agent count is "0"
* Verify "ENABLED" agent count is "4"
* Verify "DISABLED" agent count is "1"
* Deleting a "Disabled" agent should return "200"

* Enable "non-existent-uuid"
* Verify return code is "404"
* Delete "non-existent-uuid" - Using Agents Api
* Verify return code is "404"

Verify security unauthorized
* Logout - On Any Page

* Login as "view"

* Logged in as "view"

* Enable "some-uuid"
* Verify return code is "401"
* Delete "some-uuid" - Using Agents Api
* Verify return code is "401"

The following randomly fails with a timeout issue on IE when fetching the agents page in the
constructor. I've tried various workarounds, but nothing fixes the problem... commenting out for
now because it's not a critical assertion. Will watch it run on the agent to debug further... yogi
On Agents Page
verify "New Agent" link is not present






Teardown of contexts
____________________
* Capture go state "AgentsApi" - teardown
* With "3" live agents in directory "AgentsApi" - teardown
* Using pipeline "basic-pipeline-fast, admin-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


