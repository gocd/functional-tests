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

FetchArtifactFromAncestor
=========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "uppest_pipeline, upper_pipeline, upper_peer_pipeline, up_pipeline, down_pipeline" - setup
* With "2" live agents in directory "FetchMaterialFromAncestor" - setup
* Capture go state "FetchMaterialFromAncestor" - setup

FetchArtifactFromAncestor
-------------------------

tags: 5783, fetchartifact, ancestor, stage1

* With material named "hg_material" in pipeline "uppest_pipeline"
* Checkin file "first" as user "user" with message "Comment user"
* With material named "hg_material" in pipeline "uppest_pipeline"
* Checkin file "second" as user "user" with message "Comment user"

* Trigger pipelines "uppest_pipeline" and wait for label "1" to pass for stage "3"
* Trigger pipelines "upper_pipeline" and wait for labels "1" to pass
* Trigger pipelines "uppest_pipeline" and wait for labels "2" to pass
* Trigger pipelines "upper_peer_pipeline" and wait for labels "1" to pass
* Trigger pipelines "up_pipeline" and wait for labels "1" to pass
* Trigger pipelines "down_pipeline" and wait for labels "1" to pass
* Navigate to stage "down-stage1" of run "1" having counter "1"

Successful fetch from an ancestor
* Navigate to job "down-job1"

* Open console tab
* Verify console says that artifact "first" was fetched from "${runtime_name:uppest_pipeline}/1/uppest-stage1/latest/uppest-job1" for path "uppest_pipeline/upper_pipeline/up_pipeline" from "uppest-stage1/uppest-job1"
* Verify console says that artifact "first" was fetched from "${runtime_name:uppest_pipeline}/2/uppest-stage1/1/uppest-job1" for path "uppest_pipeline/upper_peer_pipeline/up_pipeline" from "uppest-stage1/uppest-job1"

Successful fetch from direct parent

* On Pipeline Dashboard Page
* Looking at pipeline "upper_pipeline"
* Navigate to stage "upper-stage1" of run "1" having counter "1"

* Navigate to job "upper-job1"

* Open console tab
* Verify console says that artifact "first" was fetched from "${runtime_name:uppest_pipeline}/1/uppest-stage1/latest/uppest-job1" for path "uppest_pipeline" from "uppest-stage1/uppest-job1"




Teardown of contexts
____________________
* Capture go state "FetchMaterialFromAncestor" - teardown
* With "2" live agents in directory "FetchMaterialFromAncestor" - teardown
* Using pipeline "uppest_pipeline, upper_pipeline, upper_peer_pipeline, up_pipeline, down_pipeline" - teardown
* Basic configuration - teardown


