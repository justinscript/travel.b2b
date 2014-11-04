#! /bin/bash

#################################################
#         		左边网java应用发布脚本
#   	(maven对java项目打包,重新启动tomcat服务器）
#
#    V1.0      Writen by: zxc       Date:2014-08-18
##################################################

rm -rf bin deploy out target
path=`pwd`

echo 'cd '$path',and mevan install package,bulid zuobian war'
echo 'cp $JAVA_HOME/jre/lib/ext/sunjce_provider.jar to src/main/webapp/WEB-INF/lib'
cp $JAVA_HOME/jre/lib/ext/sunjce_provider.jar $path/src/main/webapp/WEB-INF/lib/
which mvn
mvn -Dmaven.test.skip=true -DdownloadSources=true eclipse:eclipse clean install
##mvn clean && mvn install package -Dmaven.test.skip=true
mkdir deploy
mkdir deploy/tomcat-server
mkdir deploy/tomcat-server/webapps
cp -r $path/src/main/resources/config/tomcat deploy/conf
mkdir out
mkdir bin
cp target/zuobian.war $path/deploy/tomcat-server/webapps/root.war
cp -r target/zuobian  $path/deploy/tomcat-server/webapps/root
cp src/main/resources/config/linux/* bin/


export JAVA_HOME=/usr/local/java
export TOMCAT_HOME=/usr/local/tomcat
export PRODUCTION_MODE=run

export DEPLOY_HOME=$path/deploy
export OUTPUT_HOME=$path/out
export APP_PORT=29999
export DEBUG_PORT=28888
export WAR_NAME=root.war

export CHECK_STARTUP_URL="http://localhost:29999/ok.htm"
export STARTUP_SUCCESS_MSG="OK"

if [ ! -e $APACHE_HOME ]; then
    echo "********************************************************************"
    echo "**Error: $APACHE_HOME not exist."
    echo "********************************************************************"    
    exit 1
fi

if [ ! -e $JAVA_HOME ]; then
    echo "********************************************************************"
    echo "**Error: $JAVA_HOME not exist"
    echo "********************************************************************"    
    exit 1
fi


JAVA_DEBUG_OPT=" -server -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=$DEBUG_PORT,server=y,suspend=n "
if [ $PRODUCTION_MODE = "run" ]; then
    str=`file $JAVA_HOME/bin/java | perl -pe 's/\n//g' | grep 64-bit`
    if [ -n "$str" ]; then
        memTotal=`cat /proc/meminfo |grep MemTotal|awk '{printf "%d", $2/1024 }'`
        if [ $memTotal -gt 2500 ]; then
            JAVA_MEM_OPTS=" -server -Xms512m -Xmx2g -Xmn128m -XX:PermSize=96m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
        else
            JAVA_MEM_OPTS=" -server -Xmx1g -Xms1g -Xmn256m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
        fi
    else
        JAVA_MEM_OPTS=" -server -Xms1024m -Xmx1024m -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
    fi

	JAVA_OPTS="$JAVA_OPTS $JAVA_MEM_OPTS "
         
elif [ $PRODUCTION_MODE = "test" ]; then
    JAVA_MEM_OPTS=" -server -Xms1024m -Xmx1024m -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
    JAVA_OPTS="$JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPT "
    
elif [ $PRODUCTION_MODE = "dev" ]; then
    JAVA_MEM_OPTS=" -Xms64m -Xmx1024m -XX:MaxPermSize=128m "
    JAVA_OPTS="$JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPT "
    
else
    echo "********************************************************************"
    echo "**Error: $PRODUCTION_MODE should be only: run, test, dev"
    echo "********************************************************************"    
    exit 1
fi

JAVA_OPTS_EXT=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dcatalina.base=$path/deploy -Dcatalina.home=$TOMCAT_HOME -Dwtp.deploy=$path/deploy/tomcat-server/webapps "
JAVA_OPTS="$JAVA_OPTS $JAVA_OPTS_EXT"

export JAVA_OPTS=$JAVA_OPTS
env | grep "webapps"
echo $JAVA_OPTS

chmod -R +x $path/bin/

$path/bin/tomcatctl start
$path/bin/tomcatctl check
