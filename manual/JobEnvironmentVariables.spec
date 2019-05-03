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

JobEnvironmentVariables
=======================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-with-all-materials, pipeline-with-all-materials-downstream" - setup
* With "1" live agents in directory "AgentsUIScreen" - setup
* Capture go state "JobEnvironmentVariables" - setup

JobEnvironmentVariables
-----------------------

tags: 2719, git, hg, svn, perforce, environment variables, dependency pipeline, automate, 3465, linux, perforce, #6272, stage1, buildcommand

Pipeline with all 4 materials

* Trigger pipelines "pipeline-with-all-materials" and wait for labels "1" to pass

* With material named "modifiable_git" in pipeline "pipeline-with-all-materials"
* Checkin file "file.txt" as user "user <user@foo.com>" with message "Checking in once"
* Remember current version as "git-new"
* With material named "modifiable_git" in pipeline "pipeline-with-all-materials"
* Checkin file "another_file.txt" as user "user <user@foo.com>" with message "Checking in again"
* Remember current version as "git-new1"
* With material named "name-with-hyphen" in pipeline "pipeline-with-all-materials"
* Remember current version as "name-with-hyphen-git"

* Trigger pipelines "pipeline-with-all-materials" and wait for labels "2" to pass
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Navigate to job "pipeline-with-all-materials-job"

* Open console tab
* Remember latest revision for material with destination folder "hg" as "hg-latest-revision"
* Verify console has environment variable "GO_SERVER_URL" with value "https://127.0.0.1:8254/go"
* Verify console has environment variable "GO_PIPELINE_NAME" with value "${runtime_name:pipeline-with-all-materials}"
* Verify console has environment variable "GO_PIPELINE_LABEL" with value "2"
* Verify console has environment variable "GO_STAGE_NAME" with value "defaultStage"
* Verify console has environment variable "GO_STAGE_COUNTER" with value "1"
* Verify console has environment variable "GO_JOB_NAME" with value "pipeline-with-all-materials-job"
* Verify console has environment variable "GO_REVISION_MODIFIABLE_GIT" with value of remembered revision "git-new1"
* Verify console has environment variable "GO_TO_REVISION_MODIFIABLE_GIT" with value of remembered revision "git-new1"
* Verify console has environment variable "GO_FROM_REVISION_MODIFIABLE_GIT" with value of remembered revision "git-new"
* Verify console has environment variable "GO_REVISION_NAME_WITH_HYPHEN" with value of remembered revision "name-with-hyphen-git"
* Verify console has environment variable "GO_TO_REVISION_NAME_WITH_HYPHEN" with value of remembered revision "name-with-hyphen-git"
* Verify console has environment variable "GO_FROM_REVISION_NAME_WITH_HYPHEN" with value of remembered revision "name-with-hyphen-git"
* Verify console has environment variable "GO_REVISION_HG" with value from alias "hg-latest-revision"
* Verify console has environment variable "GO_TO_REVISION_HG" with value from alias "hg-latest-revision"
* Verify console has environment variable "GO_FROM_REVISION_HG" with value from alias "hg-latest-revision"
* Verify console has environment variable "GO_REVISION_GIT" with value "300ba10905f552d0954f1a61f1857cb48e212660"
* Verify console has environment variable "GO_TO_REVISION_GIT" with value "300ba10905f552d0954f1a61f1857cb48e212660"
* Verify console has environment variable "GO_FROM_REVISION_GIT" with value "300ba10905f552d0954f1a61f1857cb48e212660"
* Verify console has environment variable "GO_REVISION_SVN" with value "3"
* Verify console has environment variable "GO_TO_REVISION_SVN" with value "3"
* Verify console has environment variable "GO_FROM_REVISION_SVN" with value "3"
* Verify console has environment variable "GO_REVISION_P4" with value "1"
* Verify console has environment variable "GO_TO_REVISION_P4" with value "1"
* Verify console has environment variable "GO_FROM_REVISION_P4" with value "1"
* Verify console has environment variable "GO_REVISION_GIT_SUBMODULE" matches revision with comment with value "inited_submodule" for material in dest folder "git-submodule"
* Verify console has environment variable "GO_TO_REVISION_GIT_SUBMODULE" matches revision with comment with value "inited_submodule" for material in dest folder "git-submodule"
* Verify console has environment variable "GO_FROM_REVISION_GIT_SUBMODULE" matches revision with comment with value "inited_submodule" for material in dest folder "git-submodule"
* Verify console has environment variable "GO_REVISION_SVN_EXTERNAL" with value "4"
* Verify console has environment variable "GO_TO_REVISION_SVN_EXTERNAL" with value "4"
* Verify console has environment variable "GO_FROM_REVISION_SVN_EXTERNAL" with value "4"
* Verify console contains "Start updating git"
* Verify console contains "Start updating svn"
* Verify console contains "Start updating hg"
* Verify console contains "Start updating p4"
* Verify console contains "BUILD SUCCESSFUL"
* Verify console contains artifact from "hg/dev/nant.build" to "hg-artifact"
* Verify console contains artifact from "git/first.txt" to "git-artifact"
* Verify console contains artifact from "svn/build.xml" to "svn-artifact"
* Verify console contains artifact from "p4/README.txt,v" to "p4-artifact"
* Verify console does not contain "setting environment variable 'CRUISE_ENVIRONMENT_NAME'"
* Verify console does not contain "setting environment variable 'GO_ENVIRONMENT_NAME'"

Pipeline with one material scm and a dependency material
* On Pipeline Dashboard Page
* Looking at pipeline "pipeline-with-all-materials-downstream"
* Trigger pipelines "pipeline-with-all-materials-downstream" and wait for labels "1" to pass
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Navigate to job "pipeline-with-all-materials-downstream-job"

* Open console tab
* Verify console has environment variable "GO_REVISION" with value from alias "hg-latest-revision"
* Verify console has environment variable "GO_TO_REVISION" with value from alias "hg-latest-revision"
* Verify console has environment variable "GO_FROM_REVISION" with value from alias "hg-latest-revision"
* Verify console has environment variable "GO_DEPENDENCY_LABEL_${runtime_name:pipeline-with-all-materials}" with value "2"
* Verify console has environment variable "GO_DEPENDENCY_LOCATOR_${runtime_name:pipeline-with-all-materials}" with value "${runtime_name:pipeline-with-all-materials}/2/defaultStage/1"





Teardown of contexts
____________________
* Capture go state "JobEnvironmentVariables" - teardown
* With "1" live agents in directory "AgentsUIScreen" - teardown
* Using pipeline "pipeline-with-all-materials, pipeline-with-all-materials-downstream" - teardown
* Basic configuration - teardown


