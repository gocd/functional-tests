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

TaskViewerAndEditor-EditFlow
============================

     |TaskIndex|Old BuildFile|New BuildFile|Old Target|New Target|Old Working Dir|New Working Dir|TaskType|OnCancel Build File|OnCancel Target|OnCancel Working Dir|Properties-View                                                           |OnCancel-TaskType|
     |---------|-------------|-------------|----------|----------|---------------|---------------|--------|-------------------|---------------|--------------------|--------------------------------------------------------------------------|-----------------|
     |1        |             |new file name|longsleep |new target|hg/dev         |blah/pwd       |Ant     |build.xml          |on cancel      |blah/on/cancel      |Build File: new file name, Target: new target, Working Directory: blah/pwd|Ant              |
     |2        |             |rakeFile     |all       |metric-fu |hg/dev         |boooo          |Rake    |rakeFile           |target         |clean/up/dir        |Build File: rakeFile, Target: metric-fu, Working Directory: boooo         |Rake             |
     |3        |             |default.build|all       |build     |hg/dev         |blahaaa        |NAnt    |someotherfile.build|clean          |blahaaa             |Build File: default.build, Target: build, Working Directory: blahaaa      |NAnt             |
Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline-with-tasks" - setup
* Capture go state "TaskViewerAndEditor-EditFlow" - setup

TaskViewerAndEditor-EditFlow
----------------------------

tags: 4700, 4702, Clicky Admin


* Click on pipeline "edit-pipeline-with-tasks" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

Edit preconfigured task and add the same task type as an oncancel task
* Open tasks

* Open task <TaskIndex>

* Verify build file is <Old BuildFile>
* Enter build file as <New BuildFile>
* Verify target is <Old Target>
* Enter target as <New Target>
* Verify working directory is <Old Working Dir>
* Enter working directory as <New Working Dir>
* Set any runif
* Add a <OnCancel-TaskType> on cancel task
* Enter on cancel build file as <OnCancel Build File>
* Enter on cancel target as <OnCancel Target>
* Enter on cancel working directory as <OnCancel Working Dir>
* Click save - BuildTaskPopup

* Verify presence of <TaskIndex> index task as <TaskType> with runif "Any" and properties <Properties-View> and <OnCancel-TaskType> on cancel task




Teardown of contexts
____________________
* Capture go state "TaskViewerAndEditor-EditFlow" - teardown
* Using pipeline "edit-pipeline-with-tasks" - teardown
* Basic configuration - teardown


