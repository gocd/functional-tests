// Copyright 2016 ThoughtWorks, Inc.
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//    http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


Materials
=========

* Basic configuration - setup
* Using pipeline "parent_pipeline" - setup
* Enable feature "pipeline_config_single_page_app_key"

Materials listing
-----------------

tags: SPA, webdriver

* Go to "parent_pipeline" edit page
* View materials
* Verify materials section has Git Material with data
  |Name |Destination |IgnoreFields  |AutoUpdate |Branch |
  |git  |git         |*.ignore      |false      |master |
* Verify materials section has Svn Material with data
  |Name |Destination |IgnoreFields  |AutoUpdate |CheckExternals |
  |svn  |svn         |*.ignore      |false      |false          |
* Verify materials section has Hg Material with data
  |Name |Destination |IgnoreFields  |AutoUpdate |Branch |
  |hg   |hg          |*.ignore      |false      |       |

Materials test connection
-------------------------

tags: SPA, webdriver

* Test connection for Git Material
* Verify Git test connection failed

Tear downs
_______________

* Basic configuration - teardown
* Using pipeline "parent_pipeline" - teardown