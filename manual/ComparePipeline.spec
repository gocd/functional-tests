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

ComparePipeline
===============

Setup of contexts
* Basic configuration - setup
* Using pipeline "up, down" - setup
* With "1" live agents in directory "card_activity" - setup
* Capture go state "ComparePipeline" - setup

ComparePipeline
---------------

tags: 4688, compare_pipeline, stage1

* Trigger pipelines "up" and wait for labels "1" to pass
* Compare pipeline instance "1" with "1"

* Verify that "to" textbox is populated with "1"
* Verify that "from" textbox is populated with "1"

* With material named "hg-material" in pipeline "up"
* Checkin file "foo.txt" as user "user" with message "Comment user"
* Remember current version as "FIRST_REVISION"
* Checkin file "bar.txt" as user "luser" with message "Comment loser"
* Remember current version as "SECOND_REVISION"

* On Pipeline Dashboard Page
* Trigger pipelines "up" and wait for labels "2" to pass
* Trigger pipelines "down" and wait for labels "1" to pass
* Looking at pipeline "up"
* Compare pipeline instance "1" with "2"

* Verify that "from" textbox is populated with "1"
* Verify that "to" textbox is populated with "2"

* With material named "hg-material" in pipeline "up"
* Checkin file "foo1.txt" as user "user" with message "second checkin comment"
* Remember current version as "THIRD_REVISION"

* On Pipeline Dashboard Page
* Trigger pipelines "up" and wait for labels "3" to pass
* Trigger pipelines "down" and wait for labels "2" to pass
* Looking at pipeline "up"
* Compare pipeline instance "2" with "3"

Exact matching labels
* Verify that "from" textbox is populated with "2"
* Verify that "to" textbox is populated with "3"
* Search for "1" on "from" textbox
* Click on label "1" in the dropdown
* Verify that "from" textbox is populated with "1"
* Verify that "to" textbox is populated with "3"

Match revision
* Search for revision "SECOND_REVISION" on "to" textbox
* Click on label "2" in the dropdown
* Verify that "from" textbox is populated with "1"
* Verify that "to" textbox is populated with "2"

Match partial user
* Search for "user" on "from" textbox
* Verify dropdown has labels "3,2"
* Click on label "2" in the dropdown
* Verify that "from" textbox is populated with "2"
* Verify that "to" textbox is populated with "2"

Match triggered user
* Search for "anony" on "from" textbox
* Verify dropdown has labels "1"
* Click on label "1" in the dropdown

Match partial comment
* Search for "comm" on "to" textbox
* Verify dropdown has labels "3,2"
* Click on label "3" in the dropdown
* Verify that "from" textbox is populated with "1"
* Verify that "to" textbox is populated with "3"

Downstream
* On Pipeline Dashboard Page
* Looking at pipeline "down"
* Compare pipeline instance "1" with "2"

* Verify that "from" textbox is populated with "1"
* Verify that "to" textbox is populated with "2"
* Search for "2" on "to" textbox
* Click on label "1" in the dropdown




Teardown of contexts
____________________
* Capture go state "ComparePipeline" - teardown
* With "1" live agents in directory "card_activity" - teardown
* Using pipeline "up, down" - teardown
* Basic configuration - teardown


