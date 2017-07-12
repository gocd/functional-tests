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

SvnPipeline
===========

Setup of contexts
* Basic configuration - setup
* Using pipeline "svn-pipeline, svn-pipeline-with-external" - setup
* With "1" live agents in directory "AgentsUIScreen" - setup
* Capture go state "SvnPipeline" - setup

SvnPipeline
-----------

tags: 1995, 2126, svn-support, long, not-windows, automate, buildcommand

Modify material definition and make sure the pipeline gets scheduled

* Setting first stage of "svn-pipeline" to auto approval

* Looking at pipeline "svn-pipeline"
* Wait for first stage to pass with pipeline label "1"

* Removing "hg" material from "svn-pipeline"

* On Pipeline Dashboard Page
* Looking at pipeline "svn-pipeline"
* Wait for first stage to pass with pipeline label "2"

* Looking at first agent for pipeline "svn-pipeline"
* Verify code for "hg" material is removed

* Changing destination of "svn" material of "svn-pipeline" to "changed"

* On Pipeline Dashboard Page
* Looking at pipeline "svn-pipeline"
* Wait for first stage to pass with pipeline label "3"

* Looking at first agent for pipeline "svn-pipeline"
* Verify code for "svn" material is removed
* Verify "changed" is checked out under the pipelines folder

SVN External tests

* On Pipeline Dashboard Page
* Looking at pipeline "svn-pipeline-with-external"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"

* With external material of "svn-external" for pipeline "svn-pipeline-with-external"
* Checkin file "foo.txt" as user "twist" with message "Added foo.txt"

* Setting first stage of "svn-pipeline-with-external" to auto approval

* On Pipeline Dashboard Page
* Looking at pipeline "svn-pipeline-with-external"
* Wait for first stage to pass with pipeline label "2"
* Navigate to stage "defaultStage" of run "2"

* Navigate to job "svn-pipeline-with-external-job"

* Open console tab
* Verify console contains "Start updating svn-external/external at revision 7"

* With material named "svn-external" in pipeline "svn-pipeline-with-external"
* Checkin file "foo.txt" as user "twist" with message "Added foo.txt"

* On Pipeline Dashboard Page
* Looking at pipeline "svn-pipeline-with-external"
* Wait for first stage to pass with pipeline label "3"

* Removing externals for "svn-external" of pipeline "svn-pipeline-with-external"

* With external material of "svn-external" for pipeline "svn-pipeline-with-external"
* Checkin file "something.txt" as user "twist" with message "Added something.txt"

* On Pipeline Dashboard Page
* Looking at pipeline "svn-pipeline-with-external"
* Trigger pipeline
* Wait for first stage to pass with pipeline label "4"
* Navigate to stage "defaultStage" of run "4"

* Navigate to job "svn-pipeline-with-external-job"

* Open console tab
* Verify console does not contain "Start updating svn-external/external"




Teardown of contexts
____________________
* Capture go state "SvnPipeline" - teardown
* With "1" live agents in directory "AgentsUIScreen" - teardown
* Using pipeline "svn-pipeline, svn-pipeline-with-external" - teardown
* Basic configuration - teardown


