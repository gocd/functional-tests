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

SeeWhatActuallyChangedSinceTheLastPipelineRun
=============================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "svn-pipeline" - setup
* With "1" live agents in directory "SeeWhatActuallyChangedSinceLastPipelineRun" - setup
* Capture go state "SeeWhatActuallyChangedSinceTheLastPipelineRun" - setup

SeeWhatActuallyChangedSinceTheLastPipelineRun
---------------------------------------------

tags: 2414, 3032, svn support, scheduling, dashboard, diagnostics messages, automate

* Looking at pipeline "svn-pipeline"
* Trigger pipeline "svn-pipeline"
* Wait for first stage to pass with pipeline label "1"

* With material named "svn" in pipeline "svn-pipeline"
* Checkin file "foo.txt" as user "twist" with message "Added foo.txt"

* On Pipeline Dashboard Page
* Looking at pipeline "svn-pipeline"
* Trigger pipeline "svn-pipeline"
* Wait for first stage to pass with pipeline label "2"
* Navigate to stage "defaultStage" of run "2"

* Navigate to job "svn-pipeline-job"

* Verify material tab contains revision "Revision: 4, modified by twist"
* Verify revision "Revision: 4, modified by twist" is marked as changed
* Verify latest revision for material with name "hg" and destination folder "hg" is not marked as changed

Marked as changed is verified by looking at the css class "highlight-true"




Teardown of contexts
* Capture go state "SeeWhatActuallyChangedSinceTheLastPipelineRun" - teardown
* With "1" live agents in directory "SeeWhatActuallyChangedSinceLastPipelineRun" - teardown
* Using pipeline "svn-pipeline" - teardown
* Basic configuration - teardown


