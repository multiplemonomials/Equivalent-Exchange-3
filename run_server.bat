@echo off
rem for machines with java 8, use java 7 as 8 doesn't work for MC right now
set PATH=C:\Program Files\Java\jdk1.7.0_51\bin;%PATH%
set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_51
gradle runServer
pause