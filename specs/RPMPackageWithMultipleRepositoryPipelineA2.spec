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

RPMPackageWithMultipleRepositoryPipelineA2
==========================================

Setup of contexts 
* Package configuration - setup
* Using pipeline "A2" - setup
* Setup file system based yum repos "repo1;repo2" - setup
* With "1" live agents in directory "RPMPackageWithMultipleRepositories" - setup
* Capture go state "RPMPackageWithMultipleRepositories" - setup

RPMPackageWithMultipleRepositoryPipelineA2
------------------------------------------

tags: #7467, Yum repo package, plugins-tests

* Looking at pipeline "A2"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Verify pipeline "A2" is triggered by "changes"


* Looking at pipeline "A2"
* Open changes section for counter "1"

* verify material of type "Package" name "repo-1:P-2" for pipeline "A2" with counter "1" and modification "0"  has changed revision "go-server-13.1.0-122.noarch"
* verify material of type "Package" name "repo-2:Q-2" for pipeline "A2" with counter "1" and modification "0"  has changed revision "go-agent-13.1.0-16714.noarch"

* Navigate to materials for "A2" "1" "defaultStage" "1"

* Looking at material of type "Package" named "repo-1:P-2"
* Verify modification "0" has revision "go-server-13.1.0-122.noarch" - Already On Build Cause Section
* Verify modification "0" is checked in by "anonymous" with comment "Trackback: Not Provided"
* Verify material has changed - Already On Build Cause Section
* Looking at material of type "Package" named "repo-2:Q-2"
* Verify modification "0" has revision "go-agent-13.1.0-16714.noarch" - Already On Build Cause Section
* Verify modification "0" is checked in by "anonymous" with comment "Trackback: Not Provided"
* Verify material has changed - Already On Build Cause Section

* On Pipeline Dashboard Page
* Looking at pipeline "A2"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Verify commit "go-server-13.1.0-122.noarch" is shown with user "anonymous" and comment "Trackback: Not Provided" for package material "repo-1:P-2"
* Verify commit "go-agent-13.1.0-16714.noarch" is shown with user "anonymous" and comment "Trackback: Not Provided" for package material "repo-2:Q-2"
* Go to materials tab

* Verify commit "go-server-13.1.0-122.noarch" is shown with user "anonymous" and comment "Trackback: Not Provided" for package material "repo-1:P-2" - Already on Stage Detail Materials Tab
* Verify commit "go-agent-13.1.0-16714.noarch" is shown with user "anonymous" and comment "Trackback: Not Provided" for package material "repo-2:Q-2" - Already on Stage Detail Materials Tab

* On Pipeline Dashboard Page
* Looking at pipeline "A2"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "defaultJob"

* Verify material tab contains revision for package materials "repo-1:P-2=go-server-13.1.0-122.noarch,repo-2:Q-2=go-agent-13.1.0-16714.noarch,modifiable_git=Added rake file and related task"

* On Admin page
* Open "Templates" tab

* Edit template "A-series"

* Open stages tab

* Open stage "defaultStage"

* Select stage type as "manual"
* Click save - Already on Edit Stage Page
* Verify that stage saved successfully - Already on edit stage page

* Publish artifacts "go-server-13.1.0-123.noarch.rpm" to "repo1"

* On Pipeline Dashboard Page
* Trigger pipelines "A2" and wait for labels "2" to pass
* Looking at pipeline "A2"
* Verify stage "1" is "Passed" on pipeline with label "2"

* On Pipeline Dashboard Page
* Looking at pipeline "A2"
* Open changes section for counter "2"

* verify material of type "Package" name "repo-1:P-2" for pipeline "A2" with counter "2" and modification "0"  has changed revision "go-server-13.1.0-123.noarch"
* verify material of type "Package" with name "repo-2:Q-2" for pipeline "A2" with counter "2" and modification "0" has unchanged revision "go-agent-13.1.0-16714.noarch"

* Looking at material of type "Package" named "repo-1:P-2" for pipeline "A2" with counter "2"
* Verify modification "0" has revision "go-server-13.1.0-123.noarch"
* Verify material has changed
* Looking at material of type "Package" named "repo-2:Q-2" for pipeline "A2" with counter "2"
* Verify modification "0" has revision "go-agent-13.1.0-16714.noarch"
* Verify material has not changed

* On Pipeline Dashboard Page
* Navigate to materials for "A2" "2" "defaultStage" "1"


* Looking at material of type "Package" named "repo-1:P-2"
* Verify modification "0" has revision "go-server-13.1.0-123.noarch" - Already On Build Cause Section
* Verify modification "0" is checked in by "anonymous" with comment "Trackback: Not Provided"
* Verify material has changed - Already On Build Cause Section
* Looking at material of type "Package" named "repo-2:Q-2"
* Verify modification "0" has revision "go-agent-13.1.0-16714.noarch" - Already On Build Cause Section
* Verify modification "0" is checked in by "anonymous" with comment "Trackback: Not Provided"
* Verify material has not changed - Already On Build Cause Section

* On Pipeline Dashboard Page
* Looking at pipeline "A2"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Verify commit "go-server-13.1.0-123.noarch" is shown with user "anonymous" and comment "Trackback: Not Provided" for package material "repo-1:P-2"
* Verify commit "go-agent-13.1.0-16714.noarch" is shown with user "anonymous" and comment "Trackback: Not Provided" for package material "repo-2:Q-2"
* Go to materials tab

