set SPRING_CONFIG_LOCATION=file:./libs/conf
set logging=./libs/conf/logback.xml

start "b_musics_blindtest" /MIN java -jar -Dlogging.config=%logging% libs\musics-blindtest-2.0.1.war
exit