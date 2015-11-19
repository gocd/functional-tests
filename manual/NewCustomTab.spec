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

NewCustomTab
============

Setup of contexts 
* Completed svn pipeline - setup
* Capture go state "NewCustomTab" - setup

NewCustomTab
------------

tags: svn support, customizable reports, scheduling


* open job detail
* verify sub tab "My Tab" not exists
* add customized sub tab "MyTab"
* open job detail
* verify sub tab "MyTab" exists
* verify sub tab "MyTab" content exist
* trigger new pipeline and complete it
* open job detail
* verify sub tab "MyTab" exists
* verify sub tab "MyTab" content exist


Teardown of contexts 
* Capture go state "NewCustomTab" - teardown
* Completed svn pipeline - teardown


