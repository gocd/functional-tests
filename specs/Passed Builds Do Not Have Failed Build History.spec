Passed Builds Do Not Have Failed Build History
==============================================

Setup of contexts
* Basic configuration - setup
* Using pipeline "junit-tests" - setup
* With "1" live agents in directory "StageDetailsShineIntegration" - setup
* Capture go state "Passed builds do not have failed build history" - setup

Passed Builds Do Not Have Failed Build History
----------------------------------------------

tags: fbh, shine, bisect


Smoke test/happy path to check that passed stages do not get a failed stage history.

* With material named "junit-tests-material" in pipeline "junit-tests"
* Commit file "junit-failures/pass-a-pass-b/TEST-cruise.testing.JUnit.xml" to directory "junit-output" as user "user-1"

* Looking at pipeline "junit-tests"
* Trigger pipeline
* Verify stage "1" is "Building" on pipeline with label "1"

* Stop "1" jobs that are waiting for file to exist

* On Pipeline Dashboard Page
* Looking at pipeline "junit-tests"
* Wait for first stage to pass with pipeline label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to tests tab
* Wait for shine feed to update
* Verify total runs "2" failures "0" errors "0"





Teardown of contexts
* Capture go state "Passed builds do not have failed build history" - teardown
* With "1" live agents in directory "StageDetailsShineIntegration" - teardown
* Using pipeline "junit-tests" - teardown
* Basic configuration - teardown


