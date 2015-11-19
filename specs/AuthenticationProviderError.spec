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

AuthenticationProviderError
===========================

Setup of contexts
* Invalid ldap configuration - setup
* Capture go state "AuthenticationProviderError" - setup

AuthenticationProviderError
---------------------------

tags: 6441, #6441, manual

DNS error for Ldap server
* Login as "anything" should fail with message "Failed to authenticate with your authentication provider. Please check if your authentication provider is up and available to serve requests. Help Topic: Authentication"





Teardown of contexts
* Capture go state "AuthenticationProviderError" - teardown
* Invalid ldap configuration - teardown


