set SPRING_CONFIG_LOCATION=file:./libs/conf
set logging=./libs/conf/logback.xml

start "b_blind_test" /MIN java -jar -Dlogging.config=%logging% libs/musics-blindtest-1.1.0-SNAPSHOT.war
exit