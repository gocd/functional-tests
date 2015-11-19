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

MaterialEditorNameAndFolderValidations
======================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "MaterialEditorNameAndFolderValidations" - setup

MaterialEditorNameAndFolderValidations
--------------------------------------

tags: Clicky Admin, 4598

* Click on pipeline "edit-pipeline" for editing

* Open material listing page

Create one of hg, svn, p4 and git materials

* Create new "hg" material with data "materialName=hg_material,url=http://foo.bar,folder=hg_dir"
* Verify that "hg" with name "hg_material" is added with attributes "url>http://foo.bar,dest>hg_dir"
* Create new "svn" material with data "materialName=svn_material,url=http://foo.bar,folder=svn_dir"
* Verify that "svn" with name "svn_material" is added with attributes "url>http://foo.bar,dest>svn_dir"
* Create new "p4" material with data "materialName=p4_material,serverAndPort=p4:1666,view=abc,folder=p4_dir"
* Verify that "p4" with name "p4_material" is added with attributes "port>p4:1666,dest>p4_dir"
* Create new "git" material with data "materialName=git_material,url=http://foo.bar,folder=git_dir"
* Verify that "git" with name "git_material" is added with attributes "url>http://foo.bar,dest>git_dir"

Material Form Validations
* MaterialFormValidator 
     |materialName|elementType|field       |value      |message?                                                                                                                                                                                                                                                                                                   |
     |------------|-----------|------------|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
     |hg_material |textbox    |materialName|hg-material|You have defined multiple materials called 'hg-material'. Material names are case-insensitive and must be unique. Note that for dependency materials the default materialName is the name of the upstream pipeline. You can override this by setting the materialName explicitly for the upstream pipeline.|
     |hg_material |textbox    |folder      |hg         |Invalid Destination Directory. Every material needs a different destination directory and the directories should not be nested.                                                                                                                                                                            |
     |svn_material|textbox    |materialName|hg-material|You have defined multiple materials called 'hg-material'. Material names are case-insensitive and must be unique. Note that for dependency materials the default materialName is the name of the upstream pipeline. You can override this by setting the materialName explicitly for the upstream pipeline.|
     |svn_material|textbox    |folder      |hg         |Invalid Destination Directory. Every material needs a different destination directory and the directories should not be nested.                                                                                                                                                                            |
     |git_material|textbox    |materialName|hg-material|You have defined multiple materials called 'hg-material'. Material names are case-insensitive and must be unique. Note that for dependency materials the default materialName is the name of the upstream pipeline. You can override this by setting the materialName explicitly for the upstream pipeline.|
     |git_material|textbox    |folder      |hg         |Invalid Destination Directory. Every material needs a different destination directory and the directories should not be nested.                                                                                                                                                                            |
     |p4_material |textbox    |materialName|hg-material|You have defined multiple materials called 'hg-material'. Material names are case-insensitive and must be unique. Note that for dependency materials the default materialName is the name of the upstream pipeline. You can override this by setting the materialName explicitly for the upstream pipeline.|
     |p4_material |textbox    |folder      |hg         |Invalid Destination Directory. Every material needs a different destination directory and the directories should not be nested.                                                                                                                                                                            |






Teardown of contexts
* Capture go state "MaterialEditorNameAndFolderValidations" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


