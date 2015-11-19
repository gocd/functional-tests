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

PipelineDependencyWithDependencyMaterialAtHop
=============================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "fourth, fifth, sixth, last-sixth" - setup
* With "2" live agents in directory "StageDetails" - setup
* Capture go state "PipelineDependencyWithDependencyMaterialAtHop" - setup

PipelineDependencyWithDependencyMaterialAtHop
---------------------------------------------

tags: dependency pipeline, 3695, automate

Get to a sane state

* Trigger pipelines "fourth" and wait for labels "1" to pass
* Trigger pipelines "fifth" and wait for labels "1" to pass
* Trigger pipelines "sixth" and wait for labels "1" to pass
* Looking at pipeline "last-sixth"
* Verify stage "1" is "Passed" on pipeline with label "1"

Case of Dependency material with hop

* With material named "hg-first" in pipeline "fourth"
* Checkin file "abc.txt" as user "user" with message "Comment user"
* Commit file "new_artifact.txt" to directory "baz"

* On Pipeline Dashboard Page
* Trigger pipelines "fourth" and wait for labels "2" to pass

* For pipeline "fifth" - Using pipeline api
* Using "${runtime_name:fourth}/1/defaultStage/1" revision of "${runtime_name:fourth}"
* Schedule should return code "202"

* Looking at pipeline "fifth"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Trigger pipelines "sixth" and wait for labels "2" to pass
* Looking at pipeline "last-sixth"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Looking at pipeline "last-sixth"
* Open changes section for counter "2"

* Looking at material of type "Pipeline" named "${runtime_name:fourth}" for pipeline "last-sixth" with counter "2"
* Verify has only "1" modifications
* Verify modification "0" has revision "${runtime_name:fourth}/1/defaultStage/1"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Navigate to job "job-of-last"




Teardown of contexts
* Capture go state "PipelineDependencyWithDependencyMaterialAtHop" - teardown
* With "2" live agents in directory "StageDetails" - teardown
* Using pipeline "fourth, fifth, sixth, last-sixth" - teardown
* Basic configuration - teardown


