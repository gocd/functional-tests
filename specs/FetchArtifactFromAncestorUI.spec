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

FetchArtifactFromAncestorUI
===========================

Setup of contexts 
* Basic configuration - setup
* Using pipeline "uppest_pipeline, upper_pipeline, upper_peer_pipeline, up_pipeline, down_pipeline" - setup
* Capture go state "FetchMaterialFromAncestorUI" - setup

FetchArtifactFromAncestorUI
---------------------------

tags: 5783, Fetch Artifact, Clicky Admin, Task, 5784

* Click on pipeline "down_pipeline" for editing

* Open stage listing page

* Open stage "down-stage2"

* Open jobs

* Open job "down-job2"

* Open tasks

* Open new task form "Fetch Artifact"

* Verify has "task[pipelineName](text_field): NULL, task[stage](text_field): NULL, task[job](text_field):NULL"
* Verify has "task[src](text_field): NULL, task[isSourceAFile](check_box): false, task[dest](text_field):NULL"
* Set "task[pipelineName](text_field): ${runtime_name:uppest_pipeline}/${runtime_name:upper_pipeline}/${runtime_name:up_pipeline}" - Already on Task edit popup
* Set "task[stage](text_field): uppest-stage1" - Already on Task edit popup
* Set "task[job](text_field): uppest-job1" - Already on Task edit popup
* Set "task[src](text_field): source_file" - Already on Task edit popup
* Set "task[isSourceAFile](check_box): true" - Already on Task edit popup
* Set "task[dest](text_field): dest_dir" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "1" is "Fetch Artifact" task without on cancel and properties "Pipeline Name: ${runtime_name:uppest_pipeline}/${runtime_name:upper_pipeline}/${runtime_name:up_pipeline}, Stage Name: uppest-stage1, Job Name: uppest-job1, Source File: source_file, Destination: dest_dir" that runs if state is "Passed"

* For pipeline "down_pipeline"
* Verify "fetchartifact" task with attributes "@pipeline: ${runtime_name:uppest_pipeline}/${runtime_name:upper_pipeline}/${runtime_name:up_pipeline}, @stage: uppest-stage1, @job: uppest-job1, @srcfile: source_file, @dest: dest_dir" exists for "down-job2" in "down-stage2"

* Open task "1"

* Verify has "task[pipelineName](text_field): ${runtime_name:uppest_pipeline}/${runtime_name:upper_pipeline}/${runtime_name:up_pipeline}"
* Verify has "task[stage](text_field): uppest-stage1"
* Verify has "task[job](text_field): uppest-job1"
* Verify has "task[src](text_field): source_file"
* Verify has "task[isSourceAFile](check_box): true"
* Verify has "task[dest](text_field): dest_dir"
* Set "task[src](text_field): source_dir" - Already on Task edit popup
* Set "task[isSourceAFile](check_box): false" - Already on Task edit popup
* Set "task[dest](text_field): dest_dir2" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "1" is "Fetch Artifact" task without on cancel and properties "Pipeline Name: ${runtime_name:uppest_pipeline}/${runtime_name:upper_pipeline}/${runtime_name:up_pipeline}, Stage Name: uppest-stage1, Job Name: uppest-job1, Source Directory: source_dir, Destination: dest_dir2" that runs if state is "Passed"

* For pipeline "down_pipeline"
* Verify "fetchartifact" task with attributes "@pipeline: ${runtime_name:uppest_pipeline}/${runtime_name:upper_pipeline}/${runtime_name:up_pipeline}, @stage: uppest-stage1, @job: uppest-job1, @srcdir: source_dir, @dest: dest_dir2" exists for "down-job2" in "down-stage2"

* Open new task form "Fetch Artifact"

AutoComplete and Save
* Set "task[stage](text_field): down" - Already on Task edit popup
* Auto complete should show up "down-stage1" for textBox "0"
* Select option from suggestion at index "0" for text box "0"
* Set "task[job](text_field): down" - Already on Task edit popup
* Auto complete should show up "down-job1" for textBox "1"
* Set "task[pipelineName](text_field): up" - Already on Task edit popup
* Auto complete should show up "${runtime_name:uppest_pipeline}/${runtime_name:upper_pipeline}/${runtime_name:up_pipeline}" for textBox "2"
* Auto complete should show up "${runtime_name:uppest_pipeline}/${runtime_name:upper_peer_pipeline}/${runtime_name:up_pipeline}" for textBox "2"
* Auto complete should show up "${runtime_name:upper_pipeline}/${runtime_name:up_pipeline}" for textBox "2"
* Auto complete should show up "${runtime_name:upper_peer_pipeline}/${runtime_name:up_pipeline}" for textBox "2"
* Auto complete should show up "${runtime_name:up_pipeline}" for textBox "2"
* Select option from suggestion at index "4" for text box "2"
* Set "task[stage](text_field): u" - Already on Task edit popup
* Auto complete should show up "uppest-stage1" for textBox "3"
* Auto complete should show up "uppest-stage2" for textBox "3"
* Select option from suggestion at index "0" for text box "3"
* Set "task[job](text_field): up" - Already on Task edit popup
* Auto complete should show up "uppest-job1a" for textBox "4"
* Select option from suggestion at index "1" for text box "4"
* Set "task[src](text_field): source_dir" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "2" is "Fetch Artifact" task without on cancel and properties "Pipeline Name: ${runtime_name:uppest_pipeline}/${runtime_name:upper_pipeline}/${runtime_name:up_pipeline}, Stage Name: uppest-stage1, Job Name: uppest-job1a, Source Directory: source_dir" that runs if state is "Passed"

* For pipeline "down_pipeline"
* Verify "fetchartifact" task with attributes "@pipeline: ${runtime_name:uppest_pipeline}/${runtime_name:upper_pipeline}/${runtime_name:up_pipeline}, @stage: uppest-stage1, @job: uppest-job1a, @srcdir: source_dir" exists for "down-job2" in "down-stage2"

* Open new task form "Fetch Artifact"

* Set "task[stage](text_field): down" - Already on Task edit popup
* Auto complete should show up "down-stage1" for textBox "0"
* Select option from suggestion at index "0" for text box "0"
* Set "task[job](text_field): down" - Already on Task edit popup
* Auto complete should show up "down-job1" for textBox "1"
* Select option from suggestion at index "0" for text box "1"
* Set "task[src](text_field): src_dir" - Already on Task edit popup
* Save and verify saved successfully

* Verify task "3" is "Fetch Artifact" task without on cancel and properties "Stage Name: down-stage1, Job Name: down-job1, Source Directory: src_dir" that runs if state is "Passed"

* For pipeline "down_pipeline"
* Verify "fetchartifact" task with attributes "@stage: down-stage1, @job: down-job1, @srcdir: src_dir" exists for "down-job2" in "down-stage2"

* On Pipeline Configuration wizard
* Click on pipeline "down_pipeline" for editing

* Open material listing page

* Open new pipeline material creation popup

* Enter pipeline "uppest_pipeline" and stage "uppest-stage2"
* Click save - Already on pipeline material creation popup

* Verify that material "${runtime_name:up_pipeline}" cannot be deleted because of reason "Cannot delete this material since it is used in a fetch artifact task."
* Verify that material "${runtime_name:uppest_pipeline}" can be deleted


Teardown of contexts
____________________
* Capture go state "FetchMaterialFromAncestorUI" - teardown
* Using pipeline "uppest_pipeline, upper_pipeline, upper_peer_pipeline, up_pipeline, down_pipeline" - teardown
* Basic configuration - teardown


