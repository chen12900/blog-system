@echo off
set "MAVEN_PROJECTBASEDIR=%~dp0"
set "MAVEN_HOME=%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.9.7"
if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
    echo Downloading Maven...
    mkdir "%MAVEN_HOME%\.." 2>nul
    curl -k -L -o "%TEMP%\maven.zip" "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.7/apache-maven-3.9.7-bin.zip"
    powershell -Command "Expand-Archive -Path '%TEMP%\maven.zip' -DestinationPath '%MAVEN_HOME%\..' -Force"
    del "%TEMP%\maven.zip"
)
set "PATH=%MAVEN_HOME%\bin;%PATH%"
set "MAVEN_OPTS=-Xmx1024m -XX:MaxMetaspaceSize=256m"
call mvn -DskipTests=true %*
