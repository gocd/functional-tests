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

DuplicatePipelineManual
=======================

DuplicatePipelineManual
-----------------------


Scenario: user should be able to clone a pipeline that has been created from the scratch
* Pipeline that has been created from the scratch
* There are a list of pipeline group admins associated with pipeline

* Pipeline group admin or Super Admin access the pipelines page

* He should see a clone link for pipeline in context
* He should also be able to clone and be able to create a completely new pipeline

Scenario: user should be able to clone a pipeline that has been created using a template
* Pipeline that has been created from the template
* There are a list of pipeline group admins associated with pipeline

* Pipeline group admin  or Super Admin access the pipelines page

* He should see a clone link for pipeline in context
* He should also be able to clone and be able to create a completely new pipeline
* The created pipeline should use the same template as the original one


Scenario: Validation message should be shown if a same pipeline name that is already existing is being given as a name for the new pipeline
* Pipeline that has been created from the scratch
* There are a list of pipeline group admins associated with pipeline

* Pipeline group admin  tries to clone the existing pipeline

* He should be shown only the pipeline group names for which he is the admin
* There should be an option to select a different pipeline group
* If an existing pipeline name is entered and clone button pressed there should be a validation stating the pipeline name already exists


Scenario: Pipeline should be created with the content that was initially cloned and should not fail on modifcation for originial pipeline ( Need not do an md5 check)

* Pipeline that has been created from the scratch
* There are a list of pipeline group admins associated with pipeline

* Pipeline group admin  or Super Admin tries to clone the existing pipeline and the existing pipeline is being modified by some other admin

* The clone should go successfully with what ever value that was available during cloning
* The cloned pipeline should be in a paused state
* Initial state of the pipeline should be retained for the orignial pipeline


Scenario: Pipeline group option should not be shown if it is a community license

* Pipeline that has been created from the scratch
* There are a list of pipeline group admins associated with pipeline
* The user is having a community license

* While cloning pipeline

* Pipeline group option should  not be shown

Scenario: If a pipeline group is not given it should be associated with the pipeline group of the original pipeline
* Pipeline that has been created from the scratch
* There are a list of pipeline group admins associated with pipeline
* The user is having a community license

* While cloning pipeline with out selecting any specific pipeline group

* Pipeline that was created should be associated with the same pipeline group as that of the original pipeline


Scenario: Invalid username is being entered for pipeline name
* Pipeline that has been created from the scratch
* There are a list of pipeline group admins associated with pipeline

* While cloning pipeline by giving an invalid name for pipeline name

* Validation error message should be shown for a pipeline in context

Scenario: User is cloning a pipeline that is belonging to an environment
* Pipeline that has been created from the scratch
* Another pipeline has been created using a template
* Both the pipelines are associated with an environment
* There are a list of pipeline group admins associated with pipeline

* When the admin cloned the pipleine

* Cloned pipeline should not be associated with any environment


Scenario: Pipeline that is getting cloned has some parameters that has been associated with a role
* Pipeline that is being cloned has certain parameters that has been associated with a role and user for a particular stage
* Pipeline belongs to a particular pipeline group say pipeline_original and the associated roles and user has been defined for a particular pipeline
* There exists another pipeline for which no user or role has been defined

* The admin clones the pipeline and associates the cloned pipeline to another pipeline group say pipeline_clone

* Cloning of pipeline should be successfull and the cloned pipeline should inherit the permissions of the pipeline_clone group permissions





