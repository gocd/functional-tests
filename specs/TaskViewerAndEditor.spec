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

TaskViewerAndEditor
===================

     |TaskType|Target            |BuildFile    |WorkingDirectory                                                           |RunIf-View    |Properties-View                                                                                                                            |
     |--------|------------------|-------------|---------------------------------------------------------------------------|--------------|-------------------------------------------------------------------------------------------------------------------------------------------|
     |Ant     |compile test      |build.xml    |working/directory/very/very/very/deep/down/in/our/current/working/directory|Failed, Passed|Build File: build.xml, Target: compile test, Working Directory: working/directory/very/very/very/deep/down/in/our/current/working/directory|
     |Rake    |test deploy       |RakeFile     |pwd                                                                        |Failed, Passed|Build File: RakeFile, Target: test deploy, Working Directory: pwd                                                                          |
     |NAnt    |clean build deploy|default.build|windows\directory\in\the\super\windows                                     |Failed, Passed|Build File: default.build, Target: clean build deploy, Working Directory: windows\directory\in\the\super\windows                           |
Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "TaskViewerAndEditor" - setup

TaskViewerAndEditor
-------------------

tags: 4700, 4702, Clicky Admin


* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open tasks
* Add new <TaskType> task

* Enter build file as <BuildFile>
* Enter target as <Target>
* Enter working directory as <WorkingDirectory>
* Set failed runif
* Click save - BuildTaskPopup

* Verify presence of "2" index task as <TaskType> with runif <RunIf-View> and properties <Properties-View> and "No" on cancel task





Teardown of contexts
* Capture go state "TaskViewerAndEditor" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


