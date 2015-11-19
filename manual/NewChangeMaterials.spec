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

NewChangeMaterials
==================

Setup of contexts 
* Basic configuration - setup
* Using pipeline "basic-auto-pipeline" - setup
* With "1" live agents in directory "ChangeMaterial" - setup
* Capture go state "NewChangeMaterials" - setup

NewChangeMaterials
------------------

tags: git support, rake support, 1995, gui config

* looking at pipeline "basic-auto-pipeline"
* wait for first stage to pass with pipeline label "1"

* changing destination of "git" material of "basic-auto-pipeline" to "git-changed"

* looking at pipeline "basic-auto-pipeline"
* wait for first stage to pass with pipeline label "2"

* change the scm for pipeline "basic-auto-pipeline" from "git" to "hg"

* looking at pipeline "basic-auto-pipeline"
* wait for first stage to pass with pipeline label "3"


create an auto pipeline with git material
let it finish once
change the material to hg
see that the pipeline is triggered automatically
change the material URL to point to a different hg material
see that the pipeline is triggered automatically
change the material URL to a different format
see that the pipeline is triggered automatically





Teardown of contexts 
* Capture go state "NewChangeMaterials" - teardown
* With "1" live agents in directory "ChangeMaterial" - teardown
* Using pipeline "basic-auto-pipeline" - teardown
* Basic configuration - teardown


