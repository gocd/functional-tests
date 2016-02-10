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

SvnPostCommitHook
=================

Setup of contexts
* Basic configuration - setup
* Using pipeline "svn-postcommithook-pipeline, another-svn-postcommithook-pipeline" - setup
* With "2" live agents in directory "StageDetails" - setup
* Capture go state "SvnPostCommitHook" - setup

SvnPostCommitHook
-----------------

tags: 6433, svn-support


* With material "svn" for pipeline "svn-postcommithook-pipeline"
* Checkin file "new-file-1" as user " <pair02@thoughtworks.com>" with message "Added new-file 1"
* Remember current version as "svn-revision-1"
* Initiate post commit hook


* Looking at pipeline "svn-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"

* Looking at pipeline "another-svn-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"


* With material "svn-2" for pipeline "another-svn-postcommithook-pipeline"
* Checkin file "new-file-2" as user " <pair03@thoughtworks.com>" with message "Added new-file 2"
* Remember current version as "svn-revision-2"
* Initiate post commit hook

* Looking at pipeline "another-svn-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"

* Looking at material of type "svn" named "svn-2" for pipeline "another-svn-postcommithook-pipeline" with counter "2"
* Verify modification "0" has revision "svn-revision-2"
* Verify material has changed
* Looking at material of type "svn" named "svn" for pipeline "another-svn-postcommithook-pipeline" with counter "2"
* Verify modification "0" has revision "svn-revision-1"
* Verify material has not changed

* On Pipeline Dashboard Page
* Looking at pipeline "svn-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "1"


* With material "svn" for pipeline "svn-postcommithook-pipeline"
* Checkin file "new-file-3" as user " <pair03@thoughtworks.com>" with message "Added new-file 3"
* Remember current version as "svn-revision-3"
* Initiate post commit hook

* Looking at pipeline "svn-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Open changes section for counter "2"


* Looking at material of type "svn" named "svn" for pipeline "svn-postcommithook-pipeline" with counter "2"
* Verify modification "0" has revision "svn-revision-3"
* Verify material has changed

* On Pipeline Dashboard Page
* Looking at pipeline "another-svn-postcommithook-pipeline"
* Verify stage "1" is "Passed" on pipeline with label "3"
* Open changes section for counter "3"


* Looking at material of type "svn" named "svn" for pipeline "another-svn-postcommithook-pipeline" with counter "3"
* Verify modification "0" has revision "svn-revision-3"
* Verify material has changed
* Looking at material of type "svn" named "svn-2" for pipeline "another-svn-postcommithook-pipeline" with counter "3"
* Verify modification "0" has revision "svn-revision-2"
* Verify material has not changed




Teardown of contexts
____________________
* Capture go state "SvnPostCommitHook" - teardown
* With "2" live agents in directory "StageDetails" - teardown
* Using pipeline "svn-postcommithook-pipeline, another-svn-postcommithook-pipeline" - teardown
* Basic configuration - teardown


