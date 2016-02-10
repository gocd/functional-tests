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

YumRepoPackageAsMaterial
========================

Setup of contexts
* Setup http based yum repo - setup
* Package configuration - setup
* Using pipeline "pipeline_with_yum_repo_package_as_material, pipeline_repo_user_has_special_password" - setup
* With "2" live agents in directory "YumRepoPackageAsMaterial" - setup
* Capture go state "YumRepoPackageAsMaterial" - setup

YumRepoPackageAsMaterial
------------------------

tags: #7272, Yum repo package, plugins-tests, 7364, #7571

* Looking at pipeline "pipeline_with_yum_repo_package_as_material"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "defaultJob"

* Open console tab
* Verify console has environment variable "GO_PACKAGE_TW_REPO_GO_AGENT_LABEL" with value "go-agent-13.1.1-16714.noarch"
* Verify console has environment variable "GO_REPO_TW_REPO_GO_AGENT_REPO_URL" with value "http://localhost:8081/${runtime_package_http_repo_name:http_repo1}"
* Verify console has environment variable "GO_REPO_TW_REPO_GO_AGENT_USERNAME" with value "user"
* Verify console has environment variable "GO_REPO_TW_REPO_GO_AGENT_PASSWORD" with value "********"
* Verify console has environment variable "GO_PACKAGE_TW_REPO_GO_AGENT_PACKAGE_SPEC" with value "go-agent"
* Verify console has environment variable "GO_PACKAGE_TW_REPO_GO_AGENT_LOCATION" with value "http://user:******@localhost:8081/${runtime_package_http_repo_name:http_repo1}/go-agent-13.1.1-16714.noarch.rpm"
* Verify console contains "GO_REPO_TW_REPO_GO_AGENT_PASSWORD : ****** AND GO_PACKAGE_TW_REPO_GO_AGENT_LOCATION : http://user:******@localhost:8081/${runtime_package_http_repo_name:http_repo1}/go-agent-13.1.1-16714.noarch.rpm"

* Publish new artifact

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline_with_yum_repo_package_as_material"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Navigate to stage "defaultStage" of run "2"

* Navigate to job "defaultJob"

* Open console tab
* Verify console has environment variable "GO_PACKAGE_TW_REPO_GO_AGENT_LABEL" with value "go-agent-13.1.1-16715.noarch"
* Verify console has environment variable "GO_PACKAGE_TW_REPO_GO_AGENT_LOCATION" with value "http://user:******@localhost:8081/${runtime_package_http_repo_name:http_repo1}/go-agent-13.1.1-16715.noarch.rpm"

User with password containing special characters
* On Pipeline Dashboard Page
* Looking at pipeline "pipeline_repo_user_has_special_password"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "defaultJob"

* Open console tab
* Verify console has environment variable "GO_PACKAGE_TW_REPO_2_GO_AGENT_NOARCH_LABEL" with value "go-agent-13.1.1-16715.noarch"
* Verify console has environment variable "GO_REPO_TW_REPO_2_GO_AGENT_NOARCH_REPO_URL" with value "http://localhost:8081/${runtime_package_http_repo_name:http_repo1}"
* Verify console has environment variable "GO_REPO_TW_REPO_2_GO_AGENT_NOARCH_USERNAME" with value "user_with_special_password"
* Verify console has environment variable "GO_REPO_TW_REPO_2_GO_AGENT_NOARCH_PASSWORD" with value "********"
* Verify console has environment variable "GO_PACKAGE_TW_REPO_2_GO_AGENT_NOARCH_PACKAGE_SPEC" with value "go-agent.noarch"
* Verify console has environment variable "GO_PACKAGE_TW_REPO_2_GO_AGENT_NOARCH_LOCATION" with value "********"
* Verify console contains "GO_REPO_TW_REPO_2_GO_AGENT_NOARCH_PASSWORD : ****** AND GO_PACKAGE_TW_REPO_2_GO_AGENT_NOARCH_LOCATION : ******"





Teardown of contexts
____________________
* Capture go state "YumRepoPackageAsMaterial" - teardown
* With "2" live agents in directory "YumRepoPackageAsMaterial" - teardown
* Using pipeline "pipeline_with_yum_repo_package_as_material, pipeline_repo_user_has_special_password" - teardown
* Package configuration - teardown
* Setup http based yum repo - teardown


