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

NewCruiseInvalidLicense
=======================

Setup of contexts 
* Cruise config without license - setup
* Capture go state "NewCruiseInvalidLicense" - setup

NewCruiseInvalidLicense
-----------------------

tags: licensing, restful api


NO IDEA WHAT THIS MEANS! (Sachin)
With empty license

* RequestsOnInValidLicense
| Url | Method | Should Ignore? | Should Pay? | Should Redirect? | 
| files/pipeline/LATEST/stage/1/job/1/readme.txt | POST | true | false | false | 
| files/pipeline/LATEST/stage/1/job/1/readme.txt | PUT | true | false | false | 
| files/pipeline/LATEST/stage/1/job/1/readme.txt | GET | false | false | true | 
| admin/agent | GET | true | false | false | 
| pipelineStatus.json | GET | false | true | false | 


With invalid license

* open server details page
* enter license "invalid user" "invalid license"
* verify flash message "The license key you entered is invalid. Please enter a valid license key."





Teardown of contexts 
* Capture go state "NewCruiseInvalidLicense" - teardown
* Cruise config without license - teardown


