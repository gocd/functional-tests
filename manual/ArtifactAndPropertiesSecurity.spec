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

ArtifactAndPropertiesSecurity
=============================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "viewable-pipeline, 2-stage-viewable" - setup
* With "1" live agents in directory "pipeline_security" - setup
* Capture go state "ArtifactAndPropertiesSecurity" - setup

ArtifactAndPropertiesSecurity
-----------------------------

tags: #2400, #2786, security, authorisation, authentication, #2514, automate, 6786

Admin can add/edit artifacts and add/view job properties

* Looking at pipeline "viewable-pipeline"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Looking at pipeline "2-stage-viewable"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"

* Logged in as "admin"

* For pipeline "viewable-pipeline" label "1" stage "defaultStage" counter "1" job "defaultJob"
* Create artifact "new_artifact.txt"
* Verify return code is "201" - Using Artifact Api

* Looking at pipeline "viewable-pipeline"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Navigate to job "defaultJob"

* Verify artifacts tab contains file "new_artifact.txt"

* For pipeline "viewable-pipeline" label "1" stage "defaultStage" counter "1" job "defaultJob"
* Append "foo bar baz" to artifact "new_artifact.txt"
* Verify return code is "200" - Using Artifact Api

* Verify artifacts tab contains file "new_artifact.txt"
* Verify artifact "new_artifact.txt" contains text "foo bar baz"

* verify property "foo" with value "bar" for pipeline "viewable-pipeline" stage "defaultStage" label "1" counter "1" job "defaultJob" can be added


Operate user can add/edit artifacts and view/add properties using api
* Logged in as "operate"

* For pipeline "viewable-pipeline" label "1" stage "defaultStage" counter "1" job "defaultJob"
* Append "new text" to artifact "new_artifact.txt"
* Verify return code is "200" - Using Artifact Api

* verify property "prop_name_by_operate" with value "prop_value_by_operate" for pipeline "viewable-pipeline" stage "defaultStage" label "1" counter "1" job "defaultJob" can be added

View user cannot add/edit artifacts and add properties using api

* Logged in as "view"

* For pipeline "viewable-pipeline" label "1" stage "defaultStage" counter "1" job "defaultJob"
* Create artifact "another_artifact.txt"
* Verify return code is "403" - Using Artifact Api

* Looking at pipeline "viewable-pipeline"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Navigate to job "defaultJob"

* Verify artifacts tab does not contain file "another_artifact.txt"

* For pipeline "viewable-pipeline" label "1" stage "defaultStage" counter "1" job "defaultJob"
* Append "foo bar baz" to artifact "another_artifact.txt"
* Verify return code is "403" - Using Artifact Api

* Verify artifacts tab does not contain file "another_artifact.txt"

* verify property "quux" with value "baz" for pipeline "viewable-pipeline" stage "defaultStage" label "1" counter "1" job "defaultJob" cannot be added

View user cannot add properties using UI

* Logout and login as "view"

* Looking at pipeline "viewable-pipeline"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Navigate to job "defaultJob"







Teardown of contexts
____________________
* Capture go state "ArtifactAndPropertiesSecurity" - teardown
* With "1" live agents in directory "pipeline_security" - teardown
* Using pipeline "viewable-pipeline, 2-stage-viewable" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


