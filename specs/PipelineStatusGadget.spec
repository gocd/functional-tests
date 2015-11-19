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

PipelineStatusGadget
====================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "admin-pipeline, viewable-pipeline" - setup
* Gadget rendering server is running - setup
* Capture go state "PipelineStatusGadget" - setup

PipelineStatusGadget
--------------------

tags: 4249, gadgets, oAuth, 4366, 4332, 5270

* Open "Server Configuration" tab

* For field "siteUrl" "http://localhost:8253"
* For field "secureSiteUrl" "https://localhost:8254"
* Save configuration
* Verify message "Saved configuration successfully." shows up

* Logout - On any page over localhost ssl

* Login as "admin" - On Login Page Over Localhost Ssl

* Looking at pipeline "admin-pipeline"
* Trigger pipeline

* On Admin page - Logged in using ssl
* Navigate to Admin page
* Open "OAuth Clients" tab

* Delete all client entries
* Navigate to new client form

* Using client name "gadget-server"
* Using redirect url "http://localhost:3000/gadgets/oauthcallback"
* Add new auth client

* Remember client entries for "gadget-server"

* By creating user "admin@tw.com" with password "admin"
* By creating user "view@tw.com" with password "view"
* By creating user "noPermission@tw.com" with password "noPermission"

* Log in as "admin@tw.com" using password "admin"

* Using client "gadget-server"
* Using service "Go"
* Add client for gadget "pipeline.xml"

* On Gadget Rendering Page
* Add pipeline status gadget for "admin-pipeline"
* Authorize gadget to access data using user "admin"
* Verify pipeline "admin-pipeline" is at label "1"

Check if user without view permission can view the gadget content

* Logout - On any page over localhost ssl

* Logout - On Gadget rendering page
* Log in as "noPermission@tw.com" using password "noPermission"

* Add pipeline status gadget for "admin-pipeline"
* Authorize gadget to access data using user "pavan"
* Verify message contains "User 'pavan' does not have view permission on pipeline '${runtime_name:admin-pipeline}'" - On Gadget rendering page

Check if user can view the gadget content for a non-existent pipeline

* Logout - On any page over localhost ssl

* Logout - On Gadget rendering page
* Log in as "noPermission@tw.com" using password "noPermission"

* Add pipeline status gadget for "a-pipeline-that-does-not-exist"
* Verify message contains "Pipeline 'a-pipeline-that-does-not-exist' not found." - On Gadget rendering page

Ensure disabled user's can't view gadget

* Logout - On any page over localhost ssl

* Logout - On Gadget rendering page
* Log in as "view@tw.com" using password "view"

* Add pipeline status gadget for "viewable-pipeline"
* Authorize gadget to access data using user "view"
* Verify pipeline "viewable-pipeline" has no history

* On Admin page - Logged in using ssl
* Navigate to Admin page
* Open "User Summary" tab

* Disable users "view"
* Verify users "view" are disabled

* On Gadget Rendering Page
* Add pipeline status gadget for "viewable-pipeline"
* Verify authorize link is present

* Logout - On any page over localhost ssl

force logging out session for hostname. Other tests use hostname in url. Was causing flakyness
* Logout - On Any Page Over Hostname Ssl




Teardown of contexts
* Capture go state "PipelineStatusGadget" - teardown
* Gadget rendering server is running - teardown
* Using pipeline "admin-pipeline, viewable-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


