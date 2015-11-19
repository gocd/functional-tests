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

NewProfessionalLicense
======================

Setup of contexts 
* sample configuration - setup
* Configure professional license - setup
* Trigger "sample-pipeline" - setup
* Capture go state "NewProfessionalLicense" - setup

NewProfessionalLicense
----------------------

tags: #2777, licensing, security, professional license

Old twist test - AllowProfessionalLicenseUseFeatures.scn

Test that professional License should enable pipeline group level security

* login as "user-admin"
* configure a pipeline in a group with operate permission
* trigger the pipeline
* verify no error message and pipline triggers





Teardown of contexts 
* Capture go state "NewProfessionalLicense" - teardown
* Trigger "sample-pipeline" - teardown
* Configure professional license - teardown
* sample configuration - teardown


