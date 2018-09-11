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

PipelineXml
===========

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline-fast, admin-pipeline" - setup
* With "1" live agents in directory "NOT-USED" - setup
* Capture go state "PipelineXml" - setup

PipelineXml
-----------

tags: 3378, 3411, feeds, shine, automate

* With material "basic-pipeline-hg" for pipeline "basic-pipeline-fast"
* Checkin file "new-file-1" as user "go <go@po.com>" with message "Added new-file 1"

* Logged in as "view"

* Verify "basic-pipeline-fast" is shown
* Verify "admin-pipeline" is not shown

* Verify "basic-pipeline-fast" is viewable
* Verify pipeline instance "foo-pipeline" is not found
* Verify "admin-pipeline" is not viewable

* Logged in as "admin"
* Trigger pipelines "basic-pipeline-fast" and wait for labels "1" to pass
* Trigger pipelines "admin-pipeline" and wait for labels "1" to pass

* Verify shows first instance of "defaultStage" of "admin-pipeline"

* Logged in as "view"

* Verify shows first instance of "defaultStage" of "basic-pipeline-fast"

* Verify loads "basic-pipeline-fast" instance with file "new-file-1"
* Verify unauthorized to load "admin-pipeline"
* Verify fails to find "basic-pipeline-fast" with bad id





Teardown of contexts
____________________
* Capture go state "PipelineXml" - teardown
* With "1" live agents in directory "NOT-USED" - teardown
* Using pipeline "basic-pipeline-fast, admin-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


