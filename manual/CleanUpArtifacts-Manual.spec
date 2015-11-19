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

CleanUpArtifacts-Manual
=======================

Setup of contexts 
* Basic configuration - setup

CleanUpArtifacts-Manual
-----------------------

tags: 1920, Clicky Admin, administration

Scenario : Migration related scenario

Given
There is already a go/cruise version installed.

When
User upgrades to latest version of go

Then
After sucessful migration, existing artifacts attribute of server tag should have been removed and replaced with artifacts tag with in the server tag.
Opening the server configuration page should show previously configured artifacts location (Is it a part of story scope???)
He should also see a check box for auto-deleting of the documents and an adjacent textbox field with an input value of zero





Teardown of contexts 
* Basic configuration - teardown


