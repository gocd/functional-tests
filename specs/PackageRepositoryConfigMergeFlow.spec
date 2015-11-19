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

PackageRepositoryConfigMergeFlow
================================

Setup of contexts
* Setup http based yum repo - setup
* Package configuration - setup
* Capture go state "PackageRepositoryConfigMergeFlow" - setup

PackageRepositoryConfigMergeFlow
--------------------------------

tags: clicky ui, admin-page, configuration, plugins-tests, #7767, config-merge

Successful Merge
* Open "Package Repositories" tab

* Click on "tw-repo" in repository listing
* Enter username as "user_1"

* Update value of key "USERNAME" to "user_2" for repo with id "repo-id-2"

* Click save - Already on Package Repositories tab
* Verify message shows up "The configuration was modified by someone else, but your changes were merged successfully."

Merge Conflict

* Click on "tw-repo" in repository listing
* Enter username as "user_3"

* Update value of key "USERNAME" to "user_2" for repo with id "repo-id"

* Click save - Already on Package Repositories tab
* Verify message shows up "Save failed. Configuration file has been modified by someone else.RELOADThis will refresh the page and you will lose your changes on this page."
* Click save - Already on Package Repositories tab
* Verify message shows up "Save failed. Configuration file has been modified by someone else.RELOADThis will refresh the page and you will lose your changes on this page."





Teardown of contexts
* Capture go state "PackageRepositoryConfigMergeFlow" - teardown
* Package configuration - teardown
* Setup http based yum repo - teardown


