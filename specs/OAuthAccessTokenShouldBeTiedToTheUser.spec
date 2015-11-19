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

OAuthAccessTokenShouldBeTiedToTheUser
=====================================

Setup of contexts
* Group admin security configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline1, pipeline2" - setup
* Capture go state "OAuthAccessTokenShouldBeTiedToTheUser" - setup

OAuthAccessTokenShouldBeTiedToTheUser
-------------------------------------

tags: 4248, oAuth

This scenario tests that access token should be tied to the user who approved

* Adding "http://localhost:8253" as site uRL
* Adding "https://localhost:8254" as secure site uRL

* Logout - On any page over localhost ssl

* Login as "admin" - On Login Page Over Localhost Ssl

* On Admin page
* Open "OAuth Clients" tab

* Delete all client entries
* Navigate to new client form

Populate OAuth Clients
* PopulateOAuthClients 
     |Client Name|Redirect Url            |
     |-----------|------------------------|
     |client     |/dummy_not_in_production|


* On Admin page
* Open "OAuth Clients" tab

* Remember client entries for "client"

* Logout - On Any Page

* Login as "group1Admin" - On Login Page Over Localhost Ssl

* Using "HTTPS"

* For client "client"
* Request approval
* Approve pending request
* Save authorization code
* Obtain access token

* On Admin page - Logged in using ssl
* Logout - On any page over localhost ssl

* Login as "group2Admin" - On Login Page Over Localhost Ssl

* For client "client"
* Request approval
* Approve pending request
* Save authorization code
* Obtain access token

* On Preferences OAuth Tokens page
* Verify token "client" exists

* Logout - On Any Page

* For client "client"
* For user "group1Admin"
* Using "HTTPS"
* Gadget page for pipeline "pipeline1" should contain "pipeline_pipeline1"

* For client "client"
* For user "group2Admin"
* Using "HTTPS"
* Gadget page for pipeline "pipeline2" should contain "pipeline_pipeline2"

* Logout - On Any Page Over Hostname Ssl




Teardown of contexts
* Capture go state "OAuthAccessTokenShouldBeTiedToTheUser" - teardown
* Using pipeline "pipeline1, pipeline2" - teardown
* Login as "admin" - teardown
* Group admin security configuration - teardown


