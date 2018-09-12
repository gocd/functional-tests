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

GoServerBaseURLConfig
=====================

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-fast" - setup
* Capture go state "GoServerBaseURLConfig" - setup

GoServerBaseURLConfig
---------------------

tags: 4619, Base URL, Proxy Friendliness

Remove this duplication once TLB supports concepts

Test that the links in the feeds use the siteUrl when set. It will use siteUrl even if secureSiteUrl is set for feeds.

From 18.10 reelase feed apis will have requested URL and not the siteURL set in server configuration, so modified the assertions

* Looking at pipeline "basic-pipeline-fast"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1"

* Cancel "defaultStage" - Already On Stage Detail Page
* Wait for stage result to show "Cancelled"

* Verify the feed id matches ".*?:8253/go/api/pipelines/${runtime_name:basic-pipeline-fast}/stages.xml"

* On Admin page
* Open "Server Configuration" tab

* Verify text field "siteUrl" has value ""
* Verify text field "secureSiteUrl" has value ""
* Set field "siteUrl" to "http://foo.bar:1234"
* Save configuration
* Verify message "Saved configuration successfully." shows up

* Verify the feed id matches ".*?:8253/go/api/pipelines/${runtime_name:basic-pipeline-fast}/stages.xml"

* On Admin page
* Open "Server Configuration" tab

* Verify text field "siteUrl" has value "http://foo.bar:1234"
* Verify text field "secureSiteUrl" has value ""
* Set field "secureSiteUrl" to "http://foo.bar:1334"
* Save configuration
* Verify message contains "Failed to save the server configuration. Reason: secureSiteUrl \"http://foo.bar:1334\" is invalid. It must be a secure URL"
* Set field "secureSiteUrl" to "https://foo.bar:1334"
* Save configuration
* Verify message "Saved configuration successfully." shows up

* Verify the feed id matches ".*?:8253/go/api/pipelines/${runtime_name:basic-pipeline-fast}/stages.xml"

* On Admin page
* Open "Server Configuration" tab

* Set field "siteUrl" to "https://foo.bar:1334"
* Save configuration
* Verify message "Saved configuration successfully." shows up

* Verify the feed id matches ".*?:8253/go/api/pipelines/${runtime_name:basic-pipeline-fast}/stages.xml"

* On Admin page
* Open "Server Configuration" tab

* Set field "siteUrl" to "http://foo.bar:1234"
* Set field "secureSiteUrl" to ""
* Save configuration
* Verify message "Saved configuration successfully." shows up


Teardown of contexts
____________________
* Capture go state "GoServerBaseURLConfig" - teardown
* Using pipeline "basic-pipeline-fast" - teardown
* Basic configuration - teardown


