# pragmatische_pinguine-hp

This ReadMe is intended for users trying to run the source code.

**If you are trying to run the jars, please refer to the ReadMe in the folder abgabe.**
## How to setup this project in Intellij:
- set the src folder: Roborally/src/**java** needs to be the blue root folder
- set resource folder: Roborally/src/resources needs to be market as resource folder
- set project SDK: Java 17.0.1
- add javafx lib: go to File, Project Structure, Modules, Dependencies, go to plus symbol go to Library, Java then choose JavaFX lib folder. For example: C:\Program Files\Java\javafx-sdk-17.0.0.1\lib
- add the lib folder containing gson as a library
- build project
- try to run src/java/communicaction/client/chatgui/Main (javafx will be missing some modules)
- go to Run, Edit Configurations, Modify Options, add VM-Options: "--module-path "C:\Your\path\to\javafx\lib" --add-modules=javafx.controls,javafx.fxml" with your javafx lib path
- if Project can't find files and you already tried to setup the project once try to clear cashes under File, Invalidate Chaches, Invalidate and Restart