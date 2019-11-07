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

ServerConfigurationTabMailHost
==============================

Setup of contexts
* Basic configuration - setup
* Capture go state "ServerConfigurationTabMailHost" - setup

ServerConfigurationTabMailHost
------------------------------

tags: 4171, clicky ui, admin-page, configuration, ignore


* ConfigureMailHost 
     |Hostname                  |Port|Username|Password|TLS  |From Email             |Admin Email           |Message?                                                                                                                |
     |--------------------------|----|--------|--------|-----|-----------------------|----------------------|------------------------------------------------------------------------------------------------------------------------|
     |b+ve                      |25  |        |        |true |ccedev@thoughtworks.com|admin@thoughtworks.com|Invalid hostname. A valid hostname can only contain letters (A-z) digits (0-9) hyphens (-) dots (.) and underscores (_).|
     |chimisc01.thoughtworks.com|-1  |        |        |false|ccedev@thoughtworks.com|admin@thoughtworks.com|Invalid port.                                                                                                           |
     |chimisc01.thoughtworks.com|25  |        |        |true |ccedev                 |admin@thoughtworks.com|From address is not a valid email address.                                                                              |
     |chimisc01.thoughtworks.com|25  |        |        |true |ccedev@thoughtworks.com|admin                 |Admin address is not a valid email address.                                                                             |
     |chimisc01.thoughtworks.com|25  |pavan   |awesome |true |ccedev@thoughtworks.com|admin@thoughtworks.com|Saved configuration successfully.                                                                                       |






Teardown of contexts
____________________
* Capture go state "ServerConfigurationTabMailHost" - teardown
* Basic configuration - teardown


