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

BackupGoServer
==============

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline" - setup
* Delete backup history - setup
* Capture go state "BackupGoServer" - setup

BackupGoServer
--------------

tags: 5570, administration, backup, 5818, stage1, h2db_only

* Open "Backup" tab

* Verify the location of the backup store is at "serverBackups"
* Verify the last performed backup message contains "Go has not performed a backup yet."
* Perform backup

* Proceed with backup

* Verify the message "Backup was generated successfully."
* Verify the last performed backup message contains "Last backup was taken by 'admin' on"

* Verify the "serverBackups" directory exists
* Verify the "serverBackups" directory contains "db.zip" file in the tree
* Verify the "serverBackups" directory contains "config-repo.zip" file in the tree
* Verify the "serverBackups" directory contains "config-dir.zip" file in the tree
* Verify the "serverBackups" directory contains "version.txt" file in the tree
* Verify the "serverBackups" directory contains file named "version.txt" which has running go version





Teardown of contexts
____________________
* Capture go state "BackupGoServer" - teardown
* Delete backup history - teardown
* Using pipeline "basic-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


