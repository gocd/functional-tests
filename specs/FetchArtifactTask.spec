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

FetchArtifactTask
=================

Setup of contexts 
* Basic configuration - setup
* Using pipeline "downstream-pipeline,basic-pipeline-fast" - setup
* Capture go state "FetchArtifactTask" - setup

FetchArtifactTask
-----------------

tags: 4706, Fetch Artifact, Clicky Admin, Task

* Click on pipeline "downstream-pipeline" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open tasks

* Open new task form "Fetch Artifact"

* Verify has "task[pipelineName](text_field): NULL, task[stage](text_field): NULL, task[job](text_field):NULL"
* Verify has "task[src](text_field): NULL, task[isSourceAFile](check_box): false, task[dest](text_field):NULL"
* Set "task[pipelineName](text_field): ${runtime_name:basic-pipeline-fast}" - Already on Task edit popup
* Set "task[stage](text_field): defaultStage" - Already on Task edit popup
* Set "task[job](text_field): defaultJob" - Already on Task edit popup
* Set "task[src](text_field): source_file" - Already on Task edit popup
* Set "task[isSourceAFile](check_box): true" - Already on Task edit popup
* Set "task[dest](text_field): dest_dir" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "2" is "Fetch Artifact" task without on cancel and properties "Pipeline Name: ${runtime_name:basic-pipeline-fast}, Stage Name: defaultStage, Job Name: defaultJob, Source File: source_file, Destination: dest_dir" that runs if state is "Passed"

* For pipeline "downstream-pipeline"
* Verify "fetchartifact" task with attributes "@pipeline: ${runtime_name:basic-pipeline-fast}, @stage: defaultStage, @job: defaultJob, @srcfile: source_file, @dest: dest_dir" exists for "defaultJob" in "defaultStage"

* Open task "2"

* Verify has "task[pipelineName](text_field): ${runtime_name:basic-pipeline-fast}"
* Verify has "task[stage](text_field): defaultStage"
* Verify has "task[job](text_field): defaultJob"
* Verify has "task[src](text_field): source_file"
* Verify has "task[isSourceAFile](check_box): true"
* Verify has "task[dest](text_field): dest_dir"
* Set "task[src](text_field): source_dir" - Already on Task edit popup
* Set "task[isSourceAFile](check_box): false" - Already on Task edit popup
* Set "task[dest](text_field): dest_dir2" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "2" is "Fetch Artifact" task without on cancel and properties "Pipeline Name: ${runtime_name:basic-pipeline-fast}, Stage Name: defaultStage, Job Name: defaultJob, Source Directory: source_dir, Destination: dest_dir2" that runs if state is "Passed"

* For pipeline "downstream-pipeline"
* Verify "fetchartifact" task with attributes "@pipeline: ${runtime_name:basic-pipeline-fast}, @stage: defaultStage, @job: defaultJob, @srcdir: source_dir, @dest: dest_dir2" exists for "defaultJob" in "defaultStage"

* Open new task form "Fetch Artifact"

AutoComplete and Save
* Set "task[pipelineName](text_field): b" - Already on Task edit popup
* Auto complete should show up "${runtime_name:basic-pipeline-fast}" - Already on Task edit popup
* Select option "${runtime_name:basic-pipeline-fast}" - Already on Task edit popup
* Set "task[stage](text_field): d" - Already on Task edit popup
* Auto complete should show up "defaultStage" for textBox "1"
* Select option from suggestion at index "0" for text box "1"
* Set "task[job](text_field): d" - Already on Task edit popup
* Auto complete should show up "defaultJob" for textBox "2"
* Select option from suggestion at index "0" for text box "2"
* Set "task[src](text_field): source_dir" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "3" is "Fetch Artifact" task without on cancel and properties "Pipeline Name: ${runtime_name:basic-pipeline-fast}, Stage Name: defaultStage, Job Name: defaultJob, Source Directory: source_dir" that runs if state is "Passed"

* For pipeline "downstream-pipeline"
* Verify "fetchartifact" task with attributes "@pipeline: ${runtime_name:basic-pipeline-fast}, @stage: defaultStage, @job: defaultJob, @srcdir: source_dir" exists for "defaultJob" in "defaultStage"

* Open new task form "Fetch Artifact"

Validations
* Verify has "task[pipelineName](text_field): NULL, task[stage](text_field): NULL, task[job](text_field):NULL"
* Verify has "task[src](text_field): NULL, task[isSourceAFile](check_box): false, task[dest](text_field):NULL"
* Save and verify save failed
* Verify save failed with error message "Should provide either srcdir or srcfile"


Teardown of contexts
____________________
* Capture go state "FetchArtifactTask" - teardown
* Using pipeline "downstream-pipeline,basic-pipeline-fast" - teardown
* Basic configuration - teardown


