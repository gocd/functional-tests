// --GO-LICENSE-START--
// Copyright 2016 ThoughtWorks, Inc.
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

PackageWithDuplicateAndMissingKeysOnConfigEdit
==============================================

Setup of contexts
* Invalid Package configuration - setup
* Using pipeline "M8,M9" - setup
* Capture go state "RPMPackageWithMDUErrors" - setup

PackageWithDuplicateAndMissingKeysOnConfigEdit
----------------------------------------------

tags: #7468, Yum repo package, plugins-tests

* Looking at pipeline "M8"
* Verify pipeline has not been triggered even once
* Looking at pipeline "M9"
* Verify pipeline has not been triggered even once

* On Admin page

* Open error and warning messages popup

* Verify error message "Modification check failed for material: Repository: [repo_url=file://${runtime_package_repo_uri:repo3}, repo_url_dup=file://${runtime_package_repo_uri:repo3}] - Package: [package_spec=go-server, package_spec_dup=go-server]" and description "Interaction with plugin with id 'yum' implementing 'package-repository' extension failed while requesting for 'latest-revision'. Reason: [The plugin sent a response that could not be understood by Go. Plugin returned with code '500' and the following response: 'Unsupported key(s) found : REPO_URL_DUP. Allowed key(s) are : REPO_URL, USERNAME, PASSWORD; Unsupported key(s) found : PACKAGE_SPEC_DUP. Allowed key(s) are : PACKAGE_SPEC']"
old message: Verify error message "Modification check failed for material: Repository: [repo_url=file://${runtime_package_repo_uri:repo3}, repo_url_dup=file://${runtime_package_repo_uri:repo3}] - Package: [package_spec=go-server, package_spec_dup=go-server]" and description "Interaction with plugin with id 'yum' implementing 'package-repository' extension failed while requesting for 'latest-revision'. Reason: [The plugin sent a response that could not be understood by Go. Plugin returned with code '500' and the following response: 'Unsupported key(s) found : REPO_URL_DUP. Allowed key(s) are : REPO_URL, USERNAME, PASSWORD; Unsupported key(s) found : PACKAGE_SPEC_DUP. Allowed key(s) are : PACKAGE_SPEC']"
* Verify error message "Modification check failed for material: Repository: [] - Package: []" and description "Interaction with plugin with id 'yum' implementing 'package-repository' extension failed while requesting for 'latest-revision'. Reason: [The plugin sent a response that could not be understood by Go. Plugin returned with code '500' and the following response: 'Repository url is empty; Package spec is empty']"

* Open "Config XML" tab

* Click edit
* Remember current tab - Already on Source Xml Tab
* Change repo config to have duplicate keys
* Click save - Already on Source Xml Tab
* Verify error message "Duplicate key 'REPO_URL' found for Repository 'repo-with-dup-and-invalid-keys'" is shown - Already on Source Xml Tab
* Click cancel
* Click edit
* Change package config to have duplicate keys
* Click save - Already on Source Xml Tab
* Verify error message "Duplicate key 'PACKAGE_SPEC' found for Package 'pkg-dup-and-invalid-keys'" is shown - Already on Source Xml Tab


Teardown of contexts
____________________
* Capture go state "RPMPackageWithMDUErrors" - teardown
* Using pipeline "M8,M9" - teardown
* Invalid Package configuration - teardown


