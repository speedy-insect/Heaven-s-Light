@echo off
echo Building Heaven's Light Minecraft Mod...
echo.

echo Checking for sound files...
call add-sounds.bat
echo.

echo Cleaning previous build...
call gradlew.bat clean

echo.
echo Building mod...
call gradlew.bat build

echo.
if exist "build\libs\heavens-light-1.0.0.jar" (
    echo Build successful!
    echo Mod JAR created: build\libs\heavens-light-1.0.0.jar
    echo.
    echo To install:
    echo 1. Make sure you have Fabric Loader installed for Minecraft 1.20.4
    echo 2. Download Fabric API from CurseForge or Modrinth
    echo 3. Place both JARs in your mods folder
    echo 4. Launch Minecraft with Fabric profile
) else (
    echo Build failed! Check the output above for errors.
)

echo.
pause