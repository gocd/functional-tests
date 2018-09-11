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

PackagerInformationOnDifferentScreens
=====================================

Setup of contexts
* Package configuration - setup
* Using pipeline "K1,K2" - setup
* Setup file system based yum repos "repo3" - setup
* With "1" live agents in directory "PackagerInformationOnDifferentScreens" - setup
* Capture go state "PackagerInformationOnDifferentScreens" - setup

PackagerInformationOnDifferentScreens
-------------------------------------

tags: #7808, plugins-tests

* Trigger pipelines "K1" and wait for labels "1" to pass
* Looking at pipeline "K1"
* Open changes section for counter "1"

* Looking at material of type "Package" named "repo-3:CP-2" for pipeline "K1" with counter "1"
* Verify modification "0" has revision "go-agent-13.1.0-112.noarch"
* Verify modification "0" has comment containing "Built on go-qa3"

* On Pipeline Dashboard Page
* Looking at pipeline "K1"
* Compare pipeline instance "1" with "1"

* Verify displays package with uri "file://${runtime_package_repo_uri:repo3}" having spec "go-agent" with revision "go-agent-13.1.0-112.noarch" published by "anonymous"

* On Pipeline Dashboard Page
* Looking at pipeline "K1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Verify commit "go-agent-13.1.0-112.noarch" is shown with user "anonymous" and comment "Trackback: Not Provided" for package material "repo-3:CP-2"
* Go to materials tab

* Verify commit "go-agent-13.1.0-112.noarch" is shown with user "anonymous" and comment "Trackback: Not Provided" for package material "repo-3:CP-2" - Already on Stage Detail Materials Tab

* On Pipeline Dashboard Page
* Looking at pipeline "K1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Navigate to job "defaultJob"

* Verify material tab contains revision for package materials "repo-3:CP-2=go-agent-13.1.0-112.noarch"

* On Pipeline Dashboard Page
* Trigger pipelines "K2" and wait for labels "1" to pass
* Looking at pipeline "K2"
* Open changes section for counter "1"

* Looking at material of type "Package" named "repo-3:CP-1" for pipeline "K2" with counter "1"
* Verify modification "0" has revision "go-server-12.4.0-1234.noarch"
* Verify modification "0" has comment containing "Built on go-qa3 Trackback: Not Provided"
* Verify modification "0" has modified by containing "Thoughtworks Studios <twstudios@thoughtworks.com>"

* On Pipeline Dashboard Page
* Looking at pipeline "K2"
* Compare pipeline instance "1" with "1"

* Verify displays package with uri "file://${runtime_package_repo_uri:repo3}" having spec "go-server" with revision "go-server-12.4.0-1234.noarch" published by "Thoughtworks Studios <twstudios@thoughtworks.com>"

* On Pipeline Dashboard Page
* Looking at pipeline "K2"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Verify commit "go-server-12.4.0-1234.noarch" is shown with user "Thoughtworks Studios <twstudios@thoughtworks.com>" and comment "Trackback: Not Provided" for package material "repo-3:CP-1"
* Go to materials tab

* Verify commit "go-server-12.4.0-1234.noarch" is shown with user "Thoughtworks Studios <twstudios@thoughtworks.com>" and comment "Trackback: Not Provided" for package material "repo-3:CP-1" - Already on Stage Detail Materials Tab

* On Pipeline Dashboard Page
* Looking at pipeline "K2"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Navigate to job "defaultJob"

* Verify material tab contains revision for package materials "repo-3:CP-1=go-server-12.4.0-1234.noarch"





Teardown of contexts
____________________
* Capture go state "PackagerInformationOnDifferentScreens" - teardown
* With "1" live agents in directory "PackagerInformationOnDifferentScreens" - teardown
* Setup file system based yum repos "repo3" - teardown
* Using pipeline "K1,K2" - teardown
* Package configuration - teardown


