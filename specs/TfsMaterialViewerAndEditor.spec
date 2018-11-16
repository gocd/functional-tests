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

TfsMaterialViewerAndEditor
==========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "TfsMaterialViewerAndEditor" - setup

TfsMaterialViewerAndEditor
--------------------------

tags: Clicky Admin, #5775, 6191, tfs, internal

* Click on pipeline "edit-pipeline" for editing

* Open parameters page

* Enter tfs url as parameter "1" with name "valid_tfs_url" and value "integration_tests"
* Enter parameter "2" name "username" and derive value from environment variable "TFS_SERVER_USERNAME"
* Enter parameter "3" name "valid_project_path" and value "$/MyFirstProject"
* Enter parameter "4" name "destination_dir" and value "tfs_destination"
* Enter tfs url as parameter "5" with name "invalid_tfs_url" and value "incorrect_url"
* Enter parameter "6" name "invalid_username" and value "invalidUser"
* Enter parameter "7" name "invalid_project_path" and value "$/invalid_project_path"
* Click save
* Verify saved successfully
* Open general options page


* Open material listing page

* Open new tfs material creation popup

* Enter material name "tfs_material" - Already on Tfs Material Creation Popup
* Enter url "incorrect_url" - Already on tfs material creation popup
* Click check connection
* Verify message "There was an error with the material configuration!"
* Enter url "#{invalid_tfs_url}" - Already on tfs material creation popup
* Click check connection
* Verify message "There was an error with the material configuration!"
* Enter url "#{valid_tfs_url}" - Already on tfs material creation popup
* Enter username "invalidUser" - Already on Tfs Material Creation Popup
* Click check connection
* Verify message "There was an error with the material configuration!"
* Enter url "integration_tests" - Already on tfs material creation popup
* Enter username "#{invalid_username}" - Already on Tfs Material Creation Popup
* Click check connection
* Verify message "There was an error with the material configuration!"
* Enter username "#{username}" - Already on Tfs Material Creation Popup
* Enter valid password - Already on Tfs Material Creation Popup
* Enter project path as "$/invalid_project_path"
* Click check connection
* Verify message "project path might be invalid"
* Enter valid username - Already on Tfs Material Creation Popup
* Enter project path as "#{invalid_project_path}"
* Click check connection
* Verify message "project path might be invalid"
* Enter project path as "#{valid_project_path}"
* Enter destination directory "#{destination_dir}" - Already on Tfs Material Creation Popup
* Click check connection
* Verify message "Connection OK"
* Enter project path as "$/MyFirstProject"
* Enter destination directory "tfs_destination" - Already on Tfs Material Creation Popup
* Click check connection
* Verify message "Connection OK"
* Make autoupdate to be "false" - Already on Tfs Material Creation Popup
* Click save - Already on tfs material creation popup

* Verify that material saved successfully
* Verify that "tfs" with name "tfs_material" is added with attributes "materialName>tfs_material,url>https://go-tfs-user.visualstudio.com,username>go.tfs.user,dest>tfs_destination,autoUpdate>false,projectPath>$/MyFirstProject"
* Edit material "tfs_material"

* Enter material name "tfs_material_edited" - Already on Tfs Material Creation Popup
* Enter url "integration_tests_edited" - Already on tfs material creation popup
* Enter username "user_edited" - Already on Tfs Material Creation Popup
* Enter password "abc" - Already on Tfs Material Creation Popup
* Enter project path as "$/MyFirstProject"
* Enter destination directory "tfs_destination_edited" - Already on Tfs Material Creation Popup
* Make autoupdate to be "false" - Already on Tfs Material Creation Popup
* Click save - Already on tfs material creation popup

* Verify that material saved successfully
* Verify that "tfs" with name "tfs_material_edited" is added with attributes "materialName>tfs_material_edited,url>https://go-tfs-user.visualstudio.com,username>user_edited,dest>tfs_destination_edited,autoUpdate>false,projectPath>$/MyFirstProject"




Teardown of contexts
____________________
* Capture go state "TfsMaterialViewerAndEditor" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


