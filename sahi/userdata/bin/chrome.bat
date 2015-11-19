@ECHO OFF
if "%1"=="" goto ERROR
SET SAHI_HOME=..\..
SET SCRIPTS_PATH=scripts/%1
SET BROWSER=C:\Documents and Settings\%Username%\Local Settings\Application Data\Google\Chrome\Application\chrome.exe
SET BROWSER_PROCESS=chrome.exe
SET BROWSER_OPTION=--user-data-dir=$userDir\browser\chrome\profiles\sahi$threadNo --proxy-server=localhost:9999 --disable-popup-blocking 
SET START_URL=http://gramam/demo/
SET THREADS=30
SET LOG_DIR=default
java -cp %SAHI_HOME%\lib\ant-sahi.jar net.sf.sahi.test.TestRunner %SCRIPTS_PATH% "%BROWSER%" %START_URL% %LOG_DIR% localhost 9999 %THREADS% %BROWSER_PROCESS% "%BROWSER_OPTION%"
goto :EOF

:ERROR
echo "Usage: chrome.bat <sah file|suite file>"
echo "File path is relative to userdata/scripts"
echo "Example:" 
echo "chrome.bat demo/demo.suite"
echo "chrome.bat demo/sahi_demo.sah"