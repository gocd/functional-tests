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

ComparePipelinesWithTriangleDependency
======================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "fourth, fifth, another-sixth-manual" - setup
* With "1" live agents in directory "compare_pipelines" - setup
* Capture go state "ComparePipelinesWithTriangleDependency" - setup

ComparePipelinesWithTriangleDependency
--------------------------------------

tags: 4643, automate, compare_pipeline, dependency_walk

* With material "hg-first" for pipeline "fourth"
* Checkin file "hg-new-file" as user "go" with message "Added hg-new-file"
* Remember current version as "hg-new"

* Trigger pipelines "fourth" and wait for labels "1" to pass
* Trigger pipelines "fourth" and wait for labels "2" to pass
* Trigger pipelines "fifth" and wait for labels "1" to pass
* Trigger pipelines "another-sixth-manual" and wait for labels "1" to pass
* Compare pipeline instance "1" with "1"

* Verify displays revision "${runtime_name:fourth}/2/defaultStage/1" having label "2" under pipeline named "${runtime_name:fourth}"
* Verify displays revision "${runtime_name:fifth}/1/defaultStage/1" having label "1" under pipeline named "${runtime_name:fifth}"
* Verify displays revision "hg-new" having comment "Added hg-new-file" under "Mercurial" named "hg-first" for pipeline "fourth"




Teardown of contexts
____________________
* Capture go state "ComparePipelinesWithTriangleDependency" - teardown
* With "1" live agents in directory "compare_pipelines" - teardown
* Using pipeline "fourth, fifth, another-sixth-manual" - teardown
* Basic configuration - teardown


