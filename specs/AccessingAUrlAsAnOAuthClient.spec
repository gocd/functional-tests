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

AccessingAUrlAsAnOAuthClient
============================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline" - setup
* Capture go state "AccessingAUrlAsAnOAuthClient" - setup

AccessingAUrlAsAnOAuthClient
----------------------------

tags: 4248, oAuth, windows

* Logout and login as "view"
* Verify "/go/admin/oauth/clients" returns "403"

* Logout

* Login as "admin"

* Open "Server Configuration" tab

* Verify text field "siteUrl" has value ""
* Verify text field "secureSiteUrl" has value ""
* Set field "siteUrl" to "http://localhost:8253"
* Set field "secureSiteUrl" to "https://localhost:8254"
* Save configuration
* Verify message "Saved configuration successfully." shows up

* On Admin page - Logged in using ssl
* Logout - On any page over localhost ssl

* Login as "admin" - On Login Page Over Localhost Ssl

* Navigate to Admin page
* Open "OAuth Clients" tab

* Delete all client entries
* Navigate to new client form

Populate OAuth Clients
* PopulateOAuthClients 
     |Client Name |Redirect Url            |
     |------------|------------------------|
     |twist-client|/dummy_not_in_production|


* Open "OAuth Clients" tab

* Remember client entries for "twist-client"

* For client "twist-client"
* Request approval
* Approve pending request
* Save authorization code
* Using "HTTPS"
* Obtain access token
* Gadget page for pipeline "basic-pipeline" should contain "No historical data"
* "/pipelines" Should return code "403"
* On Preferences OAuth Tokens page
* Logout - On any page over localhost ssl

force logging out session for hostname. Other tests use hostname in url. Was causing flakyness
* Logout - On Any Page Over Hostname Ssl



Teardown of contexts
* Capture go state "AccessingAUrlAsAnOAuthClient" - teardown
* Using pipeline "basic-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


