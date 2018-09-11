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

RPMPackageWithMDUErrors
=======================

Setup of contexts
* Package configuration - setup
* Using pipeline "M1,M2,M3,M4,M5,M6,M7" - setup
* Setup file system based yum repos "repo1:go-server-13.1.0-122.noarch.rpm;repo3:go-agent-13.1.0-112.noarch.rpm,go-agent-13.1.0-112.x86_64.rpm" - setup
* With "2" live agents in directory "RPMPackageWithMDUErrors" - setup
* Capture go state "RPMPackageWithMDUErrors" - setup

RPMPackageWithMDUErrors
-----------------------

tags: #7468, Yum repo package, plugins-tests

* Looking at pipeline "M1"
* Verify pipeline has not been triggered even once
* Looking at pipeline "M2"
* Verify pipeline has not been triggered even once
* Looking at pipeline "M3"
* Verify pipeline has not been triggered even once
* Looking at pipeline "M4"
* Verify pipeline has not been triggered even once
* Looking at pipeline "M5"
* Verify pipeline has not been triggered even once
* Looking at pipeline "M6"
* Verify pipeline has not been triggered even once
* Looking at pipeline "M7"
* Verify pipeline has not been triggered even once

* On Admin page

* Open error and warning messages popup

* Verify error message "Modification check failed for material: Repository: [repo_url=file://${runtime_package_repo_uri:repo3}] - Package: [package_spec=go-agent+]" and description "Interaction with plugin with id 'yum' implementing 'package-repository' extension failed while requesting for 'latest-revision'. Reason: [The plugin sent a response that could not be understood by Go. Plugin returned with code '500' and the following response: 'Error while querying repository with path 'file://${runtime_package_repo_uri:repo3}' and package spec 'go-agent+'. ']"
* Verify error message "Modification check failed for material: Repository: [repo_url=file://${runtime_package_repo_uri:repo1}] - Package: [package_spec=GO-server]" and description "Interaction with plugin with id 'yum' implementing 'package-repository' extension failed while requesting for 'latest-revision'. Reason: [The plugin sent a response that could not be understood by Go. Plugin returned with code '500' and the following response: 'Error while querying repository with path 'file://${runtime_package_repo_uri:repo1}' and package spec 'GO-server'. ']"
* Verify error message "Modification check failed for material: Repository: [repo_url=file://${runtime_package_repo_uri:repo3}] - Package: [package_spec=go-agent]" and description "Interaction with plugin with id 'yum' implementing 'package-repository' extension failed while requesting for 'latest-revision'. Reason: [The plugin sent a response that could not be understood by Go. Plugin returned with code '500' and the following response: 'Given Package Spec (go-agent) resolves to more than one file on the repository: go-agent-13.1.0-112.noarch.rpm, go-agent-13.1.0-112.x86_64.rpm']"
* Verify error message "Modification check failed for material: Repository: [repo_url=file://${runtime_package_repo_uri:repo1}] - Package: [package_spec=go-server-13.1]" and description "Interaction with plugin with id 'yum' implementing 'package-repository' extension failed while requesting for 'latest-revision'. Reason: [The plugin sent a response that could not be understood by Go. Plugin returned with code '500' and the following response: 'Error while querying repository with path 'file://${runtime_package_repo_uri:repo1}' and package spec 'go-server-13.1'. ']"
* Verify error message "Modification check failed for material: Repository: [repo_url=file://${runtime_package_repo_uri:repo1}] - Package: [package_spec=1go-server]" and description "Interaction with plugin with id 'yum' implementing 'package-repository' extension failed while requesting for 'latest-revision'. Reason: [The plugin sent a response that could not be understood by Go. Plugin returned with code '500' and the following response: 'Error while querying repository with path 'file://${runtime_package_repo_uri:repo1}' and package spec '1go-server'. ']"
* Verify error message "Modification check failed for material: Repository: [repo_url=file://${runtime_package_repo_uri:repo3}] - Package: [package_spec=go-agent*]" and description "Interaction with plugin with id 'yum' implementing 'package-repository' extension failed while requesting for 'latest-revision'. Reason: [The plugin sent a response that could not be understood by Go. Plugin returned with code '500' and the following response: 'Given Package Spec (go-agent*) resolves to more than one file on the repository: go-agent-13.1.0-112.noarch.rpm, go-agent-13.1.0-112.x86_64.rpm']"
* Verify error message "Modification check failed for material: Repository: [repo_url=file://${runtime_package_repo_uri:repo3}] - Package: [package_spec=go-agent?]" and description "Interaction with plugin with id 'yum' implementing 'package-repository' extension failed while requesting for 'latest-revision'. Reason: [The plugin sent a response that could not be understood by Go. Plugin returned with code '500' and the following response: 'Error while querying repository with path 'file://${runtime_package_repo_uri:repo3}' and package spec 'go-agent?'. ']"
* Verify error message "Modification check failed for material: Repository: [repo_url=file://${runtime_package_repo_uri:repo3}] - Package: [package_spec=go-agent-13.1.0.noarch]" and description "Interaction with plugin with id 'yum' implementing 'package-repository' extension failed while requesting for 'latest-revision'. Reason: [The plugin sent a response that could not be understood by Go. Plugin returned with code '500' and the following response: 'Error while querying repository with path 'file://${runtime_package_repo_uri:repo3}' and package spec 'go-agent-13.1.0.noarch'. ']"


* Publish artifacts "GO-server-13.2.0-125.noarch.rpm" to "repo1"

* On Pipeline Dashboard Page
* Looking at pipeline "M7"
* Verify stage "1" is "Passed" on pipeline with label "1"




Teardown of contexts
____________________
* Capture go state "RPMPackageWithMDUErrors" - teardown
* With "2" live agents in directory "RPMPackageWithMDUErrors" - teardown
* Setup file system based yum repos "repo1:go-server-13.1.0-122.noarch.rpm;repo3:go-agent-13.1.0-112.noarch.rpm,go-agent-13.1.0-112.x86_64.rpm" - teardown
* Using pipeline "M1,M2,M3,M4,M5,M6,M7" - teardown
* Package configuration - teardown


