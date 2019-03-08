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

HistoricalConfigSnapshotRetrival
================================

Setup of contexts
* Secure configuration - setup
* Using pipeline "basic-pipeline" - setup
* Capture go state "HistoricalConfigSnapshotRetrival" - setup

HistoricalConfigSnapshotRetrival
--------------------------------

tags: config, api, 1441, versioning

* Alias current md5 as "first-revision"

* Add resource "linux" to all agents - Using Agents API

* Alias current md5 as "second-revision"
* Verify "first-revision" does not have "<resource>linux</resource>"
* Verify "current" has "<resource>linux</resource>"
* Verify "second-revision" has "<resource>linux</resource>"

* Add user "hero" as admin

* Wait for config load
* Alias current md5 as "third-revision"
* Verify "first-revision" does not have "<user>hero</user>"
* Verify "second-revision" does not have "<user>hero</user>"
* Verify "current" has "<user>hero</user>"
* Verify "third-revision" has "<user>hero</user>"


Teardown of contexts
____________________
* Capture go state "HistoricalConfigSnapshotRetrival" - teardown
* Using pipeline "basic-pipeline" - teardown
* Secure configuration - teardown


