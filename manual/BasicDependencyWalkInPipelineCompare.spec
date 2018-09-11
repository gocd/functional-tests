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

BasicDependencyWalkInPipelineCompare
====================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "up, down,child-of-down" - setup
* With "1" live agents in directory "dependency_walk" - setup
* Capture go state "BasicDependencyWalkInPipelineCompare" - setup

BasicDependencyWalkInPipelineCompare
------------------------------------

tags: 4643, automate, compare_pipeline, dependency_walk, stage1

* Trigger pipelines "up" and wait for labels "1" to pass
* Trigger pipelines "down" and wait for labels "1" to pass
* Trigger pipelines "child-of-down" and wait for labels "1" to pass

* With material "hg-material" for pipeline "up"
* Checkin file "hg-new-file" as user "go" with message "Added hg-new-file"
* Remember current version as "hg-new1"

Verify dependency chaining after scm triggers autoFirst pipeline

* Trigger pipelines "up" and wait for labels "2" to pass
* Trigger pipelines "down" and wait for labels "2" to pass
* Trigger pipelines "child-of-down" and wait for labels "2" to pass

* With material "hg-material" for pipeline "up"
* Checkin file "hg-another-new-file" as user "go" with message "Added hg-another-new-file"
* Remember current version as "hg-new2"

* Trigger pipelines "up" and wait for labels "3" to pass
* Trigger pipelines "down" and wait for labels "3" to pass
* Trigger pipelines "child-of-down" and wait for labels "3" to pass
* Compare pipeline instance "2" with "3"

* Verify displays revision "${runtime_name:up}/3/defaultStage/1" having label "3" under pipeline named "${runtime_name:up}"
* Verify displays revision "${runtime_name:down}/3/defaultStage/1" having label "3" under pipeline named "${runtime_name:down}"
* Verify displays revision "hg-new2" having comment "Added hg-another-new-file" under "Mercurial" named "hg-material" for pipeline "up"
* Search for "2" on "to" textbox
* Click on label "2" in the dropdown
* Verify displays revision "${runtime_name:up}/2/defaultStage/1" having label "2" under pipeline named "${runtime_name:up}"
* Verify displays revision "${runtime_name:down}/2/defaultStage/1" having label "2" under pipeline named "${runtime_name:down}"
* Verify displays revision "hg-new1" having comment "Added hg-new-file" under "Mercurial" named "hg-material" for pipeline "up"






Teardown of contexts
____________________
* Capture go state "BasicDependencyWalkInPipelineCompare" - teardown
* With "1" live agents in directory "dependency_walk" - teardown
* Using pipeline "up, down,child-of-down" - teardown
* Basic configuration - teardown


