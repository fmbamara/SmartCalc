@ECHO OFF
SET DIR=%~dp0
SET GRADLE_USER_HOME=%DIR%.gradle
java -jar "%DIR%gradle\wrapper\gradle-wrapper.jar" %*