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

GitPipeline
===========

Setup of contexts
* Basic configuration - setup
* Using pipeline "git-pipeline" - setup
* With "1" live agents in directory "git_pipeline" - setup
* Capture go state "GitPipeline" - setup

GitPipeline
-----------

tags: git, automate, buildcommand

* With external material of "git-submodule" for pipeline "git-pipeline"
* Checkin file "new_file" as user "twist <twist@thoughtworks.com>" with message "Added new_file"
* With material "git-submodule" for pipeline "git-pipeline"
* Checkin git submodule reference

* Setting first stage to auto approval

* Looking at pipeline "git-pipeline"
* Wait for stage "defaultStage" status to be "Building" with label "1"
* Navigate to stage "defaultStage" of run "1"

* Turn on auto refresh
* Wait for jobs to show "In Progress: 1" with jobs "defaultJob"

* On Pipeline Dashboard Page
* Stop "1" jobs waiting for file to exist - On Pipeline Dashboard Page
* Navigate to stage "defaultStage" of run "1"

* Turn on auto refresh
* Wait for jobs to show "Passed: 1" with jobs "defaultJob"

* On Pipeline Dashboard Page
* Open changes section for counter "1"

* Looking at material of type "Git" named "git-submodule" for pipeline "git-pipeline" with counter "1"
* Verify modification "0" has comment containing "Updated submodule"
* Verify material has changed

* On Pipeline Dashboard Page
* Looking at pipeline "git-pipeline"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "defaultJob"

* Open console tab
* Verify console contains submodule status for dir "referenced-submodule" and ref "heads/master"

* With material "git-submodule" for pipeline "git-pipeline"
* Checkin file "new_file_2" as user "foo <foo@bar.com>" with message "This is a one line description.\n\nThis should be on a new paragraph"

* On Pipeline Dashboard Page
* Looking at pipeline "git-pipeline"
* Wait for stage "defaultStage" status to be "Building" with label "2"
* Navigate to stage "defaultStage" of run "2"

* Turn on auto refresh
* Wait for jobs to show "In Progress: 1" with jobs "defaultJob"

* On Pipeline Dashboard Page
* Looking at pipeline "git-pipeline"
* Stop "1" jobs waiting for file to exist - On Pipeline Dashboard Page
* Navigate to stage "defaultStage" of run "2"

* Turn on auto refresh
* Wait for jobs to show "Passed: 1" with jobs "defaultJob"

* On Pipeline Dashboard Page
* Looking at pipeline "git-pipeline"
* Open changes section for counter "2"





Teardown of contexts
____________________
* Capture go state "GitPipeline" - teardown
* With "1" live agents in directory "git_pipeline" - teardown
* Using pipeline "git-pipeline" - teardown
* Basic configuration - teardown


