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

HgBranchAsMaterial
==================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-using-hg-default-branch,pipeline-using-hg-branch" - setup
* With "1" live agents in directory "HgBranchAsMaterial" - setup
* Capture go state "HgBranchAsMaterial" - setup

HgBranchAsMaterial
------------------

tags: #7089, Hg branch

* Looking at pipeline "pipeline-using-hg-default-branch"
* Verify stage "1" is "Passed" on pipeline with label "1"
* Looking at pipeline "pipeline-using-hg-branch"
* Verify stage "1" is "Passed" on pipeline with label "1"

* With material named "hg-material" in pipeline "pipeline-using-hg-branch"
* Commit file "new_artifact.txt" to directory "baz"

* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-using-hg-branch"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Looking at pipeline "pipeline-using-hg-default-branch"
* Verify pipeline is at label "1" and does not get triggered





Teardown of contexts
* Capture go state "HgBranchAsMaterial" - teardown
* With "1" live agents in directory "HgBranchAsMaterial" - teardown
* Using pipeline "pipeline-using-hg-default-branch,pipeline-using-hg-branch" - teardown
* Basic configuration - teardown


