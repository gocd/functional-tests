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

FetchArtifactTaskUI
===================

Setup of contexts 
* Basic configuration - setup
* Using pipeline "upper_pipeline,uppest_pipeline" - setup
* Capture go state "FetchArtifactTask" - setup

FetchArtifactTaskUI
-------------------

tags: 4706, Fetch Artifact, Clicky Admin, Task

* Click on pipeline "upper_pipeline" for editing

* Open stage listing page

* Open stage "upper-stage1"

* Open jobs

* Open job "upper-job1"

* Open tasks

* Open new task form "Fetch Artifact"

Using source file
* Verify has "task[pipelineName](text_field): NULL, task[stage](text_field): NULL, task[job](text_field):NULL"
* Verify has "task[src](text_field): NULL, task[isSourceAFile](check_box): false, task[dest](text_field):NULL"
* Set "task[pipelineName](text_field): ${runtime_name:uppest_pipeline}" - Already on Task edit popup
* Set "task[stage](text_field): uppest-stage2" - Already on Task edit popup
* Set "task[job](text_field): uppest-job2" - Already on Task edit popup
* Set "task[src](text_field): source_file" - Already on Task edit popup
* Set "task[isSourceAFile](check_box): true" - Already on Task edit popup
* Set "task[dest](text_field): dest_dir" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "3" is "Fetch Artifact" task without on cancel and properties "Pipeline Name: ${runtime_name:uppest_pipeline}, Stage Name: uppest-stage2, Job Name: uppest-job2, Source File: source_file, Destination: dest_dir" that runs if state is "Passed"

* For pipeline "upper_pipeline"
* Verify "fetchartifact" task with attributes "@pipeline: ${runtime_name:uppest_pipeline}, @stage: uppest-stage2, @job: uppest-job2, @srcfile: source_file, @dest: dest_dir" exists for "upper-job1" in "upper-stage1"

* Open task "3"

Using source dir
* Verify has "task[pipelineName](text_field): ${runtime_name:uppest_pipeline}"
* Verify has "task[stage](text_field): uppest-stage2"
* Verify has "task[job](text_field): uppest-job2"
* Verify has "task[src](text_field): source_file"
* Verify has "task[isSourceAFile](check_box): true"
* Verify has "task[dest](text_field): dest_dir"
* Set "task[src](text_field): source_dir" - Already on Task edit popup
* Set "task[isSourceAFile](check_box): false" - Already on Task edit popup
* Set "task[dest](text_field): dest_dir2" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "3" is "Fetch Artifact" task without on cancel and properties "Pipeline Name: ${runtime_name:uppest_pipeline}, Stage Name: uppest-stage2, Job Name: uppest-job2, Source Directory: source_dir, Destination: dest_dir2" that runs if state is "Passed"

* For pipeline "upper_pipeline"
* Verify "fetchartifact" task with attributes "@pipeline: ${runtime_name:uppest_pipeline}, @stage: uppest-stage2, @job: uppest-job2, @srcdir: source_dir, @dest: dest_dir2" exists for "upper-job1" in "upper-stage1"

* Open new task form "Fetch Artifact"

AutoComplete and Save
* Set "task[pipelineName](text_field): u" - Already on Task edit popup
* Auto complete should show up "${runtime_name:uppest_pipeline}" - Already on Task edit popup
* Select option "${runtime_name:uppest_pipeline}" - Already on Task edit popup
* Set "task[stage](text_field): u" - Already on Task edit popup
* Auto complete should show up "uppest-stage2" for textBox "1"
* Select option from suggestion at index "1" for text box "1"
* Set "task[job](text_field): u" - Already on Task edit popup
* Auto complete should show up "uppest-job2" for textBox "2"
* Select option from suggestion at index "0" for text box "2"
* Set "task[src](text_field): source_dir" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "4" is "Fetch Artifact" task without on cancel and properties "Pipeline Name: ${runtime_name:uppest_pipeline}, Stage Name: uppest-stage2, Job Name: uppest-job2, Source Directory: source_dir" that runs if state is "Passed"

