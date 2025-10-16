@echo off
echo Setting up Heaven's Light development environment...
echo.

echo Generating IDE files...
call gradlew.bat genEclipseRuns genIntellijRuns

echo.
echo Generating sources for better IDE support...
call gradlew.bat genSources

echo.
echo Development setup complete!
echo.
echo To run in development:
echo - Use "gradlew.bat runClient" to test the mod
echo - Use "gradlew.bat runServer" to test server functionality
echo.
echo IDE Integration:
echo - IntelliJ IDEA: Import the project as a Gradle project
echo - Eclipse: Import using the generated Eclipse files
echo.
pause