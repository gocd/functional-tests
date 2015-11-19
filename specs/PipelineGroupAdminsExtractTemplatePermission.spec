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

PipelineGroupAdminsExtractTemplatePermission
============================================

Setup of contexts
* Group admin security configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline-with-group-and-stage-security" - setup
* Capture go state "PipelineGroupAdminExtractTemplatePermission" - setup

PipelineGroupAdminsExtractTemplatePermission
--------------------------------------------

tags: group admin, 4138

User pavan is the pipeline Group Admin. He should not see Extract Template.

* Open "Pipelines" tab

* Verify that extract template is visible and enabled for "pipeline-with-group-and-stage-security"

* Logout - On Any Page

* Login as "pavan"

* On Admin page
* Open "Pipelines" tab

* Verify that extract template is not visible for "pipeline-with-group-and-stage-security"




Teardown of contexts
* Capture go state "PipelineGroupAdminExtractTemplatePermission" - teardown
* Using pipeline "pipeline-with-group-and-stage-security" - teardown
* Login as "admin" - teardown
* Group admin security configuration - teardown


