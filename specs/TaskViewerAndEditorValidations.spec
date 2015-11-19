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

TaskViewerAndEditorValidations
==============================

     |TaskType|
     |--------|
     |Ant     |
     |Rake    |
     |NAnt    |
Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "TaskViewerAndEditorValidations" - setup

TaskViewerAndEditorValidations
------------------------------

tags: 4700, 4702, Clicky Admin

* Click on pipeline "edit-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open tasks
* Add new <TaskType> task

Adding a relative path for working directory
* Enter working directory as "../../should/fail"
* Save and verify save failed
* Verify validation failed for "task[workingDirectory](text_field): Task of job 'defaultJob' in stage 'defaultStage' of pipeline '${runtime_name:edit-pipeline}' has path '../../should/fail' which is outside the working directory."

Adding a invalid param for working directory
* Enter working directory as "#{a}"
* Save and verify save failed
* Verify validation failed for "task[workingDirectory](text_field): Parameter 'a' is not defined. All pipelines using this parameter directly or via a template must define it."




Teardown of contexts
* Capture go state "TaskViewerAndEditorValidations" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


