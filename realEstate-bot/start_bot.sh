#!/bin/sh

MY_HOME=./bot
MY_CLASSPATH=$MY_HOME
MY_CLASSPATH=$MY_CLASSPATH:$MY_HOME/lib/c3p0-0.9.1.1.jar
MY_CLASSPATH=$MY_CLASSPATH:$MY_HOME/lib/log4j-1.2.17.jar
MY_CLASSPATH=$MY_CLASSPATH:$MY_HOME/lib/log4j-api-2.1.jar
MY_CLASSPATH=$MY_CLASSPATH:$MY_HOME/lib/log4j-core-2.1.jar
MY_CLASSPATH=$MY_CLASSPATH:$MY_HOME/lib/slf4j-api-1.7.10.jar
MY_CLASSPATH=$MY_CLASSPATH:$MY_HOME/lib/slf4j-log4j12-1.7.10.jar
MY_CLASSPATH=$MY_CLASSPATH:$MY_HOME/lib/quartz-2.2.1.jar
MY_CLASSPATH=$MY_CLASSPATH:$MY_HOME/lib/realEstate-bot-0.0.1.jar

#nohup usr/bin/java -Xmx512m -Xms256m -classpath $MY_CLASSPATH com.contetial.realEstate.bot.jobcontroller.JobRunner &

nohup java -classpath $MY_CLASSPATH com.contetial.realEstate.bot.jobcontroller.JobRunner &
