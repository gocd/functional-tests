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

PipelineDependency
==================

Setup of contexts
* Basic configuration - setup
* Using pipeline "first, second, third, last" - setup
* With "2" live agents in directory "StageDetails" - setup
* Capture go state "PipelineDependency" - setup

PipelineDependency
------------------

tags: dependency pipeline, 3695, automate, stage1

Get to a sane state

* Trigger pipelines "first" and wait for labels "1" to pass
* Trigger pipelines "second" and wait for labels "1" to pass
* Trigger pipelines "third" and wait for labels "1" to pass
* Looking at pipeline "last"
* Verify stage "1" is "Passed" on pipeline with label "1"

Case of SCM with no hop

* With material "svn-third" for pipeline "third"
* Checkin file "svn1.ignore" as user "twist" with message "Added svn1.ignore"
* Remember current version as "svn1"
* Checkin file "svn2.ignore" as user "twist" with message "Added svn2.ignore"
* Remember current version as "svn2"

* For pipeline "third" - Using pipeline api
* Using remembered revision "svn1" for material "svn-third"
* Schedule should return code "202"

* On Pipeline Dashboard Page
* Looking at pipeline "third"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Looking at pipeline "last"
* Verify stage "1" is "Passed" on pipeline with label "2"

* On Pipeline Dashboard Page
* Looking at pipeline "last"
* Open changes section for counter "2"

* Looking at material of type "Subversion" named "svn-last" for pipeline "last" with counter "2"
* Verify modification "0" has comment containing "Added svn1.ignore"
* Verify modification "0" does not have comment containing "Added svn2.ignore"

* On Pipeline Dashboard Page
* Looking at pipeline "last"
* Navigate to stage "defaultStage" of run "2"

* Navigate to job "job-of-last"

* Open console tab
* Verify console shows remembered revision "svn1" for material "svn-last"
* Verify console has environment variable "GO_REVISION_SVN_LAST" with value of remembered revision "svn1"

Case of Dependency material without hop

* On Pipeline Dashboard Page
* Looking at pipeline "second"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "2"
* Looking at pipeline "last"
* Verify stage "1" is "Passed" on pipeline with label "3"

* Remember revision "${runtime_name:second}/1/defaultStage/1" as "dependency1"
* Remember revision "${runtime_name:second}/2/defaultStage/1" as "dependency2"

* For pipeline "third" - Using pipeline api
* Using remembered revision "dependency1" for material "${runtime_name:second}"
* Schedule should return code "202"

* On Pipeline Dashboard Page
* Looking at pipeline "third"
* Verify stage "1" is "Passed" on pipeline with label "3"
* Looking at pipeline "last"
* Verify stage "1" is "Passed" on pipeline with label "4"

* Navigate to the pipeline history page for pipeline "last"

* Looking at pipeline with label "4"
* Verify build cause message on row "1" contains "${runtime_name:second}/2/defaultStage/1" and not "${runtime_name:second}/1/defaultStage/1"

//TODO: Add the job console verification here

Case of SCM with hop

* With material "hg-first" for pipeline "first"
* Checkin file "hg1.ignore" as user "twist" with message "Added hg1.ignore"
* Remember current version as "hg1"
* Checkin file "hg2.ignore" as user "twist" with message "Added hg2.ignore"
* Remember current version as "hg2"

* For pipeline "first" - Using pipeline api
* Using remembered revision "hg1" for material "hg-first"
* Schedule should return code "202"

* On Pipeline Dashboard Page
* Looking at pipeline "first"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Looking at pipeline "third"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "4"
* Looking at pipeline "last"
* Verify stage "1" is "Passed" on pipeline with label "5"

* On Pipeline Dashboard Page
* Looking at pipeline "last"
* Open changes section for counter "5"

* Looking at material of type "Mercurial" named "hg-last" for pipeline "last" with counter "5"
* Verify modification "0" has comment containing "Added hg1.ignore"
* Verify modification "0" does not have comment containing "Added hg2.ignore"

* On Pipeline Dashboard Page
* Looking at pipeline "last"
* Navigate to stage "defaultStage" of run "5"

* Navigate to job "job-of-last"

* Open console tab
* Verify console shows remembered revision "hg1" for material "hg-last"
* Verify console has environment variable "GO_REVISION_HG_LAST" with value of remembered revision "hg1"





Teardown of contexts
____________________
* Capture go state "PipelineDependency" - teardown
* With "2" live agents in directory "StageDetails" - teardown
* Using pipeline "first, second, third, last" - teardown
* Basic configuration - teardown


