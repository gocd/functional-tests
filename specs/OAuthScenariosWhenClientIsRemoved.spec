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

OAuthScenariosWhenClientIsRemoved
=================================

Setup of contexts
* Group admin security configuration - setup
* Login as "admin" - setup
* Using pipeline "pipeline1, pipeline2" - setup
* Capture go state "OAuthScenariosWhenClientIsRemoved" - setup

OAuthScenariosWhenClientIsRemoved
---------------------------------

tags: 4248, oAuth

* Add site uRL
* Add secure site uRL

* Logout - On any page over localhost ssl

* Login as "admin" - On Login Page Over Localhost Ssl

* Open "OAuth Clients" tab

* Delete all client entries
* Navigate to new client form

Populate OAuth Clients
* PopulateOAuthClients 
     |Client Name  |Redirect Url            |
     |-------------|------------------------|
     |group1-client|/dummy_not_in_production|


* Open "OAuth Clients" tab

The case where the client is deleted before authenticating

* Remember client entries for "group1-client"
* Delete "group1-client" entry

* Using "HTTPS"

* For client "group1-client"
* Verify requesting approval redirects to "/dummy_not_in_production" and returns with error "invalid-client-id"

Populate OAuth Clients
* PopulateOAuthClients 
     |Client Name  |Redirect Url            |
     |-------------|------------------------|
     |group1-client|/dummy_not_in_production|

* Remember client entries for "group1-client"

* For client "group1-client"
* Request approval
* Approve pending request
* Save authorization code

* On Admin page - Logged in using ssl
* Navigate to Admin page
* Open "OAuth Clients" tab

* Delete "group1-client" entry

* For client "group1-client"
* Verify obtaining access token returns error message "invalid-client-credentials" and status "400"

* Logout - On any page over localhost ssl

* Logout - On Any Page Over Hostname Ssl




Teardown of contexts
* Capture go state "OAuthScenariosWhenClientIsRemoved" - teardown
* Using pipeline "pipeline1, pipeline2" - teardown
* Login as "admin" - teardown
* Group admin security configuration - teardown


