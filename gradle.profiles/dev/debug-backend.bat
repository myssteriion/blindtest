set SPRING_CONFIG_LOCATION=file:./libs/conf
set logging=./libs/conf/logback.xml

java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8001,suspend=y -jar -Dlogging.config=%logging% libs\musics-blindtest-2.0.0.war
pause