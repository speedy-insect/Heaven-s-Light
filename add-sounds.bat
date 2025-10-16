@echo off
echo Heaven's Light - Sound File Setup
echo ===================================
echo.

set SOUND_DIR=src\main\resources\assets\heavens-light\sounds

echo Checking for required sound files...
echo.

if not exist "%SOUND_DIR%\heavenly_choir.ogg" (
    echo ❌ Missing: heavenly_choir.ogg
    set MISSING=1
) else (
    echo ✅ Found: heavenly_choir.ogg
)

if not exist "%SOUND_DIR%\vision_ambient.ogg" (
    echo ❌ Missing: vision_ambient.ogg
    set MISSING=1
) else (
    echo ✅ Found: vision_ambient.ogg
)

if not exist "%SOUND_DIR%\gentle_harp.ogg" (
    echo ❌ Missing: gentle_harp.ogg
    set MISSING=1
) else (
    echo ✅ Found: gentle_harp.ogg
)

echo.

if defined MISSING (
    echo Some sound files are missing!
    echo.
    echo Please add the following OGG audio files to: %SOUND_DIR%\
    echo - heavenly_choir.ogg ^(soft choir, 10-15 seconds^)
    echo - vision_ambient.ogg ^(gentle ambient, 5-8 seconds^)
    echo - gentle_harp.ogg ^(harp melody, 3-5 seconds^)
    echo.
    echo See %SOUND_DIR%\README.md for detailed specifications.
    echo.
    echo The mod will work without sounds, but the audio experience will be missing.
) else (
    echo ✅ All sound files found! You're ready to build the mod.
    echo.
    echo Run build.bat to compile the mod with sounds included.
)

echo.
pause