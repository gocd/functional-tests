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

MaterialCheckConnectivity
=========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "MaterialCheckConnectivity" - setup

MaterialCheckConnectivity
-------------------------

tags: 5018, Clicky admin

* Click on pipeline "edit-pipeline" for editing

* Open material listing page

* Edit material "hg-material"

* Check connectivity should be successful - Already on Mercurial Material Creation Popup
* Close - Already on Mercurial Material Creation Popup

* Remember url for material "hg-material" of pipeline "edit-pipeline"
* Add parameter "url" to pipeline "edit-pipeline"

* On Pipeline Configuration wizard
* Click on pipeline "edit-pipeline" for editing

* Open material listing page

* Edit material "hg-material"

* Enter url "#{url}" - Already on Mercurial Material Creation Popup
* Check connectivity should be successful - Already on Mercurial Material Creation Popup
* Enter url "foo" - Already on Mercurial Material Creation Popup
* Check connectivity should fail






Teardown of contexts
* Capture go state "MaterialCheckConnectivity" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


