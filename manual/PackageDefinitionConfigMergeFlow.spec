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

PackageDefinitionConfigMergeFlow
================================

Setup of contexts
* Setup http based yum repo - setup
* Package configuration - setup
* Using pipeline "pipeline_with_yum_repo_package_as_material,pipeline_repo_user_has_special_password" - setup
* Capture go state "PackageDefinitionConfigMergeFlow" - setup

PackageDefinitionConfigMergeFlow
--------------------------------

tags: clicky ui, admin-page, configuration, plugins-tests, #7767, config-merge

Successful Merge
* Open "Pipelines" tab

* Edit pipeline "pipeline_with_yum_repo_package_as_material"

* Open material listing page

* Open new package material creation popup

* Add a new package definition with "new-package" and spec "new-package-spec" to repository "repo-id-2"
* Add package material "new-package" to pipeline "pipeline_repo_user_has_special_password"

* Select repository "repo-id"
* Select option "addNew" - AlreadyOnPackageMaterialCreationPopup
* Enter package name "go-server" and spec "go-server"
* Click save - AlreadyOnPackageMaterialCreationPopup
* Verify successful merge message shows up





Teardown of contexts
____________________
* Capture go state "PackageDefinitionConfigMergeFlow" - teardown
* Using pipeline "pipeline_with_yum_repo_package_as_material,pipeline_repo_user_has_special_password" - teardown
* Package configuration - teardown
* Setup http based yum repo - teardown


