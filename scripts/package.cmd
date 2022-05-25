@echo off
FOR /F "tokens=* delims=*" %%x in (JAVA_HOME.txt) DO set JAVA_HOME="%~dp0%%x"
cd ..
.\mvnw package
