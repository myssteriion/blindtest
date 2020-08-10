set SPRING_CONFIG_LOCATION=file:./libs/conf
set logging=./libs/conf/logback.xml

java -jar -Dlogging.config=%logging% libs\musics-blindtest-3.0.0-SNAPSHOT.war
pause