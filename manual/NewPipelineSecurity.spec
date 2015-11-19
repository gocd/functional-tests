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

NewPipelineSecurity
===================

Setup of contexts 
* Configure enterprise license - setup
* Setup password file and define roles - setup
* Config one group "GroupA" with pipeline "PipelineA" - setup
* Completed all pipelines - setup
* Capture go state "NewPipelineSecurity" - setup

NewPipelineSecurity
-------------------

tags: #2400, #2786, security, passwordfile support, authorisation, authentication, svn support, #2514, exploratory

Old twist test - AnyUserCanOperateWhenNoAuthorizationDefined.scn

view permission constrains

* allow user "userA" to view pipeline group "firstGroup"
* verify pipeline is completed
* verify user "userA" can not create artifact "artifact.txt"
* verify user "userA" can not append character "test" to artifact "artifact.txt"
* verify user "userA" can not create property with key "testproperty" value "testproperty_value"

Configuration Editor,Authorization with RESTful API, Scheduling(covered by automation): 
* configure a pipeline without security
* verify user "userA" can trigger pipeline "PipelineA" using restful url
* verify pipeline "PipelineA" with label "2" is completed
* verify user "userB" can trigger pipeline "PipelineA" using restful url
* verify pipeline "PipelineA" with label "3" is completed


New Workflow(covered by automation): 
* configure a stage without security
* verify user "userA" can trigger pipeline "PipelineA" using restful url
* verify pipeline "PipelineA" with label "2" is completed
* verify user "userB" can trigger pipeline "PipelineA" using restful url
* verify pipeline "PipelineA" with label "3" is completed

Pipeline view security

Roles

* login as "user-viewer"

* open current activity page
* verify shows pipeline group "defaultGroup"
* verify not shows pipeline group "secondGroup"

* open pipeline history page for pipeline in pipeline group "defaultGroup"
* verify shows pipeline history page for pipeline in pipeline group "defaultGroup"
* verify access pipeline history page in group "secondGroup" got unauthorized error

User
* login as "user-viewer-2"
* open current activity page
* verify shows pipeline group "secondGroup"
* verify not shows pipeline group "defaultGroup"

view security on the pipeline history page

* verify access pipeline history page in group "defaultGroup" with user "user-viewer" got ok
* verify access pipeline history json in group "defaultGroup" with user "user-viewer" got ok
* verify access pipeline history json in group "secondGroup" with user "user-viewer" got unauthorized error
* verify access pipeline history page in group "secondGroup" with user "user-viewer" got unauthorized error

Pipeline operate security

Happy path

* login as "user-viewer-operator"
* open current activity page
* verify force build button is enabled
* verify pause button is enabled
* verify can rerun "defaultStage"

* checkin "a.doc" under svn trunk with comment "add user manual"
* open pipeline activity page
* verify force build button is enabled
* verify pause button is enabled
* verify can rerun "defaultStage"

Happy path REST api

* verify user "user-viewer" can not pause pipeline
* verify user "user-viewer-operator" can pause pipeline
* verify pipeline is paused
* verify user "user-viewer" can not unpause pipeline
* verify user "user-viewer-operator" can unpause pipeline
* verify pipeline is not paused
* verify user "user-viewer" can not trigger pipeline
* user "user-viewer-operator" trigger pipeline using restful url
* verify pipeline is triggered
* verify pipeline is completed
* verify user "user-viewer" can not rerun stage "defaultStage"
* user "user-viewer-operator" rerun stage "defaultStage"
* verify pipeline is triggered
* verify user "user-viewer" can not cancel stage "defaultStage"
* verify pipeline is completed
* user "user-viewer-operator" rerun stage "defaultStage"
* verify stage "defaultStage" with counter "3" is triggered
* user "user-viewer-operator" cancel stage "defaultStage"
* verify stage "defaultStage" is cancelled

Sad path

* login as "user-viewer"

* open current activity page
* verify force build button is disabled
* verify pause button is disabled
* verify can not rerun "defaultStage"

* open pipeline activity page
* verify force build button is disabled
* verify pause button is disabled
* verify can not rerun "defaultStage"

Pipeline security for admins

* login as "user-admin"
* for pipeline "sample-pipeline"
* pause pipeline
* verify pipeline is paused
* unpause pipeline
* verify pipeline is not paused
* click force build
* verify pipeline "sample-pipeline" is labelled with "1"
* verify pipeline "sample-pipeline" is building
* cancel stage "defaultStage" on pipeline
* verify pipeline is cancelled
* rerun stage "defaultStage"
* verify pipeline "sample-pipeline" labelled with "1" has stage  "defaultStage" with counter "2"

* open admin configration wizard page
* verify shows pipeline group "defaultGroup"

Ensure behaviour through rest api
* verify user "user-admin" can pause pipeline
* verify pipeline is paused
* verify user "user-admin" can unpause pipeline
* verify pipeline is not paused
* trigger pipelines
* verify pipeline is completed
* user "user-admin" cancel stage "defaultStage"
* rerun stage "defaultStage" in latest pipeline
* verify pipeline is completed

Ensure unauthorised users can't do any of the above

Also test all the above for non-admins when security is turned off





Teardown of contexts 
* Capture go state "NewPipelineSecurity" - teardown
* Completed all pipelines - teardown
* Config one group "GroupA" with pipeline "PipelineA" - teardown
* Setup password file and define roles - teardown
* Configure enterprise license - teardown


