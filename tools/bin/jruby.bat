set JRUBY_BASE=%~dp0\..\jruby
set GEM_HOME=
set GEM_PATH=
set PATH=%JRUBY_BASE%\bin;%PATH%

%JRUBY_BASE%\bin\jruby.bat %*
