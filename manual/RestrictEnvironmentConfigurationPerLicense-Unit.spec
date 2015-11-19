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

RestrictEnvironmentConfigurationPerLicense-Unit
===============================================

Setup of contexts 
* Capture go state "RestrictEnvironmentConfigurationPerLicense-Unit" - setup

RestrictEnvironmentConfigurationPerLicense-Unit
-----------------------------------------------

tags: 3347, unit, license, config

Need to repeat the following tests for environments, pipeline groups, operate permission and view permission

* Community

* verify that Environments tab says "Environments is a Go Enterprise feature. Go is currently running in Community mode. Request an Enterprise license "
* verify that on adding environments via cruise config xml it errors out with flash "You have configured the feature, Environments, that is not available in Go Community Edition. Help Topic: Configuration"

* verify that Pipelines tab says "Multiple Pipeline Groups is a Go Enterprise feature. Go is currently running in Community mode. Request an Enterprise license"
* verify that on adding a new pipeline group via cruise config xml it errors out with flash "You cannot have more than one Pipeline group in Go Community Edition. Help Topic: Configuration"

* verify that on editing a pipeline group "Permissions is a Go Enterprise feature. Go is currently running in Community mode. Request an Enterprise license "
* verify that on adding permissions via cruise config xml it errors out with flash "You cannot configure security permissions in Go Community Edition. Help Topic: Configuration"





Teardown of contexts 
* Capture go state "RestrictEnvironmentConfigurationPerLicense-Unit" - teardown


