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

AddTemplateWithCommandRepoTask
==============================

Setup of contexts
* Basic configuration - setup
* Setup command repo - setup
* Capture go state "AddTemplateCommandRepoTask" - setup

AddTemplateWithCommandRepoTask
------------------------------

tags: 6640, task-repository-location, admin-page

* Open "Templates" tab

* Add new template

* Enter template name "some_funky_template"
* Save for success - Already on create new template popup

* Open stages tab

* Open stage "defaultStage"

* Open jobs

* Open job "defaultJob"

* Open tasks

* Open new task form "More..."


* Enter "snippet" in "Basic Settings" command lookup autocomplete box
* Auto complete should show suggestions "snippet1,snippet2,snippet_3_without_comment" - Already on Task edit popup
* Select option "3" from command lookup dropdown - Already on Task edit popup
* Verify command is set to "snippet3" with arguments "arg1_for_snippet3,arg2_for_snippet3" - Already on Task edit popup
* Verify snippet details in "Basic Settings" are shown with name "snippet_3_without_comment" only
* Set "task[workingDirectory](text_field): workdir" - Already on Task edit popup
* Set "task[hasCancelTask](check_box): true" - Already on Task edit popup
* Select task "More..."
* Enter "snippet" in "Advanced Options" command lookup autocomplete box
* Auto complete should show suggestions "snippet1,snippet2,snippet_3_without_comment" - Already on Task edit popup
* Select option "2" from command lookup dropdown in advanced options
* Verify command is set to "snippet2" with arguments "arg1_for_snippet2,arg2_for_snippet2" in advanced options
* Verify snippet details in "Advanced Options" are shown with name "snippet2" description "Snippet 2 description." author "Go Team" with authorlink "http://go.team/" and more info "http://more.info/"
* Save and verify saved successfully


* Verify task "1" is "Custom Command" task with cancel "Custom Command" and properties "Command: snippet3, Arguments: arg1_for_snippet3 arg2_for_snippet3,  Working Directory: workdir " that runs if state is "Passed"

* On Admin page
* Open "Templates" tab

* Delete template "some_funky_template"




Teardown of contexts
____________________
* Capture go state "AddTemplateCommandRepoTask" - teardown
* Setup command repo - teardown
* Basic configuration - teardown


