set SPRING_CONFIG_LOCATION=file:./libs/conf
set logging=./libs/conf/logback.xml

java -jar -Dlogging.config=%logging% libs\musics-blindtest-2.1.0-SNAPSHOT.war
pause