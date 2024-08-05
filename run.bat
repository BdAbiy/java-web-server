@echo off 
cls
echo updating classes ...
echo main.class
javac main.java
echo Webserver.class
javac Webserver.java
echo done
timeout /t 5 /nobreak
@echo off
echo running main class...
cls
java main

