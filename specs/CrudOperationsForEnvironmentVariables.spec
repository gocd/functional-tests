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

CrudOperationsForEnvironmentVariables
=====================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "different_variable_at_environment_pipeline_stage_job" - setup
* With "1" live agents in directory "CrudOperationsForEnvironmentVariables" - setup
* Capture go state "CrudOperationsForEnvironmentVariables" - setup

CrudOperationsForEnvironmentVariables
-------------------------------------

tags: automate, environment variables, stage1, 6826


* Add environment "environment_1" to agents "2"

* trigger pipeline "different_variable_at_environment_pipeline_stage_job" and navigate to job "job_1" of "stage_1" on successful run "1"

* Open console tab
* Verify console contains "environment_variable_1=at_job_level_1."
* Verify console contains "environment_variable_2=at_job_level_2."

* Navigate to environment variables tab for job "job_1" of stage "stage_1" of pipeline "different_variable_at_environment_pipeline_stage_job"
variables page

* Delete variable "environment_variable_1" for "job"
* Click save - Already On Environment Variables Page
* Verify "Saved successfully." message is displayed - Already On Environment Variables Page

* On Pipeline Dashboard Page
* trigger pipeline "different_variable_at_environment_pipeline_stage_job" and navigate to job "job_1" of "stage_1" on successful run "2"

* Open console tab
* Verify console contains "environment_variable_1=at_stage_level_1."
* Verify console contains "environment_variable_2=at_job_level_2."

* On Admin page
* Navigate to environment variable tab of stage "stage_1" of pipeline "different_variable_at_environment_pipeline_stage_job"

* Delete variable "environment_variable_1" for "stage"
* Click save - Already On Environment Variables Page
* Verify "Saved successfully." message is displayed - Already On Environment Variables Page

* On Pipeline Dashboard Page
* trigger pipeline "different_variable_at_environment_pipeline_stage_job" and navigate to job "job_1" of "stage_1" on successful run "3"

* Open console tab
* Verify console contains "environment_variable_1=at_pipeline_level."
* Verify console contains "environment_variable_2=at_job_level_2."

* On Admin page
* Navigate to environment variables tab for pipeline "different_variable_at_environment_pipeline_stage_job"

* Delete variable "environment_variable_1" for "pipeline"
* Click save - Already On Environment Variables Page
* Verify "Saved successfully." message is displayed - Already On Environment Variables Page

* On Pipeline Dashboard Page
* trigger pipeline "different_variable_at_environment_pipeline_stage_job" and navigate to job "job_1" of "stage_1" on successful run "4"

* Open console tab
* Verify console contains "environment_variable_1=environment_value_1."
* Verify console contains "environment_variable_2=at_job_level_2."

* On Admin page
* Navigate to environment variables tab for pipeline "different_variable_at_environment_pipeline_stage_job"

* Add variable "environment_variable_1" with value "value_set_by_pipeline" for "pipeline" at "2"
* Click save - Already On Environment Variables Page
* Verify "Saved successfully." message is displayed - Already On Environment Variables Page

* On Pipeline Dashboard Page
* trigger pipeline "different_variable_at_environment_pipeline_stage_job" and navigate to job "job_1" of "stage_1" on successful run "5"

* Open console tab
* Verify console contains "environment_variable_1=value_set_by_pipeline."
* Verify console contains "environment_variable_2=at_job_level_2."

* On Admin page
* Navigate to environment variable tab of stage "stage_1" of pipeline "different_variable_at_environment_pipeline_stage_job"

* Add variable "environment_variable_1" with value "value_set_by_stage" for "stage" at "2"
* Click save - Already On Environment Variables Page
* Verify "Saved successfully." message is displayed - Already On Environment Variables Page

* On Pipeline Dashboard Page
* trigger pipeline "different_variable_at_environment_pipeline_stage_job" and navigate to job "job_1" of "stage_1" on successful run "6"

* Open console tab
* Verify console contains "environment_variable_1=value_set_by_stage."
* Verify console contains "environment_variable_2=at_job_level_2."

* On Admin page
* Navigate to environment variables tab for job "job_1" of stage "stage_1" of pipeline "different_variable_at_environment_pipeline_stage_job"

* Add variable "environment_variable_1" with value "value_set_by_job" for "job" at "2"
* Click save - Already On Environment Variables Page
* Verify "Saved successfully." message is displayed - Already On Environment Variables Page

* On Pipeline Dashboard Page
* trigger pipeline "different_variable_at_environment_pipeline_stage_job" and navigate to job "job_1" of "stage_1" on successful run "7"

* Open console tab
* Verify console contains "environment_variable_1=value_set_by_job."
* Verify console contains "environment_variable_2=at_job_level_2."







Teardown of contexts
* Capture go state "CrudOperationsForEnvironmentVariables" - teardown
* With "1" live agents in directory "CrudOperationsForEnvironmentVariables" - teardown
* Using pipeline "different_variable_at_environment_pipeline_stage_job" - teardown
* Basic configuration - teardown


