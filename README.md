[![Gauge
Badge](https://cdn.rawgit.com/getgauge/getgauge.github.io/master/Gauge_Badge.svg)](http://getgauge.io)

## Pre-Requisites
* Java
* Maven
* [Node](https://nodejs.org/en/) 
* [Gauge](http://getgauge.io)

## Setup
* ``` git clone``` as a sibling directory to
  [go.cd](https://github.com/gocd/gocd)
* ```$ cd gocd-functional-tests```
* ```$ gauge --install-all```

## Prepare
* cd to ```gocd-plugins``` codebase, run : ```$ mvn clean install -DskipTests```
* cd to ```gocd``` codebase, run : ```$ ./bn clean
  cruise:prepare dist ALLOW_NON_PRODUCTION_CODE=yes```
* cd to ```gocd-functional-tests``` and run : ```$ ./b clean setup```
* ```./b start_server``` 

## Running tests

* ```gauge specs/AdminTaskListing.spec```
