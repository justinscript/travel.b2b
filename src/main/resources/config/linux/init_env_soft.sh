#! /bin/bash

#################################################
#         左边网服务器系统环境初始化
#   (初始化环境配置,安装基础软件,配置环境变量）
#
#    V1.0      Writen by: zxc       Date:2014-08-18
##################################################


# update system 
zuobian_source='/data/run/source/'
sshd_config='/etc/ssh/sshd_config'
sysctl_conf='/etc/sysctl.conf'
limits_conf='/etc/security/limits.conf'
zuobian_profile_sh='/etc/profile.d/zuobian.sh'
selinux_config='/etc/selinux/config'
sysconfig_network='/etc/sysconfig/network'
rsyslog_conf='/etc/rsyslog.conf'
postfix_main_cf='/etc/postfix/main.cf'
zuobian='/data'
zuobian_static='/data/static'
zuobian_run='/data/run'
zuobian_run_static='/data/run/static'
app_user_passwd='zuobian_app_pwd_1@'

function to_continue {
    echo 'entry any key to continue...'
    read c_continue
}
function apt_yum_install_tools {
    yum install subversion vim curl tree 
}
function go_or_exit {
    echo 'do you want to continue? y/n'
    read ans
    while [ "$ans" == '' ]
    do
        read ans
    done
    if [ "$ans" == 'y' ] || [ "$ans" == 'yes' ]; then
        echo 'continue...'    
    fi
    if [ "$ans" == 'n' ] || [ "$ans" == 'no' ]; then
        echo 'exit on your command'
        exit 1
    fi
}
function check_and_ask {
    if [ $? != 0 ]; then
        echo "$? not equal zero, something error"
        go_or_exit
    fi
}
function yum_update {
    yum groupinstall -y 'Development tools' 'Additional Development' 'Server Platform Development'
    #yum update -y
    yum install expect.x86_64 gd-devel.x86_64 tree.x86_64 -y
}
function backup_first {
    for i in ${sshd_config} ${sysctl_conf} ${limits_conf}
    do
        cp $i $i".orig"
    done
}
function permitRootLogin_no {
    permit_root_login_count=`egrep -c '^#?PermitRootLogin ' ${sshd_config}`
    if [ ${permit_root_login_count} -le 0 ]; then
        echo 'PermitRootLogin no' >> ${sshd_config}
    fi
    if [ ${permit_root_login_count} -eq 1 ]; then
        sed -i 's/^PermitRootLogin yes$\|^#PermitRootLogin yes$/PermitRootLogin no/g' ${sshd_config}
    fi
    permit_root_login_prefix_count=`egrep -c '^PermitRootLogin ' ${sshd_config}`
    permit_root_login_no_count=`egrep -c '^PermitRootLogin no$' ${sshd_config}`
    if [ ${permit_root_login_prefix_count} -ne 1 ] || [ ${permit_root_login_no_count} -ne 1 ]; then
        echo 'PermitRootLogin is confuse, see below'
        egrep '^#?PermitRootLogin ' ${sshd_config}
        go_or_exit
    fi
    service sshd restart
}
# see http://wiki.eclipse.org/Jetty/Howto/High_Load for more detail
function set_sysctl {
    for i in net.core.rmem_max net.core.wmem_max net.ipv4.tcp_rmem net.ipv4.tcp_wmem net.core.somaxconn net.core.netdev_max_backlog net.ipv4.tcp_max_syn_backlog net.ipv4.tcp_syncookies net.ipv4.ip_local_port_range net.ipv4.tcp_tw_recycle
    do
        net_sysctl_count=`grep -ci $i ${sysctl_conf}`
        if [ ${net_sysctl_count} -gt 0 ]; then
            echo $i is set, see below
            grep -i $i ${sysctl_conf}
                    go_or_exit
        fi
    done

    first_time=0
    for i in 'net.core.rmem_max = 16777216' 'net.core.wmem_max = 16777216' 'net.ipv4.tcp_rmem = 4096 87380 16777216' 'net.ipv4.tcp_wmem = 4096 16384 16777216' 'net.core.somaxconn = 4096' 'net.core.netdev_max_backlog = 16384' 'net.ipv4.tcp_max_syn_backlog = 8192' 'net.ipv4.tcp_syncookies = 1' 'net.ipv4.ip_local_port_range = 1024 65535' 'net.ipv4.tcp_tw_recycle = 1'
    do
        reg=`echo $i | perl -pe 's/ = /\\\\s*=\\\\s*/'`
        net_sysctl_count=`egrep -ci "^${reg}$" ${sysctl_conf}`
        if [ ${net_sysctl_count} -gt 0 ]; then
            echo "$i is set, see below"
            grep -i "$i" ${sysctl_conf}
                    go_or_exit
            continue
        fi
        if [ ${first_time} -eq 0 ]; then
            echo '' >> ${sysctl_conf}
            echo '' >> ${sysctl_conf}
            echo "#=================data network performance tune=================#" >> ${sysctl_conf}
            first_time=1
        fi
        echo $i >> ${sysctl_conf}
    done
    sysctl -p ${sysctl_conf}
}
function set_limits {
    admin_group_limit_count=`egrep -c '^@admin' ${limits_conf}`
    if [ ${admin_group_limit_count} -gt 0 ] ; then
        echo 'admin group limit count is confuse, see below'
        egrep -c '^@admin' ${limits_conf}
        go_or_exit
    fi
    echo '' >> ${limits_conf}
    echo '' >> ${limits_conf}
    echo "#=================zuobian limits=================#" >> ${limits_conf}
    echo '@admin           soft    nproc           2047' >> ${limits_conf}
    echo '@admin           hard    nproc           16384' >> ${limits_conf}
    echo '@admin           soft    nofile          1024' >> ${limits_conf}
    echo '@admin           hard    nofile          65536' >> ${limits_conf}
    echo '' >> ${limits_conf}
}
function congestion_control {
    cubic_count=`sysctl net.ipv4.tcp_available_congestion_control | grep -c cubic`
    if [ ${cubic_count} -eq 0 ] ; then
        echo 'sysctl net.ipv4.tcp_available_congestion_contro algorithms do not contain cubic, see below'
        sysctl net.ipv4.tcp_available_congestion_control
        go_or_exit
    fi
}
function run_profile {
    if [ -e ${zuobian_profile_sh} ] ; then
        echo "${zuobian_profile_sh} alread exist, delete and rewrite ? "
        cat ${zuobian_profile_sh}
        go_or_exit
    fi
cat > ${zuobian_profile_sh} <<EOF
# zuobian inc env

# java
export JAVA_HOME=/data/run/jdk
export PATH=\$JAVA_HOME/bin:\$PATH

# maven
export MAVEN_HOME=/data/run/maven
export PATH=\${MAVEN_HOME}/bin:\$PATH
EOF
. ${zuobian_profile_sh}
}
function install_common_libs_command {
    cd ${zuobian_source}
    timestamp=`date +%s`
    compile_dir="/tmp/${timestamp}"
    if [ -e ${compile_dir} ]; then
        rm -rf ${compile_dir}
    fi
    mkdir ${compile_dir}    
    for i in vnstat-1.11.tar.gz libevent-2.0.16-stable.tar.gz openssl-1.0.0e.tar.gz pcre-8.20.tar.gz
    do
        tar -xf $i -C ${compile_dir}
    done
    for i in libevent-2.0.16-stable openssl-1.0.0e pcre-8.20 vnstat-1.11
    do
        cd "${compile_dir}/${i}"
        if [ $i == 'openssl-1.0.0e' ]; then
            ./config && make && make test && make install
            check_and_ask
            continue
        fi
        if [ $i == 'vnstat-1.11' ]; then
            make && make install
            check_and_ask
            continue
        fi
        ./configure && make && make install
        check_and_ask
    done
}
function set_host_name {
    #host_name=`ifconfig | grep 'inet addr:' | grep '192.168' | awk '{print $2}' | awk -F \. '{print "server"$NF}'`
    host_name='zuobian'
    sed -i "s/^HOSTNAME=.\+/HOSTNAME=${host_name}/g" ${sysconfig_network}
    hostname_count=`egrep -c '^HOSTNAME=' ${sysconfig_network}`
    new_hostname_count=`egrep -c "^HOSTNAME=${host_name}$" ${sysconfig_network}`
    if [ ${hostname_count} -ne 1 ] || [ ${new_hostname_count} -ne 1 ]; then
        echo "${sysconfig_network} is confuse, see below"
        cat ${sysconfig_network}
        go_or_exit
    fi
    hostname ${host_name}    
}
function disable_selinux {
    
    sed -i 's/^SELINUX=enforcing$\|^SELINUX=permissive$/SELINUX=disabled/g' ${selinux_config}
    selinux_count=`egrep -c '^SELINUX=' ${selinux_config}`
    selinux_disabled_count=`egrep -c '^SELINUX=disabled$' ${selinux_config}`
    if [ ${selinux_count} -ne 1 ] || [ ${selinux_disabled_count} -ne 1 ]; then
        echo 'SELINUX is confuse, see below'
        egrep 'SELINUX ' ${selinux_config}
        go_or_exit
    fi
    echo 0 >/selinux/enforce    
}
function stop_iptable_temporary {
    service iptables stop
}
function ntp_date_hourly {
    service ntpdate start
    cat > /etc/cron.daily/ntpdate <<EOF
#!/bin/sh
service ntpdate start
EOF
    chmod +x /etc/cron.daily/ntpdate
}
function add_apps_user {
    for i in rhine bops nisa warrior
    do
        mkdir -p ${zuobian}/${i}
        adduser ${i} -g admin -d ${zuobian}/${i}
        echo ${app_user_passwd} | passwd ${i} --stdin
        chown -R ${i}.admin ${zuobian}/${i}
        chmod 755 ${zuobian}/${i}
    done
    usermod -G admin nobody
    if [ $? != 0 ]; then
        echo 'usermod -G admin nobody error'
        go_or_exit    
    fi
}
function cp_zuobian_run_static {
    cp -r ${zuobian_run_static} ${zuobian}
    chown -R admin.admin ${zuobian_static}
    chown -R nobody.nobody "${zuobian_static}/logs"
}
function init_d {
    for i in memcached nginx manage_agent
    do
        cp "${zuobian_run}/bin/${i}" /etc/init.d/
        chkconfig --add ${i}
        chkconfig --level 3 ${i} on
        chkconfig --level 5 ${i} on
        chkconfig --list ${i}
        service ${i} start
    done
}
function e_vimrc {
    cat >> /etc/vimrc <<EOF

set expandtab
set softtabstop=4
set shiftwidth=4
EOF
}
function nfs_server {
    echo 'do you need config nfs server? y/n'
    read ans
    while [ "$ans" == '' ]
    do
        read ans
    done
    if [ "$ans" == 'y' ] || [ "$ans" == 'yes' ]; then
        mkdir -p /zuobian/nfs
        chown nfsnobody.nfsnobody /zuobian/nfs
        cat >> /etc/exports <<EOF
/zuobian/nfs 192.168.1.1/255.255.255.0(rw,all_squash,anonuid=65534,anongid=65534)
EOF
        chkconfig --level 3 nfs on
        chkconfig --level 5 nfs on
        chkconfig --list nfs 
        service nfs restart
    fi
    if [ "$ans" == 'n' ] || [ "$ans" == 'no' ]; then
        echo 'it seems you do not need nfs server'
    fi
}
function nfs_client {
    #cat >> /etc/fstab <<EOF
#192.168.1.101:/zuobian/nfs /zuobian/static/nfs    nfs rsize=8192,wsize=8192,timeo=14,intr
#EOF
    #mount -a
    echo 'empty'
}
function rsync_server {
    echo 'do you need config rsync server? y/n'
    read ans
    while [ "$ans" == '' ]
    do
        read ans
    done
    if [ "$ans" == 'y' ] || [ "$ans" == 'yes' ]; then
        cp "${zuobian_run}/bin/rsyncd" /etc/init.d/
        cp "${zuobian_run}/bin/rsyncd.conf" /etc/
        chmod +x /etc/init.d/rsyncd
        cat > /etc/rsyncd.secrets <<EOF
#!/bin/sh
zuobian_rsync:zuobian_rsync
EOF
        chmod 600 /etc/rsyncd.secrets
        chkconfig --add rsyncd
        chkconfig --level 3 rsyncd on
        chkconfig --level 5 rsyncd on
        chkconfig --list rsyncd
        service rsyncd start
    fi
    if [ "$ans" == 'n' ] || [ "$ans" == 'no' ]; then
        echo 'it seems you do not need rsync server'
    fi
}
function rsync_client {
    echo 'do you need config rsync client? y/n'
    read ans
    while [ "$ans" == '' ]
    do
        read ans
    done
    mkdir -p /zuobian/rsync/nfs_img
    chown -R nobody.nobody /zuobian/rsync/
    if [ "$ans" == 'y' ] || [ "$ans" == 'yes' ]; then
        cat > /etc/cron.hourly/rsync <<EOF
#!/bin/sh
export RSYNC_PASSWORD='zuobian_rsync'
rsync -av zuobian_rsync@192.168.1.101::nfs_img /zuobian/rsync/nfs_img
EOF
    fi
    if [ "$ans" == 'n' ] || [ "$ans" == 'no' ]; then
        echo 'it seems you do not need rsync client'
    fi
}
function app_rsyslog_conf {
    echo 'do you need config rsyslog.conf? y/n'
    read ans
    while [ "$ans" == '' ]
    do
        read ans
    done
    if [ "$ans" == 'y' ] || [ "$ans" == 'yes' ]; then
        mkdir -p /zuobian/warrior/data/logger/
        chown -R warrior.admin /zuobian/warrior/data/
        cat >> ${rsyslog_conf} <<'EOF'

# our app log, notice that log below RileOwner will set to user warrior !
$template DailyAppLogs,"/zuobian/warrior/data/logger/app-%$YEAR%%$MONTH%%$DAY%.log"
$FileOwner warrior
local0.*                                                -?DailyAppLogs
EOF
    service rsyslog restart
    fi
    if [ "$ans" == 'n' ] || [ "$ans" == 'no' ]; then
        echo 'it seems you do not need config rsyslog.conf'
    fi
}
function config_mail {
    echo 'do you need config postfix /etc/postfix/main.cf ? y/n'
    read ans
    while [ "$ans" == '' ]
    do
        read ans
    done
    if [ "$ans" == 'y' ] || [ "$ans" == 'yes' ]; then
        yum install cyrus-sasl.x86_64 cyrus-sasl-plain.x86_64 cyrus-sasl-md5.x86_64
        service saslauthd start
        chkconfig --level 3 saslauthd on
        chkconfig --level 5 saslauthd on
        chkconfig saslauthd --list
        cat >> ${postfix_main_cf} <<'EOF'

myhostname = zuobian-inc.com
alias_maps = hash:/etc/aliases
alias_database = hash:/etc/aliases
mydestination = $myhostname, localhost.$mydomain, localhost
relayhost =
mynetworks = 127.0.0.0/8 [::ffff:127.0.0.0]/104 [::1]/128
mailbox_size_limit = 0
recipient_delimiter = +
inet_interfaces = all

home_mailbox = Maildir/
smtpd_sasl_auth_enable = yes
smtpd_sasl_security_options = noanonymous
smtpd_sasl_local_domain = $myhostname
broken_sasl_auth_clients = yes

smtpd_sender_restrictions = permit_sasl_authenticated,
  permit_mynetworks,
smtpd_recipient_restrictions = permit_mynetworks,
  permit_sasl_authenticated, reject_unauth_destination, reject_unknown_sender_domain,


EOF
    service postfix restart
    adduser mailadmin
    echo '123456' | passwd mailadmin --stdin
    chown -R mailadmin.mailadmin /home/mailadmin

    fi
    if [ "$ans" == 'n' ] || [ "$ans" == 'no' ]; then
        echo 'it seems you do not need config postfix'
    fi
}
function main {
    disable_selinux
    stop_iptable_temporary
    to_continue
    ntp_date_hourly
    to_continue
    set_host_name
    to_continue
    backup_first
    to_continue
    yum_update
    to_continue
    apt_yum_install_tools
    to_continue
    #permitRootLogin_no
    to_continue
    set_sysctl
    to_continue
    set_limits
    to_continue
    congestion_control
    to_continue
    run_profile
    to_continue
    install_common_libs_command
    cp_zuobian_run_static
    to_continue
    e_vimrc
    to_continue
    exit 128
    to_continue
    add_apps_user
    to_continue
    cp_zuobian_run_static
    to_continue
    init_d
    to_continue
    e_vimrc
    to_continue
    nfs_server
    to_continue
    nfs_client
    to_continue
    rsync_server
    to_continue
    rsync_client
    to_continue
    app_rsyslog_conf
    to_continue
    config_mail
}
main