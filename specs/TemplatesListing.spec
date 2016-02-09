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

TemplatesListing
================

Setup of contexts
* Template admin configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline1,pipeline2,pipeline3,pipeline-down,pipeline-without-template" - setup
* Capture go state "TemplatesViewerAndEditor" - setup

TemplatesListing
----------------

tags: 4600, Clicky Admin

* Open "Templates" tab

* Verify that templates "simple-pass,default.pipeline,unused" are present
* Verify that template "simple-pass" is used by pipelines "pipeline1,pipeline2,pipeline3"
* Verify that template "default.pipeline" is used by pipelines "pipeline-down"
* Verify that template "unused" has message "No pipelines associated with this template"
* Verify that edit pipeline "pipeline1" lands on pipeline edit page
* Verify cannot delete templates "simple-pass,default.pipeline"
* Verify can delete tempates "unused"
* Delete template "unused"
* Verify that templates "simple-pass,default.pipeline" are present
* Verify that templates "unused" are not present

* Logout and login as "group1Admin"

* On Admin page
* Verify templates are visible





Teardown of contexts
____________________
* Capture go state "TemplatesViewerAndEditor" - teardown
* Using pipeline "pipeline1,pipeline2,pipeline3,pipeline-down,pipeline-without-template" - teardown
* Login as "admin" - teardown
* Template admin configuration - teardown