* Verify commit "go-server-13.1.0-123.noarch" is shown with user "anonymous" and comment "Trackback: Not Provided" for package material "repo-1:P-2" - Already on Stage Detail Materials Tab
* Verify commit "go-agent-13.1.0-16714.noarch" is shown with user "anonymous" and comment "Trackback: Not Provided" for package material "repo-2:Q-2" - Already on Stage Detail Materials Tab

* On Pipeline Dashboard Page
* Looking at pipeline "A2"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Navigate to stage "defaultStage" of run "2"

* Navigate to job "defaultJob"

* Verify material tab contains revision for package materials "repo-1:P-2=go-server-13.1.0-123.noarch,repo-2:Q-2=go-agent-13.1.0-16714.noarch,modifiable_git=Added rake file and related task"

* Publish artifacts "go-server-12.4.0-1234.noarch.rpm" to "repo1"

* On Pipeline Dashboard Page
* Trigger pipelines "A2" and wait for labels "3" to pass
* Looking at pipeline "A2"
* Verify stage "1" is "Passed" on pipeline with label "3"

* Looking at pipeline "A2"
* Open changes section for counter "3"

* verify material of type "Package" with name "repo-1:P-2" for pipeline "A2" with counter "3" and modification "0" has unchanged revision "go-server-13.1.0-123.noarch"
* verify material of type "Package" with name "repo-2:Q-2" for pipeline "A2" with counter "3" and modification "0" has unchanged revision "go-agent-13.1.0-16714.noarch"

* Publish artifacts "go-server-13.2.0-124.noarch.rpm" to "repo1"

* On Pipeline Dashboard Page
* Trigger pipelines "A2" and wait for labels "4" to pass
* Looking at pipeline "A2"
* Verify stage "1" is "Passed" on pipeline with label "4"

* On Pipeline Dashboard Page
* Looking at pipeline "A2"
* Open changes section for counter "4"

* verify material of type "Package" with name "repo-1:P-2" for pipeline "A2" with counter "4" and modification "0" has unchanged revision "go-server-13.1.0-123.noarch"
* verify material of type "Package" with name "repo-2:Q-2" for pipeline "A2" with counter "4" and modification "0" has unchanged revision "go-agent-13.1.0-16714.noarch"

* Publish artifacts "go-agent-13.1.0-113.x86.rpm" to "repo2"

* On Pipeline Dashboard Page
* Trigger pipelines "A2" and wait for labels "5" to pass
* Looking at pipeline "A2"
* Verify stage "1" is "Passed" on pipeline with label "5"

* On Pipeline Dashboard Page
* Looking at pipeline "A2"
* Open changes section for counter "5"

* verify material of type "Package" with name "repo-1:P-2" for pipeline "A2" with counter "5" and modification "0" has unchanged revision "go-server-13.1.0-123.noarch"
* verify material of type "Package" with name "repo-2:Q-2" for pipeline "A2" with counter "5" and modification "0" has unchanged revision "go-agent-13.1.0-16714.noarch"

* Publish artifacts "go-agent-13.1.0-16715.noarch.rpm,go-agent-13.1.0-5412.noarch.rpm" to "repo2"

* On Pipeline Dashboard Page
* Trigger pipelines "A2" and wait for labels "6" to pass
* Looking at pipeline "A2"
* Verify stage "1" is "Passed" on pipeline with label "6"

* Looking at pipeline "A2"
* Open changes section for counter "6"

* verify material of type "Package" with name "repo-1:P-2" for pipeline "A2" with counter "6" and modification "0" has unchanged revision "go-server-13.1.0-123.noarch"
* verify material of type "Package" name "repo-2:Q-2" for pipeline "A2" with counter "6" and modification "0"  has changed revision "go-agent-13.1.0-16715.noarch"

* With material "modifiable_git" for pipeline "A2"
* Checkin file "new-file-2" as user "go <go@po.com>" with message "Added new-file 2"
* Remember current version as "git-new-one"

* On Pipeline Dashboard Page
* Trigger pipelines "A2" and wait for labels "7" to pass
* Looking at pipeline "A2"
* Verify stage "1" is "Passed" on pipeline with label "7"
* Looking at pipeline "A2"
* Open changes section for counter "7"

* verify material of type "Package" with name "repo-1:P-2" for pipeline "A2" with counter "7" and modification "0" has unchanged revision "go-server-13.1.0-123.noarch"
* verify material of type "Package" with name "repo-2:Q-2" for pipeline "A2" with counter "7" and modification "0" has unchanged revision "go-agent-13.1.0-16715.noarch"
* Looking at material of type "Git" named "modifiable_git" for pipeline "A2" with counter "7"
* Verify modification "0" has revision "git-new-one"
* Verify material has changed






Teardown of contexts 
* Capture go state "RPMPackageWithMultipleRepositories" - teardown
* With "1" live agents in directory "RPMPackageWithMultipleRepositories" - teardown
* Setup file system based yum repos "repo1;repo2" - teardown
* Using pipeline "A2" - teardown
* Package configuration - teardown


