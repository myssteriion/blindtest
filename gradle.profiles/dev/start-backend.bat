set SPRING_CONFIG_LOCATION=file:./libs/conf
set logging=./libs/conf/logback.xml

java -jar -Dlogging.config=%logging% libs/blindtest-1.0.war
pause