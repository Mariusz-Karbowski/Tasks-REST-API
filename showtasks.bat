call runcrud
if "%ERRORLEVEL%" == "0" goto showTasksInChrome
echo.
echo runcrud.bat has errors - breaking work
goto fail

:showTasksInChrome
start chrome "http://localhost:8080/crud/v1/task/getTasks"
if "%ERRORLEVEL%" == "0" goto end
echo.
echo Cannot open Chrome - breaking work

:fail
echo.
echo Errors occured.

:end
echo.
echo showtasks.bat finished its work.
