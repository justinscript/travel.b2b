#!/bin/bash
### by zxc

### BEGIN INIT INFO
# Provides: nginx
# Required-Start: $syslog
# Required-Stop: $syslog
# Default-Stop: 0 1 6
# Short-Description: Start up the nginx server sevice
# Description: zuobian nginx
### END INIT INFO

# Source function library.
. /etc/init.d/functions

RETVAL=0

NGINX_HOME="/zuobian/run/nginx"
NGINX="$NGINX_HOME/sbin/nginx"
NGINX_PID="/zuobian/static/logs/nginx.pid"
start() {
	[ -x $NGINX ] || exit 5

	echo "start nginx: "
	$NGINX -p /zuobian/static/
	RETVAL=$?
	return $RETVAL
}	
stop() {
	echo "Shutting down nginx: "
	killproc -p $NGINX_PID
	RETVAL=$?
	return $RETVAL
}
restart() {
	stop
	start
}	
case "$1" in
  start)
  	start
	;;
  stop)
  	stop
	;;
  restart)
  	restart
	;;
  *)
	echo $"Usage: $0 {start|stop|restart}"
	exit 2
esac

exit $?