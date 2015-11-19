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

ClonePipeline
=============

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "ClonePipeline" - setup

ClonePipeline
-------------

tags: Clone, #5164, Clicky Admin

* Open "Pipelines" tab

* Verify "basic" has pipelines "edit-pipeline"
* Click clone button for pipeline "edit-pipeline"

* Save - Already On Clone Pipeline Popup
* Verify error message "Invalid pipeline name ''. This must be alphanumeric and can contain underscores and periods (however, it cannot start with a period). The maximum allowed length is 255 characters." - Already On Clone Pipeline Popup
* Enter pipeline name "Some Invalid Pipeline"
* Verify error message "Invalid pipeline name ''. This must be alphanumeric and can contain underscores and periods (however, it cannot start with a period). The maximum allowed length is 255 characters." - Already On Clone Pipeline Popup
* Enter pipeline name "cloned-pipeline"
* Save for success

* Verify "Cloned successfully." message is displayed
* Verify pipeline "cloned-pipeline" is paused with message "Paused by admin"

* On Admin page
* Open "Pipelines" tab

* Verify "basic" has pipelines "edit-pipeline, cloned-pipeline"
* Click clone button for pipeline "edit-pipeline"

* Enter pipeline name "another-cloned-pipeline"
* Enter pipeline group name ""
* Save for success

* Verify "Cloned successfully." message is displayed
* Verify pipeline "another-cloned-pipeline" is paused with message "Paused by admin"

* On Admin page
* Open "Pipelines" tab

* Verify "basic" has pipelines "edit-pipeline, cloned-pipeline"
* Verify "defaultGroup" has pipelines "another-cloned-pipeline"










Teardown of contexts
* Capture go state "ClonePipeline" - teardown
* Using pipeline "edit-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


