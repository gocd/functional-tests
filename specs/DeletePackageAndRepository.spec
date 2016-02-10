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

DeletePackageAndRepository
==========================

Setup of contexts
* Delete package configuration - setup
* Login as "admin" - setup
* Using pipeline "one,two" - setup
* Capture go state "DeletePackage" - setup

DeletePackageAndRepository
--------------------------

tags: #7474, plugins-tests, Permissions, #7644

* Open "Package Repositories" tab

* Expand repository listing for "repo"
* Verify repository "repo" is shown with delete icon disabled
* Verify package "used" is shown with delete icon disabled
* Verify package "another-unused" is shown with delete icon enabled
* Verify package "unused" is shown with delete icon enabled
* Verify repository "empty-repo" is shown with delete icon enabled
* Click on package "used" in repository listing

Super-admin sees all pipelines as links
* Verify package with name "used" and spec "used" is shown
* Verify delete button is "disabled"
* Click on "Show pipelines using this package" link
* Verify message "Pipelines currently associated with this package" is shown in the heading
* Verify pipeline "one" as link with group "first" on row "1"
* Verify pipeline "two" as link with group "second" on row "2"
* Click on pipeline "two" on row "2"

* Verify pipeline name is "two"
* Verify that package with name "repo:used" is added with url "Repository: [repo_url=file://tmp] - Package: [package_spec=used]"

* Logout - On Any Page

* Login as "group1Admin"

* On Admin page
* Open "Package Repositories" tab

* Expand repository listing for "repo"
* Verify package "used" is shown with delete icon disabled
* Verify tooltip message "This package is being used in one or more pipeline(s), cannot delete the package" when package "used" cannot be deleted from tree view
* Verify package "unused" is shown with delete icon enabled
* Verify tooltip message "Delete this package." when package "unused" can be deleted from tree view
* Verify package "another-unused" is shown with delete icon enabled
* Click on package "used" in repository listing

* Verify package with name "used" and spec "used" is shown
* Verify delete button is "disabled"
* Verify tooltip message "This package is being used in one or more pipeline(s), cannot delete the package" when package cannot be deleted
* Click on "Show pipelines using this package" link
* Verify message "Pipelines currently associated with this package" is shown in the heading
* Verify pipeline "one" as link with group "first" on row "1"
* Verify pipeline "two" with group "second" on row "2"
* Click on pipeline "one" on row "1"

* Verify pipeline name is "one"
* Verify that package with name "repo:used" is added with url "Repository: [repo_url=file://tmp] - Package: [package_spec=used]"

* On Admin page
* Open "Package Repositories" tab

* Delete repository "empty-repo" from tree
* Verify repository "empty-repo" is not shown in the tree
* Expand repository listing for "repo"
* Delete package "unused" from tree
* Verify package "unused" is not shown in the tree
* Click on package "another-unused" in repository listing

* Verify message "No Pipelines currently use this package" is shown
* Verify tooltip message "Delete this package." when package can be deleted
* Delete package "another-unused"

* On Admin page
* Open "Package Repositories" tab

* Expand repository listing for "repo"
* Verify package "another-unused" is not shown in the tree




Teardown of contexts
____________________
* Capture go state "DeletePackage" - teardown
* Using pipeline "one,two" - teardown
* Login as "admin" - teardown
* Delete package configuration - teardown


