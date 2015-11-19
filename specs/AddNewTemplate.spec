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

AddNewTemplate
==============

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-ls, simple-pipeline,template-params-pipeline-trunk" - setup
* Capture go state "AddNewTemplate" - setup

AddNewTemplate
--------------

tags: Clicky Admin, #4911, template, #5105

Story: 4911 - Add a new template

* Open "Templates" tab

* Add new template

* Save
* Verify error message "Invalid template name ''. This must be alphanumeric and can contain underscores and periods (however, it cannot start with a period). The maximum allowed length is 255 characters."
* Enter template name "some_funky_template"
* Save for success - Already on create new template popup

* Verify template being edited is "some_funky_template"
* Open stages tab

* Verify "defaultStage" stage is present
* Open stage "defaultStage"

* Open jobs

* Verify "defaultJob" job is present
* Open job "defaultJob"

* Open tasks

* Verify no tasks exists


Story: 5105 -  Create template from an existing pipeline

* On Admin page

* Open "Templates" tab

* Add new template

* Enter template name "template_from_pipeline"
* Select extract template from a existing pipeline
* Verify template can be extracted only from pipelines "pipeline-ls,simple-pipeline"
* Select pipeline "simple-pipeline"
* Save for success - Already on create new template popup

* Open stages tab

* Verify "first" stage is present

* On Admin page

* Open "Pipelines" tab

* Edit pipeline "simple-pipeline"

* Verify pipeline uses template "template_from_pipeline"
* Open parameters page

* Verify parameter "name" has value "value"
* Open general options page

* Go to environment variables page

* Verify that "simple-pipeline" has variable named "NAME" with value "hucha_raghuveer"

* On Admin page

* Open "Pipelines" tab

* Delete pipeline "pipeline-ls"

* Open "Templates" tab

* Add new template

* Verify extract template checkbox is disabled
* Verify extract template checkbox is not checked




Teardown of contexts
* Capture go state "AddNewTemplate" - teardown
* Using pipeline "pipeline-ls, simple-pipeline,template-params-pipeline-trunk" - teardown
* Basic configuration - teardown


