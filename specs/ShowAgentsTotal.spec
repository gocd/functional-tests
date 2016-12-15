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

ShowAgentsTotal
===============

Setup of contexts
* Basic configuration - setup
* With "1" live agents in directory "ShowAgentsTotal" - setup
* Capture go state "ShowAgentsTotal" - setup

ShowAgentsTotal
---------------

tags: 3241, agents, UI, automate, manual

We have a denied and missing agent by default. Since we don't have support for remote agents yet, we have 0 pending agents.

* Verify the "missing" agent has "Unknown" free space
* Verify "ENABLED" agent count is "2"
* Verify "DISABLED" agent count is "1"
* Verify "PENDING" agent count is "0"




Teardown of contexts
____________________
* Capture go state "ShowAgentsTotal" - teardown
* With "1" live agents in directory "ShowAgentsTotal" - teardown
* Basic configuration - teardown


