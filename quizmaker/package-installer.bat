@echo off
if exist src\main\resources\userdata\google-credentials.json (
    mvn clean package shade:shade -DskipTests || goto ERROR
    cd target
    echo Creating installer... this may take a while!
    jpackage --input . --name Quizmaker --main-jar .\quizmaker-1.0.0.jar --main-class org.ntnu.k2.g2.quizmaker.Main --icon .\classes\gui\media\quiz-logo-transparent.ico  --type exe --win-dir-chooser --vendor k2g2 --copyright k2g2 --win-menu --win-shortcut --win-upgrade-uuid 863281a1-ac73-44de-a77f-d0e6dd1fcd77 --app-version 1.0.0 || goto ERROR
    echo Installer successfully created! Check the 'target' folder...
    echo Press any key to exit...
    pause >nul
    goto:eof rem Exits the file!
) else (
    echo 'google-credentials.json' not present please refer to the installation guide for help...
    echo Press any key to exit...
    pause >nul
    exit /b 1
)

:ERROR
echo Creating the installer was NOT successful!
echo Press any key to exit...
pause >nul
exit /b 2
