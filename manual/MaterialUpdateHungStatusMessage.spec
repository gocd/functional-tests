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

MaterialUpdateHungStatusMessage
===============================

Setup of contexts 
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "MaterialUpdateHungStatusMessage" - setup

MaterialUpdateHungStatusMessage
-------------------------------

tags: 6667

Start the server with setting -Dmaterial.update.idle.timeout=60000 to see the warning message within 1 min.
The default is 15 mins.


* click on pipeline "edit-pipeline" for editing

* Open material listing page

* open new git material Creation popup

* enter material name "bad_git_material"
* enter url "https://blrstdscm01.thoughtworks.com/git/go_performance"
* make autoupdate to be "false"
* enter destination directory "git_dir"
* click save

* looking at pipeline "edit-pipeline"
* trigger pipeline
* sleep for "90" seconds
* open error and warning messages popup

* verify warning "description" contains "Material update is currently running but has not shown any activity in the last 1 minute(s). This may be hung."





Teardown of contexts 
* Capture go state "MaterialUpdateHungStatusMessage" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


