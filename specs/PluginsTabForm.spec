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

PluginsTabForm
==============

Setup of contexts
* Setup http based yum repo - setup
* Group admin security configuration - setup
* Login as "admin" - setup
* Capture go state "PluginsTabForm" - setup

PluginsTabForm
--------------

tags: 7735, 7777, admin-page, configuration, plugins-tests

* Open "Plugins" tab

* Verify plugin named "Yum Plugin" with id "yum" is valid
* Verify plugin with id "yum" has version "2.0.3" name "Yum Plugin" description "Plugin that polls a yum repository" author "ThoughtWorks Go Team" with enabled link to "https://www.thoughtworks.com/"
* Verify more information for plugin with id "yum" has path "/plugins/bundled/gocd-yum-repository-poller-plugin.jar" target os "Linux" target go version "15.2.0" and bundled status as "Yes"
* Verify plugin named "test-donothing-plugin.jar" with id "test-donothing-plugin.jar" is valid
* Verify plugin with id "test-donothing-plugin.jar" has version "" name "test-donothing-plugin.jar" description "No description available." author "Unknown" with disabled link
* Verify more information for plugin with id "test-donothing-plugin.jar" has path "/test-donothing-plugin.jar" target os "No restrictions" target go version "Unknown" and bundled status as "No"
* Verify plugin named "test-plugin-xml" with id "test-plugin-xml" is marked as invalid with message "Plugin with ID (test-plugin-xml) is not valid: Incompatible with current operating system 'Linux'. Valid operating systems are: [Windows]."
* Verify plugin with id "test-plugin-xml" has version "1.0.0" name "test-plugin-xml" description "Plugin that has only some fields in its plugin.xml" author "Unknown" with enabled link to "http://www.mdaliejaz.com/"
* Verify more information for plugin with id "test-plugin-xml" has path "test-with-some-plugin-xml-values.jar" target os "Windows" target go version "Unknown" and bundled status as "No"

* Logout and login as "group1Admin"

* On Admin page
* Open "Plugins" tab

* Verify plugin named "Yum Plugin" with id "yum" is valid
* Verify plugin with id "yum" has version "2.0.3" name "Yum Plugin" description "Plugin that polls a yum repository" author "ThoughtWorks Go Team" with enabled link to "http://www.thoughtworks.com/"
* Verify more information for plugin with id "yum" has path "/plugins/bundled/gocd-yum-repository-poller-plugin.jar" target os "Linux" target go version "15.2.0" and bundled status as "Yes"
* Verify plugin named "test-donothing-plugin.jar" with id "test-donothing-plugin.jar" is valid
* Verify plugin with id "test-donothing-plugin.jar" has version "" name "test-donothing-plugin.jar" description "No description available." author "Unknown" with disabled link
* Verify more information for plugin with id "test-donothing-plugin.jar" has path "/test-donothing-plugin.jar" target os "No restrictions" target go version "Unknown" and bundled status as "No"
* Verify plugin named "test-plugin-xml" with id "test-plugin-xml" is marked as invalid with message "Plugin with ID (test-plugin-xml) is not valid: Incompatible with current operating system 'Linux'. Valid operating systems are: [Windows]."
* Verify plugin with id "test-plugin-xml" has version "1.0.0" name "test-plugin-xml" description "Plugin that has only some fields in its plugin.xml" author "Unknown" with enabled link to "http://www.mdaliejaz.com/"
* Verify more information for plugin with id "test-plugin-xml" has path "/test-with-some-plugin-xml-values.jar" target os "Windows" target go version "Unknown" and bundled status as "No"





Teardown of contexts
____________________
* Capture go state "PluginsTabForm" - teardown
* Login as "admin" - teardown
* Group admin security configuration - teardown
* Setup http based yum repo - teardown
