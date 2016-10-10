@ECHO OFF
set JRUBY_BASE=%~dp0\..\jruby
set GEM_HOME=
set GEM_PATH=
set PATH=%JRUBY_BASE%\bin;%PATH%

set JRUBY_OPTS="-J-XX:+TieredCompilation --2.0 -J-XX:TieredStopAtLevel=1 -J-Djruby.compile.invokedynamic=false -J-Djruby.compile.mode=OFF %JRUBY_OPTS%"
%JRUBY_BASE%\bin\jruby.bat %*
