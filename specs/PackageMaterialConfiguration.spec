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

PackageMaterialConfiguration
============================

Setup of contexts
* Package configuration - setup
* Using pipeline "pipeline-for-test" - setup
* Setup http based yum repo - setup
* Capture go state "PackageMaterialConfiguration" - setup

PackageMaterialConfiguration
----------------------------

tags: #7272, Yum repo package, plugins-tests, 7317

create material using existing package definition

* Click on pipeline "pipeline-for-test" for editing

* Open material listing page

* Open new package material creation popup

* Verify radio buttons are "disabled"
* Select repository "repo-1"
* Verify radio buttons are "enabled"
* Click save - AlreadyOnPackageMaterialCreationPopup
* Verify global errors has error "Please select a repository and package"
* Select repository "repo-1"
* Select package "P-1"
* Verify readonly configuration for package with name "P-1" and spec "go-server" is displayed
* Select repository "tw-repo"
* Select package "go-agent"
* Verify readonly configuration for package with name "go-agent" and spec "go-agent" is displayed
* Click save - AlreadyOnPackageMaterialCreationPopup

* Verify that material saved successfully
* Verify that package with name "tw-repo:go-agent" is added with url "Repository: [repo_url=http://localhost:8081/${runtime_package_http_repo_name:http_repo1}] - Package: [package_spec=go-agent]"

edit material and save with new package definition
* Edit material "tw-repo:go-agent"

* Select option "addNew" - AlreadyOnPackageMaterialCreationPopup
* Enter package name "go-server" and spec "go-server"
* Click save - AlreadyOnPackageMaterialCreationPopup

* Verify that material saved successfully
* Verify that package with name "tw-repo:go-server" is added with url "Repository: [repo_url=http://localhost:8081/${runtime_package_http_repo_name:http_repo1}] - Package: [package_spec=go-server]"

edit material and associate to another existing package
* Edit material "tw-repo:go-server"

* Verify readonly configuration for package with name "go-server" and spec "go-server" is displayed
* Select option "chooseExisting" - AlreadyOnPackageMaterialCreationPopup
* Select package "go-agent"
* Click save - AlreadyOnPackageMaterialCreationPopup

* Verify that material saved successfully
* Verify that package with name "tw-repo:go-agent" is added with url "Repository: [repo_url=http://localhost:8081/${runtime_package_http_repo_name:http_repo1}] - Package: [package_spec=go-agent]"

add new material with new package definition
* Open new package material creation popup

* Select repository "tw-repo"
* Select option "addNew" - AlreadyOnPackageMaterialCreationPopup
* Verify that message "The new package will be available to be used as material in all pipelines. Other admins might be able to edit this package." is shown - Already On package Material Creation Popup
* Enter package name "invalid?" and spec "invalid?"
* Click save - AlreadyOnPackageMaterialCreationPopup
* Verify global errors has error "Invalid Package name 'invalid?'. This must be alphanumeric and can contain underscores and periods (however, it cannot start with a period). The maximum allowed length is 255 characters."
* Enter package name "go-server.noarch" and spec "go-server.noarch"
* Click save - AlreadyOnPackageMaterialCreationPopup

* Verify that material saved successfully
* Verify that package with name "tw-repo:go-server.noarch" is added with url "Repository: [repo_url=http://localhost:8081/${runtime_package_http_repo_name:http_repo1}] - Package: [package_spec=go-server.noarch]"
* Verify that package with name "tw-repo:go-agent" is added with url "Repository: [repo_url=http://localhost:8081/${runtime_package_http_repo_name:http_repo1}] - Package: [package_spec=go-agent]"




Teardown of contexts
____________________
* Capture go state "PackageMaterialConfiguration" - teardown
* Setup http based yum repo - teardown
* Using pipeline "pipeline-for-test" - teardown
* Package configuration - teardown


