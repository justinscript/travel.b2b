#! /bin/bash

#################################################
#         左边网服务器系统开发环境安装
#   (初始化开发环境,安装相关开发软件,服务器）
#
#    V1.0      Writen by: zxc       Date:2014-08-18
##################################################

zuobian='/data'
zuobian_run='/data/run'
zuobian_run_bin='/data/run/bin'
zuobian_source='/data/run/source'
zuobian_binary='/data/run/binary'

function pre_check {
    if [ ! -e ${zuobian_run} ] || [ ! -e ${zuobian_source} ] || [ ! -e ${zuobian_binary} ]; then
        echo "ERROR"
        exit 1
    fi
}
function init_run_apps_env {

    for i in maven tomcat jdk nginx
    do
        if [ -e ${zuobian_run}/${i} ]; then
            rm -rf ${zuobian_run}/${i}
        fi
    done
    for i in apache-maven-3.0.5 apache-tomcat-7.0.54 jdk1.7.0_55 nginx-1.7.1
    do
        if [ -e ${zuobian_binary}/${i} ]; then
            rm -rf ${zuobian_binary}/${i}
        fi
    done
    if [ -e ${zuobian_source}/jdk1.7.0_55 ]; then
        rm -rf ${zuobian_source}/jdk1.7.0_55
    fi

    tar -xf ${zuobian_source}/apache-maven-3.0.5-bin.tar.gz -C ${zuobian_binary}
    ln -s ${zuobian_binary}/apache-maven-3.0.5 ${zuobian_run}/maven
    cp ${zuobian_run}/maven/conf/settings.xml ${zuobian_run}/maven/conf/settings.xml.orgi
    ##cp ${zuobian_run_bin}/settings.xml ${zuobian_run}/maven/conf

    tar -xf ${zuobian_source}/apache-tomcat-7.0.54.tar.gz -C ${zuobian_binary}
    ln -s ${zuobian_binary}/apache-tomcat-7.0.54 ${zuobian_run}/tomcat

    tar -xf ${zuobian_source}/jdk-7u55-linux-x64.tar.gz -C ${zuobian_binary}
    ln -s ${zuobian_binary}/jdk1.7.0_55 ${zuobian_run}/jdk

    timestamp="init_env_"`date +%s`
    compile_dir="/tmp/${timestamp}"
    if [ -e ${compile_dir} ]; then
        rm -rf ${compile_dir}
    fi
    mkdir ${compile_dir}

    tar -xf ${zuobian_source}/nginx-1.7.1.tar.gz -C ${compile_dir}
    cd ${compile_dir}/nginx-1.7.1
    ./configure   --without-mail_pop3_module   --without-mail_imap_module   --without-mail_smtp_module  --prefix=${zuobian_binary}/nginx-1.7.1  --with-http_gzip_static_module  --with-http_stub_status_module&& make && make install && ln -s ${zuobian_binary}/nginx-1.7.1 ${zuobian_run}/nginx
    cd -

}

function main {
    pre_check
    init_run_apps_env
}
main