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

PluginValidations
=================

Setup of contexts
* Capture go state "PluginValidations" - setup

PluginValidations
-----------------

tags: #7481, Plugin Validations, plugins-tests, manual

* Open "Plugins" tab

* Verify plugin named "invalid-xml-plugin.jar" with id "invalid-xml-plugin.jar" is marked as invalid with message "Plugin with ID (invalid-xml-plugin.jar) is not valid: XML Schema validation of Plugin Descriptor(plugin.xml) failed."
* Verify plugin named "plugin-with-invalid-id.jar" with id "plugin-with-invalid-id.jar" is marked as invalid with message "Plugin with ID (plugin-with-invalid-id.jar) is not valid: XML Schema validation of Plugin Descriptor(plugin.xml) failed."
* Verify plugin named "plugin-with-multiple-load-methods.jar" with id "plugin-with-multiple-load-methods.jar" is marked as invalid with message which is either "Class [PluginWithMultipleLoadMethods] is annotated with @Extension will not be registered. Reason: java.lang.RuntimeException: More than one method with @Load annotation not allowed. Methods Found: [public void com.tw.qa.plugin.sample.PluginWithMultipleLoadMethods.onLoad(com.thoughtworks.go.plugin.api.info.PluginContext), public void com.tw.qa.plugin.sample.PluginWithMultipleLoadMethods.duplicateLoad(com.thoughtworks.go.plugin.api.info.PluginContext)]" or "Class [PluginWithMultipleLoadMethods] is annotated with @Extension will not be registered. Reason: java.lang.RuntimeException: More than one method with @Load annotation not allowed. Methods Found: [public void com.tw.qa.plugin.sample.PluginWithMultipleLoadMethods.duplicateLoad(com.thoughtworks.go.plugin.api.info.PluginContext), public void com.tw.qa.plugin.sample.PluginWithMultipleLoadMethods.onLoad(com.thoughtworks.go.plugin.api.info.PluginContext)]"




Teardown of contexts
____________________
* Capture go state "PluginValidations" - teardown


