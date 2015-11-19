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

ServerConfigurationTabUserManagement
====================================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Capture go state "ServerConfigurationTabUserManagement" - setup

ServerConfigurationTabUserManagement
------------------------------------

tags: 4171, clicky ui, admin-page, configuration, ldap

* Open "Server Configuration" tab

Password Settings
* Set password file path to only admin password properties
* Save configuration

* Logout - On Any Page

* Login as "view" should fail with message "User view not found in directory. Help Topic: Authentication"
* Login as "admin"

LDAP Settings
* On Admin page
* Open "Server Configuration" tab

* ConfigureMailHost 
     |URI                                      |Ldap Username|Ldap Password|Search Base                                                                |Search  Filter      |message?                         |
     |-----------------------------------------|-------------|-------------|---------------------------------------------------------------------------|--------------------|---------------------------------|
     |ldap://fmtdc02.corporate.thoughtworks.com|foo          |bar          |ou=Employees,ou=Enterprise,ou=Principal,dc=corporate,dc=thoughtworks,dc=com|(sAMAccountName={0})|Saved configuration successfully.|

* Set field "ldap_uri" to "ldap://bad-server"
* Validate ldap should return "Cannot connect to ldap, please check the settings. Reason: bad-server:389; nested exception is javax.naming.CommunicationException: bad-server:389 [Root exception is java.net.UnknownHostException: bad-server]"







Teardown of contexts
* Capture go state "ServerConfigurationTabUserManagement" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