* For pipeline "upper_pipeline"
* Verify "fetchartifact" task with attributes "@pipeline: ${runtime_name:uppest_pipeline}, @stage: uppest-stage2, @job: uppest-job2, @srcdir: source_dir" exists for "upper-job1" in "upper-stage1"

Fetch Artifact from a stage previous to the stage we are directly dependent on

* Open new task form "Fetch Artifact"

AutoComplete and Save
* Set "task[pipelineName](text_field): u" - Already on Task edit popup
* Auto complete should show up "${runtime_name:uppest_pipeline}" - Already on Task edit popup
* Select option "${runtime_name:uppest_pipeline}" - Already on Task edit popup
* Set "task[stage](text_field): u" - Already on Task edit popup
* Auto complete should show up "uppest-stage1" for textBox "1"
* Select option from suggestion at index "0" for text box "1"
* Set "task[job](text_field): u" - Already on Task edit popup
* Auto complete should show up "uppest-job1" for textBox "2"
* Select option from suggestion at index "0" for text box "2"
* Set "task[src](text_field): source_dir" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "5" is "Fetch Artifact" task without on cancel and properties "Pipeline Name: ${runtime_name:uppest_pipeline}, Stage Name: uppest-stage1, Job Name: uppest-job1, Source Directory: source_dir" that runs if state is "Passed"

* For pipeline "upper_pipeline"
* Verify "fetchartifact" task with attributes "@pipeline: ${runtime_name:uppest_pipeline}, @stage: uppest-stage1, @job: uppest-job1, @srcdir: source_dir" exists for "upper-job1" in "upper-stage1"

Error conditions
* Open new task form "Fetch Artifact"

* Verify has "task[pipelineName](text_field): NULL, task[stage](text_field): NULL, task[job](text_field):NULL"
* Verify has "task[src](text_field): NULL, task[isSourceAFile](check_box): false, task[dest](text_field):NULL"
* Save and verify save failed
* Verify validation failed for "task[stage](text_field): Stage is a required field."
* Verify validation failed for "task[job](text_field): Job is a required field."
* Verify validation failed for "task[src](text_field): Should provide either srcdir or srcfile"
* Set "task[pipelineName](text_field): ${runtime_name:uppest_pipeline}" - Already on Task edit popup
* Set "task[stage](text_field): uppest-stage3" - Already on Task edit popup
* Set "task[job](text_field): uppest-job3" - Already on Task edit popup
* Save and verify save failed
* Verify validation failed for "task[stage](text_field): \"${runtime_name:upper_pipeline} :: upper-stage1 :: upper-job1\" tries to fetch artifact from stage \"${runtime_name:uppest_pipeline} :: uppest-stage3\" which does not complete before \"${runtime_name:upper_pipeline}\" pipeline's dependencies."
* Verify validation failed for "task[src](text_field): Should provide either srcdir or srcfile"


Fetch Artifact from previous stage of same pipeline
* On Pipeline Configuration wizard
* Click on pipeline "uppest_pipeline" for editing

* Open stage listing page

* Open stage "uppest-stage2"

* Open jobs

* Open job "uppest-job2"

* Open tasks

* Open new task form "Fetch Artifact"

* Set "task[pipelineName](text_field): NULL" - Already on Task edit popup
* Set "task[stage](text_field): uppest-stage1" - Already on Task edit popup
* Set "task[job](text_field): uppest-job1" - Already on Task edit popup
* Set "task[src](text_field): source_dir" - Already on Task edit popup
* Set "task[isSourceAFile](check_box): false" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "2" is "Fetch Artifact" task without on cancel and properties "Stage Name: uppest-stage1, Job Name: uppest-job1, Source Directory: source_dir" that runs if state is "Passed"

Teardown of contexts
____________________
* Capture go state "FetchArtifactTask" - teardown
* Using pipeline "upper_pipeline,uppest_pipeline" - teardown
* Basic configuration - teardown


