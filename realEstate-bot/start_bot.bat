set MY_HOME=./bot
set MY_CLASSPATH=%MY_HOME%
set MY_CLASSPATH=%MY_CLASSPATH%;%MY_HOME%/lib/c3p0-0.9.1.1.jar
set MY_CLASSPATH=%MY_CLASSPATH%;%MY_HOME%/lib/log4j-1.2.17.jar
set MY_CLASSPATH=%MY_CLASSPATH%;%MY_HOME%/lib/log4j-api-2.1.jar
set MY_CLASSPATH=%MY_CLASSPATH%;%MY_HOME%/lib/log4j-core-2.1.jar
set MY_CLASSPATH=%MY_CLASSPATH%;%MY_HOME%/lib/slf4j-api-1.7.10.jar
set MY_CLASSPATH=%MY_CLASSPATH%;%MY_HOME%/lib/slf4j-log4j12-1.7.10.jar
set MY_CLASSPATH=%MY_CLASSPATH%;%MY_HOME%/lib/quartz-2.2.1.jar
set MY_CLASSPATH=%MY_CLASSPATH%;%MY_HOME%/lib/realEstate-bot-0.0.1.jar

java -Xmx512m -Xms256m -classpath %MY_CLASSPATH% com.contetial.realEstate.bot.jobcontroller.JobRunner >bot.log
