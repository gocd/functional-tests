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

MaterialSupport
===============

|material name|Pipeline|jobName|First Updating message|Secong Updating Message|Material Tab|modified by|First Revision|Second Revision|Third Revision|First modified by|material type|
|p4|p4-pipeline|defaultJob|Start updating p4|Start updating p4 at revision 5|Revision: 5|modified by cceuser@${p4_view}|4|5|6|Revision: 4, modified by cceuser|Perforce|
|svn|basic-svn-pipeline|basic-svn-pipeline-job|Start updating svn|Start updating svn at revision 4|Revision: 4|modified by cceuser|3|4|5|Revision: 3, modified by cceuser|Subversion|
Setup of contexts 
* Basic configuration - setup
* Using pipeline "p4-pipeline,basic-svn-pipeline" - setup
* With "1" live agents in directory "AgentsUIScreen" - setup
* Capture go state "MaterialSupport" - setup

MaterialSupport
---------------

tags: perforce, automate


* Trigger pipelines <Pipeline> and wait for labels "1" to pass
* Open changes section for counter "1"

* Looking at material of type <material type> named <material name> for pipeline <Pipeline> with counter "1"
* Verify revision <First Revision> exists

* On Pipeline Dashboard Page
* Navigate to stage "defaultStage" of run "1"

* Navigate to job <jobName>

* Open console tab
* Verify material tab contains revision <First modified by>
* Verify console contains <First Updating message>

* Looking at first agent for pipeline <Pipeline>
* Verify <material name> is checked out under the pipelines folder

* With material named <material name> in pipeline <Pipeline>
* Checkin file "foo.txt" as user "cceuser" with message "Comment"

* On Pipeline Dashboard Page
* Trigger pipelines <Pipeline> and wait for labels "2" to pass
* Open changes section for counter "2"

* Looking at material of type <material type> named <material name> for pipeline <Pipeline> with counter "2"
* Verify revision <Second Revision> exists

* On Pipeline Dashboard Page
* Navigate to stage "defaultStage" of run "2"

* Navigate to job <jobName>

* Open console tab
* Verify console contains <Secong Updating Message>
* Verify material tab contains revision <Material Tab>

Check autoscheduling svn pipeline

* With material named <material name> in pipeline <Pipeline>
* Checkin file "foo1.txt" as user "cceuser" with message "Comment"

* Setting first stage of <Pipeline> to auto approval

* On Pipeline Dashboard Page
* Trigger pipelines <Pipeline> and wait for labels "3" to pass
* Open changes section for counter "3"

* Looking at material of type <material type> named <material name> for pipeline <Pipeline> with counter "3"
* Verify revision <Third Revision> exists





Teardown of contexts
____________________
* Capture go state "MaterialSupport" - teardown
* With "1" live agents in directory "AgentsUIScreen" - teardown
* Using pipeline "p4-pipeline,basic-svn-pipeline" - teardown
* Basic configuration - teardown


