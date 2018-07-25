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

PackagePipelineScheduleApi
==========================

Setup of contexts
* Package configuration - setup
* Setup http based yum repo - setup
* Using pipeline "pipeline_with_yum_repo_package_api" - setup
* With "1" live agents in directory "pipeline_with_yum_repo_package_api" - setup
* Capture go state "pipeline_with_yum_repo_package_api" - setup

PackagePipelineScheduleApi
--------------------------

tags: 7805, plugins-tests

* For pipeline "pipeline_with_yum_repo_package_api" - Using pipeline api
* Schedule should return code "202"

* Looking at pipeline "pipeline_with_yum_repo_package_api"
* Wait for labels "1" to pass

* Publish new artifact

* For pipeline "pipeline_with_yum_repo_package_api" - Using pipeline api
* Using "go-agent-13.1.1-16715.noarch" revision of "package-id"
* Schedule should return code "202"

Disabling below steps since it has a flaky behavior, the scope of the spec is for the schedule api, so restricting within that
YumRepoPackageAsMaterial.spec takes care of checking if publish new material on yum repo triggers the pipeline

 On Pipeline Dashboard Page
 Looking at pipeline "pipeline_with_yum_repo_package_api"
 Wait for labels "2" to pass

* For pipeline "pipeline_with_yum_repo_package_api" - Using pipeline api
* Using "go-agent-13.1.1-16715.noarch" revision of "tw-repo:go-agent1"
* Schedule should fail with "422" and message "Request to schedule pipeline rejected { Pipeline 'pipeline_with_yum_repo_package_api"



Teardown of contexts
____________________
* Capture go state "pipeline_with_yum_repo_package_api" - teardown
* With "1" live agents in directory "pipeline_with_yum_repo_package_api" - teardown
* Using pipeline "pipeline_with_yum_repo_package_api" - teardown
* Setup http based yum repo - teardown
* Package configuration - teardown


