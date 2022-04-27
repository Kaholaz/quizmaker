#!/bin/bash
error ()
{
    echo "Creating the installer was NOT successful!"
    read  -n 1 -p "Press any key to exit..." _
    exit 1
}

if [ -e src/main/resources/userdata/google-credentials.json ]
then
    if ! mvn clean package shade:shade -DskipTests; then error; fi
    cd target
    echo "Creating installer... this may take a while!"
    if ! jpackage --input . --name Quizmaker --main-jar quizmaker-1.0-beta.jar --main-class org.ntnu.k2.g2.quizmaker.Main --icon classes/gui/media/quiz-logo-transparent.png --vendor k2g2 --copyright k2g2 --type deb --app-version 1.0.0; then error; fi
    echo "Installer sucsessfully created! (check the 'target' folder)"
    read  -n 1 -p "Press any key to exit..." _
    exit 0
else
    echo "'google-credentials.json' not present please refer to the installation guide for help..."
    read  -n 1 -p "Press any key to exit..." _
    exit 1
fi