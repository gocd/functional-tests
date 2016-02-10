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

PipelineGroupSnippetEdit
========================

Setup of contexts
* Group admin security configuration - setup
* Login as "group1Admin" - setup
* Capture go state "PipelineGroupSnippetEdit" - setup

PipelineGroupSnippetEdit
------------------------

tags: 5149


* Open config tab as group admin

Happy Path
* Verify group list has "group1,group5"
* Verify selected group is "group1"
* Click link "EDIT"
* Change group name of "group1" to "new_group"
* Save changes
* Verify that xml saved successfully
* Verify group list has "new_group,group5"
* Verify that xml snippet contains "<pipelines group=\"new_group\">"

Validations
* Verify selected group is "new_group"
* Click link "EDIT"
* Change group name of "new_group" to "foo%#"
* Save changes
* Verify error message "Failed to update group 'new_group'. Name is invalid. \"foo%#\" should conform to the pattern - [a-zA-Z0-9_\\-]{1}[a-zA-Z0-9_\\-.]*" - Already On Pipeline Group Xml




Teardown of contexts
____________________
* Capture go state "PipelineGroupSnippetEdit" - teardown
* Login as "group1Admin" - teardown
* Group admin security configuration - teardown


