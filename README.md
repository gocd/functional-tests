[![Gauge
Badge](https://cdn.rawgit.com/getgauge/getgauge.github.io/master/Gauge_Badge.svg)](https://getgauge.io)

## Pre-Requisites
* JDK 8 (verified on 1.8.0_121-b13)
* Gradle (verified to work on 3.4)
* Maven (verified to work on 3.3.9)
* [Node](https://nodejs.org/en/) 6.x (7.x will not work)
* [Gauge](https://getgauge.io) 0.9.8
* Mercurial
* Ant
* Git
* **Firefox <= 45.x** (verified on 45.8.0esr; the version of webdriver used only supports up to 45)
* If running specs related to other SCM repos like TFS, SVN, Perforce ensure they are installed on the machine executing the specs

## Setup
* Need the following repos cloned as sibling directories to this repo:
    * [go.cd](https://github.com/gocd/gocd)
    * [go-plugins](https://github.com/gocd/go-plugins)
* ```$ cd functional-tests```
* ```$ gauge --install-all```
* ```$ gauge --update-all``` (in case the plugins were already installed before the previous command, update them to the latest anyway)
* If you are using `nvm`, verify that Node 6.x is activated
* Modfiy the paths to `firefox.exe` or `firefox-bin` in `src/test/java/twist.properties` (for both the `sahi.browserLocation` and `webdriver.firefox.bin` properties) to reflect the paths on your machine

## Clean/setup
* Build GoCD master repo and `go-plugins` repo from their respective folders
```bash
# This will prepare the functional tests related artifacts in the functional tests folder
$ cd functional-tests
$ rake clean setup
```
## Running tests

```bash
$ GO_VERSION=X.x.x gauge specs/AdminTaskListing.spec
```

## Contributing

We encourage you to contribute to GoCD. For information on contributing to this project, please see our [contributor's guide](https://www.gocd.org/contribute).
A lot of useful information like links to user documentation, design documentation, mailing lists etc. can be found in the [resources](https://www.gocd.org/community/resources.html) section.


## License

```plain
Copyright 2017 ThoughtWorks, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
