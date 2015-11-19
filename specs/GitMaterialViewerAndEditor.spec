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

GitMaterialViewerAndEditor
==========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "up, edit-pipeline" - setup
* Capture go state "GitMaterialViewerAndEditor" - setup

GitMaterialViewerAndEditor
--------------------------

tags: Clicky Admin, 4598

* Click on pipeline "edit-pipeline" for editing

* Open material listing page

* Open new git material creation popup

* Enter material name "git_material" - Already on git material creation popup
* Enter url "foo.bar" - Already on git material creation popup
* Make autoupdate to be "false"
* Enter branch "2.1"
* Enter destination directory "git_dir" - Already on git material creation popup
* Enter black list "*.htm"
* Click save - Already on git material creation popup

* Verify that material saved successfully
* Verify that "git" with name "git_material" is added with attributes "materialName>git_material,url>foo.bar,dest>git_dir,autoUpdate>false,branch>2.1"
* Edit material "git_material"

* Verify material name is "git_material"
* Verify url is "foo.bar"
* Verify autoupdate is "false"
* Verify branch is "2.1"
* Verify destination directory is "git_dir"
* Verify black list is "*.htm"
* Enter branch "2.2"
* Click save - Already on git material creation popup

* Verify that material saved successfully
* Verify that "git" with name "git_material" is added with attributes "materialName>git_material,branch>2.2"
* Delete material with name "git_material"
* Verify that material saved successfully
* Verify that material with name "git_material" does not exist




Teardown of contexts
* Capture go state "GitMaterialViewerAndEditor" - teardown
* Using pipeline "up, edit-pipeline" - teardown
* Basic configuration - teardown


