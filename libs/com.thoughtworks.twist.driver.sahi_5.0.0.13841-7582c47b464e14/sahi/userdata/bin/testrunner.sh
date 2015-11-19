#!/bin/bash
if [ $# -ne 3 ] 
then
echo "Usage: testrunner.sh <sah file|suite file> <startURL> <browserType>"
echo "File path is relative to userdata/scripts"
echo "Example:" 
echo "./testrunner.sh demo/demo.suite http://sahipro.com/demo/ firefox"
echo "./testrunner.sh demo/sahi_demo.sah http://sahipro.com/demo/ chrome"
else

export SAHI_HOME=../..
export USERDATA_DIR=../
export SCRIPTS_PATH=scripts/$1
export BROWSER=$3
export START_URL=$2
export THREADS=5
export SINGLE_SESSION=false
java -cp $SAHI_HOME/lib/ant-sahi.jar net.sf.sahi.test.TestRunner -test $SCRIPTS_PATH -browserType "$BROWSER" -baseURL $START_URL -host localhost -port 9999 -threads $THREADS -useSingleSession $SINGLE_SESSION
fi
