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

TemplateAdminPermissions
========================

Setup of contexts
* Template admin configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline1,pipeline2,pipeline3,pipeline-without-template" - setup
* Capture go state "TemplatesAdminPermissions" - setup

TemplateAdminPermissions
------------------------

tags: Clicky Admin, 7876

* Open "Templates" tab

* Verify that templates "simple-pass" are present
* Verify that template "simple-pass" is used by pipelines "pipeline1,pipeline2,pipeline3"
* Verify that "simple-pass" template has permissions link enabled and click on it

* Enter user "0" as "some-user"
* Click reset - Already On Permissions Page For Template
* Verify that user "some-user" does not show up in user permissions
* Enter user "0" as "group1Admin"
* Click save - Already On Permissions Page For Template
* Verify that the message "Saved successfully." shows up - Already On Permissions Page For Template
* Verify that user "group1Admin" shows up in user permissions

* Logout and login as "group1Admin"

* On Admin page
* Open "Templates" tab

* Verify that template "simple-pass" is present with disabled "Permissions" link
* Verify that template "template-admin-pipeline" is present with disabled "Permissions" link

* Logout and login as "admin"

* On Admin page
* Open "Templates" tab

* Verify that templates "simple-pass" are present
* Verify that "simple-pass" template has permissions link enabled and click on it

* Delete user "group1Admin" - Already On Permissions Page For Template
* Click save - Already On Permissions Page For Template

* Logout and login as "group1Admin"

* On Admin page
* Open "Templates" tab

* Verify that template "simple-pass" is present with disabled "Edit" link
* Verify that template "simple-pass" is present with enabled "View" link
* Verify that template "template-admin-pipeline" is present with enabled "Edit" link
* Verify that template "template-admin-pipeline" is present with disabled "Permissions" link

Verify allow group admins view permission flag

* Logout and login as "admin"

* On Admin page
* Open "Templates" tab

* Verify that "simple-pass" template has permissions link enabled and click on it
* Switch allow pipeline group admin view access to templates flag - Already On Permissions Page For Template
* Click save - Already On Permissions Page For Template

* Logout and login as "group1Admin"

* On Admin page
* Open "Templates" tab

* Verify that templates "simple-pass" are not present


Teardown of contexts
____________________
* Capture go state "TemplatesAdminPermissions" - teardown
* Using pipeline "pipeline1,pipeline2,pipeline3,pipeline-without-template" - teardown
* Login as "admin" - teardown
* Template admin configuration - teardown


