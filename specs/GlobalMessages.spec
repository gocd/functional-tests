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

GlobalMessages
==============

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-fast" - setup
* Capture go state "GlobalMessages" - setup

GlobalMessages
--------------

tags: 3540, global messages, automate

* Make pipeline "basic-pipeline-fast" use non existant material
* Changing the artifacts location to "somethingNew"

* Trigger pipeline

* On Admin page

* Verify there are at least "1" errors
* Verify there are "1" warnings

* Verify there are at least "1" errors
* Verify there are "1" warnings
* Open error and warning messages popup

* Verify error "message" contains "Could not trigger pipeline"
* Verify warning "message" contains "The change in the artifacts directory location will not take effect until the Go Server is restarted"

* Changing the artifacts location to "artifacts"

* On Pipeline Dashboard Page
* Verify there are at least "1" errors
* Verify there are no warnings

* Verify there are at least "1" errors
* Verify there are no warnings
* Open error and warning messages popup

* Verify warning "message" do not contain "The change in the artifacts directory location will not take effect until the Go Server is restarted"
* Close





Teardown of contexts
____________________
* Capture go state "GlobalMessages" - teardown
* Using pipeline "basic-pipeline-fast" - teardown
* Basic configuration - teardown


