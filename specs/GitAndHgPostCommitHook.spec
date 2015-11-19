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

GitAndHgPostCommitHook
======================

Setup of contexts
* Basic configuration - setup
* Using pipeline "hg-postcommithook-pipeline, another-hg-postcommithook-pipeline, git-postcommithook-pipeline, another-git-postcommithook-pipeline" - setup
* With "2" live agents in directory "StageDetails" - setup
* Capture go state "GitAndHgPostCommitHook" - setup

GitAndHgPostCommitHook
----------------------

tags: Hg-post-commit-hook, Git-post-commit-hook, #190, #774


Verify git post commit hook
* With material "git" for pipeline "git-postcommithook-pipeline"
* Checkin file "new-git-file-1" as user " <pair02@thoughtworks.com>" with message "Added new-git-file 1"
* Remember current version as "git-revision-1"
* Initiate post commit hook


* With material "git-2" for pipeline "another-git-postcommithook-pipeline"
* Checkin file "new-git-file-2" as user " <pair03@thoughtworks.com>" with message "Added new-git-file 2"
* Remember current version as "git-revision-2"
* Initiate post commit hook


* Looking at pipeline "git-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Open changes section for counter "1"


* Looking at material of type "git" named "git" for pipeline "git-postcommithook-pipeline" with counter "1"
* Verify modification "0" has revision "git-revision-1"
* Verify material has changed

* On Pipeline Dashboard Page
* Looking at pipeline "another-git-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Open changes section for counter "1"



* Looking at material of type "git" named "git-2" for pipeline "another-git-postcommithook-pipeline" with counter "1"
* Verify modification "0" has revision "git-revision-2"
* Verify material has changed
* Looking at material of type "git" named "git" for pipeline "another-git-postcommithook-pipeline" with counter "1"
* Verify modification "0" has revision "git-revision-1"
* Verify material has changed




* With material "git-2" for pipeline "another-git-postcommithook-pipeline"
* Checkin file "new-git-file-3" as user " <pair03@thoughtworks.com>" with message "Added new-git-file 3"
* Remember current version as "git-revision-3"
* Initiate post commit hook

* On Pipeline Dashboard Page
* Looking at pipeline "another-git-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"



* Looking at material of type "git" named "git-2" for pipeline "another-git-postcommithook-pipeline" with counter "2"
* Verify modification "0" has revision "git-revision-3"
* Verify material has changed
* Looking at material of type "git" named "git" for pipeline "another-git-postcommithook-pipeline" with counter "2"
* Verify modification "0" has revision "git-revision-1"
* Verify material has not changed

* On Pipeline Dashboard Page
* Looking at pipeline "git-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"



Verify hg post commit hook
* With material "hg" for pipeline "hg-postcommithook-pipeline"
* Checkin file "new-hg-file-1" as user " <pair02@thoughtworks.com>" with message "Added new-hg-file 1"
* Remember current version as "hg-revision-1"
* Initiate post commit hook


* With material "hg-2" for pipeline "another-hg-postcommithook-pipeline"
* Checkin file "new-hg-file-2" as user " <pair03@thoughtworks.com>" with message "Added new-hg-file 2"
* Remember current version as "hg-revision-2"
* Initiate post commit hook

* On Pipeline Dashboard Page
* Looking at pipeline "hg-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Open changes section for counter "1"


* Looking at material of type "hg" named "hg" for pipeline "hg-postcommithook-pipeline" with counter "1"
* Verify modification "0" has revision "hg-revision-1"
* Verify material has changed

* On Pipeline Dashboard Page
* Looking at pipeline "another-hg-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Open changes section for counter "1"



* Looking at material of type "hg" named "hg-2" for pipeline "another-hg-postcommithook-pipeline" with counter "1"
* Verify modification "0" has revision "hg-revision-2"
* Verify material has changed
* Looking at material of type "hg" named "hg" for pipeline "another-hg-postcommithook-pipeline" with counter "1"
* Verify modification "0" has revision "hg-revision-1"
* Verify material has changed




* With material "hg-2" for pipeline "another-hg-postcommithook-pipeline"
* Checkin file "new-hg-file-3" as user " <pair03@thoughtworks.com>" with message "Added new-hg-file 3"
* Remember current version as "hg-revision-3"
* Initiate post commit hook

* On Pipeline Dashboard Page
* Looking at pipeline "another-hg-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"



* Looking at material of type "hg" named "hg-2" for pipeline "another-hg-postcommithook-pipeline" with counter "2"
* Verify modification "0" has revision "hg-revision-3"
* Verify material has changed
* Looking at material of type "hg" named "hg" for pipeline "another-hg-postcommithook-pipeline" with counter "2"
* Verify modification "0" has revision "hg-revision-1"
* Verify material has not changed

* On Pipeline Dashboard Page
* Looking at pipeline "hg-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"






Teardown of contexts
* Capture go state "GitAndHgPostCommitHook" - teardown
* With "2" live agents in directory "StageDetails" - teardown
* Using pipeline "hg-postcommithook-pipeline, another-hg-postcommithook-pipeline, git-postcommithook-pipeline, another-git-postcommithook-pipeline" - teardown
* Basic configuration - teardown


