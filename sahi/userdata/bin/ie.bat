@ECHO OFF
if "%1"=="" goto ERROR
SET SAHI_HOME=..\..
SET SCRIPTS_PATH=scripts/%1
SET BROWSER=C:\Program Files\Internet Explorer\IEXPLORE.EXE
SET BROWSER_PROCESS=iexplore.exe
SET BROWSER_OPTION=-noframemerging
SET START_URL=http://sahi.co.in/demo/
SET THREADS=3
SET LOG_DIR=default
%SAHI_HOME%\tools\toggle_IE_proxy.exe enable
java -cp %SAHI_HOME%\lib\ant-sahi.jar net.sf.sahi.test.TestRunner %SCRIPTS_PATH% "%BROWSER%" %START_URL% %LOG_DIR% localhost 9999 %THREADS% %BROWSER_PROCESS% "%BROWSER_OPTION%"
%SAHI_HOME%\tools\toggle_IE_proxy.exe disable
goto :EOF

:ERROR
echo "Usage: ie.bat <sah file|suite file>"
echo "File path is relative to userdata/scripts"
echo "Example:" 
echo "ie.bat demo/demo.suite"
echo "ie.bat demo/sahi_demo.sah"