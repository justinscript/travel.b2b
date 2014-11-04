#! /bin/bash

#################################################
#               左边网java应用发布脚本
#       (maven对java项目打包,重新启动tomcat服务器）
#
#    V1.0      Writen by: zxc       Date:2014-08-18
##################################################

path=`pwd`
#svn up project
echo "svn up project"
svn up /root/workspace/com.zb.pet/

echo 'cd '$path',and mevan install package,bulid zuobian war'
echo 'cp $JAVA_HOME/jre/lib/ext/sunjce_provider.jar to src/main/webapp/WEB-INF/lib'
cp $JAVA_HOME/jre/lib/ext/sunjce_provider.jar $path/src/main/webapp/WEB-INF/lib/
which mvn
mvn clean && mvn install package -Dmaven.test.skip=true

echo "shutdown tomcat app"
ps aux|grep tomcat |grep -v grep|grep tomcat|awk '{print $2}'|xargs kill -9
sleep 30
rm -rf /data/run/tomcat/webapps/zuobian*
rm -rf /data/run/tomcat/webapps/ROOT

echo "copy zuobian.war to /tomcat/webapps/"
cp target/zuobian.war /data/run/tomcat/webapps/ROOT.war
/data/run/tomcat/bin/startup.sh
sleep 50
ps aux|grep tomcat |grep -v grep|grep tomcat|awk '{print $2}'|xargs kill -9
sleep 30
find /data/run/tomcat/webapps/ -name "*.vm"|xargs perl -pi -e 's|/static/js/|/js/|g'
find /data/run/tomcat/webapps/ -name "*.vm"|xargs perl -pi -e 's|/static/css/|/css/|g'
find /data/run/tomcat/webapps/ -name "*.vm"|xargs perl -pi -e 's|/static/img/|/img/|g'

echo "svn up /static/ file"
svn up /data/static/style/js/
svn up /data/static/style/css/
svn up /data/static/style/img/

echo "now start tomcat app"


JAVA_DEBUG_OPT=" -server -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=$DEBUG_PORT,server=y,suspend=n "
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
JAVA_OPTS_EXT=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dcomset.reoced.urltime.flag=true "
JAVA_OPTS="$JAVA_OPTS $JAVA_OPTS_EXT"

export JAVA_OPTS=$JAVA_OPTS

/data/run/tomcat/bin/startup.sh

sleep 50
find /data/static/style/ -name "*.js"|xargs perl -pi -e 's|/static/js/|/js/|g'
find /data/static/style/ -name "*.js"|xargs perl -pi -e 's|/static/img/|/img/|g'
find /data/static/style/ -name "*.css"|xargs perl -pi -e 's|/static/img/|/img/|g'
tail -f /data/run/tomcat/webapps/ROOT/logs/zuobian.log
