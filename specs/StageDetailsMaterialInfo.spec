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

StageDetailsMaterialInfo
========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "first,second,third" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "StageDetailsMaterialInfo" - setup

StageDetailsMaterialInfo
------------------------

tags: 3609, stage-details, automate

* With material named "hg-first" in pipeline "first"
* Checkin file "new_file.txt" as user "cceuser4" with message "Comment abc"
* Remember current version as "hg-first-latest"

* Looking at pipeline "first"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Verify commit "hg-first-latest" is shown with user "cceuser4" and comment "Comment abc" for material "hg-first" of type "Mercurial"
* Go to materials tab

* Verify commit "hg-first-latest" is shown with user "cceuser4" and comment "Comment abc" for material "hg-first" of type "Mercurial" - Already On Stage Detail Materials Tab
* Looking at "hg-first" of type "Mercurial"
* Looking at revision alias "hg-first-latest"
* Verify has "new_file.txt" marked as "added"

* On Pipeline Dashboard Page
* Looking at pipeline "second"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"

* With material named "git-second" in pipeline "second"
* Create file "first_file.txt" as "git_user <user@git.com>"
* Create file "another_file.txt" as "git_user <user@git.com>"
* Checkin as "git_user <user@git.com>" with message "git new files"
* Remember current version as "git-second-previous"
* Modify file "first_file.txt" as "another_git_user <another@git.com>"
* Delete file "another_file.txt" as "another_git_user <another@git.com>"
* Checkin as "another_git_user <another@git.com>" with message "modification to git files"
* Remember current version as "git-second-latest"

* On Pipeline Dashboard Page
* Looking at pipeline "second"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "2"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Verify commit "git-second-latest" is shown with user "another_git_user <another@git.com>" and comment "modification to git files" for material "git-second" of type "Git"
* Go to materials tab

* Verify commit "git-second-latest" is shown with user "another_git_user <another@git.com>" and comment "modification to git files" for material "git-second" of type "Git" - Already On Stage Detail Materials Tab
* Looking at "git-second" of type "Git"
* Looking at revision alias "git-second-latest"
* Verify has "first_file.txt" marked as "modified"
* Verify has "another_file.txt" marked as "deleted"
* Verify commit "git-second-previous" is shown with user "git_user <user@git.com>" and comment "git new files" for material "git-second" of type "Git" - Already On Stage Detail Materials Tab
* Looking at revision alias "git-second-previous"
* Verify has "first_file.txt" marked as "added"
* Verify has "another_file.txt" marked as "added"

* On Pipeline Dashboard Page
* Looking at pipeline "third"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Verify revision "${runtime_name:first}/1/defaultStage/1" having label "1" is shown for material "${runtime_name:first}"
* Verify revision "${runtime_name:second}/2/defaultStage/1" having label "2" is shown for material "${runtime_name:second}"
* Go to materials tab

* Looking at "${runtime_name:first}" of type "Pipeline"
* Looking at revision "${runtime_name:first}/1/defaultStage/1"
* Verify revision "${runtime_name:first}/1/defaultStage/1" having label "1" is shown for material "${runtime_name:first}" - Already On Stage Detail Materials Tab
* Verify shows completed at
* Looking at "${runtime_name:second}" of type "Pipeline"
* Looking at revision "${runtime_name:second}/2/defaultStage/1"
* Verify revision "${runtime_name:second}/2/defaultStage/1" having label "2" is shown for material "${runtime_name:second}" - Already On Stage Detail Materials Tab
* Verify shows completed at





Teardown of contexts
* Capture go state "StageDetailsMaterialInfo" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "first,second,third" - teardown
* Basic configuration - teardown


