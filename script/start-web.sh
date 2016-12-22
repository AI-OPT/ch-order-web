#!/bin/sh
#env
APP_NAME="slp.order.web"

#set base home
RESOURCES_HOME=${CATALINA_HOME}/webapps/ROOT/WEB-INF/classes

#change config
pushd ${RESOURCES_HOME}
sed -i "s%casServerLoginUrl=.*%casServerLoginUrl=${casServerLoginUrl}%g" ./sso.properties
sed -i "s%casServerUrlPrefix=.*%casServerUrlPrefix=${casServerUrlPrefix}%g" ./sso.properties
sed -i "s%serverName=.*%serverName=${serverName}%g" ./sso.properties
sed -i "s%logOutServerUrl=.*%logOutServerUrl=${logOutServerUrl}%g" ./sso.properties
sed -i "s%logOutBackUrl=.*%logOutBackUrl=${logOutBackUrl}%g" ./sso.properties
sed -i "s%casServerLoginUrl_Inner=.*%casServerLoginUrl_Inner=${casServerLoginUrl_Inner}%g" ./sso.properties
sed -i "s%casServerUrlPrefix_Inner=.*%casServerUrlPrefix_Inner=${casServerUrlPrefix_Inner}%g" ./sso.properties
sed -i "s%serverName_Inner=.*%serverName_Inner=${serverName_Inner}%g" ./sso.properties
sed -i "s%logOutServerUrl_Inner=.*%logOutServerUrl_Inner=${logOutServerUrl_Inner}%g" ./sso.properties
sed -i "s%logOutBackUrl_Inner=.*%logOutBackUrl_Inner=${logOutBackUrl_Inner}%g" ./sso.properties
sed -i "s%innerDomains=.*%innerDomains=${innerDomains}%g" ./sso.properties

sed -i "s%whiteList=.*%whiteList=${whiteList}%g" ./whitelist.properties

sed -i "s%paas.sdk.mode=.*%paas.sdk.mode=${SDK_MODE}%g" ./paas/paas-conf.properties
sed -i "s%ccs.appname=.*%ccs.appname=${CCS_NAME}%g" ./paas/paas-conf.properties
sed -i "s%ccs.zk_address=.*%ccs.zk_address=${ZK_ADDR}%g" ./paas/paas-conf.properties

sed -i "s%logistics.url=.*%logistics.url=${LOGISTICS_URL}%g" ./ch-order-web.properties
sed -i "s%integral.search.url=.*%integral.search.url=${INTEGRAL_SEARCH_URL}%g" ./ch-order-web.properties
sed -i "s%integral.shopback.url=.*%integral.shopback.url=${INTEGRAL_SHOPBACK_URL}%g" ./ch-order-web.properties
sed -i "s%ch.username.url=.*%ch.username.url=${CH_USERNAME_URL}%g" ./ch-order-web.properties
sed -i "s%ch.pay.url=.*%ch.pay.url=${CH_PAY_URL}%g" ./ch-order-web.properties
sed -i "s%ch.refundnotice.url=.*%ch.refundnotice.url=${CH_REFUNDNOTICE_URL}%g" ./ch-order-web.properties
sed -i "s%invoice.print.url=.*%invoice.print.url=${INVOICE_PRINT_URL}%g" ./ch-order-web.properties
sed -i "s%invoice.print.loginName=.*%invoice.print.loginName=${INVOICE_PRINT_LOGINNAME}%g" ./ch-order-web.properties
sed -i "s%invoice.print.password=.*%invoice.print.password=${INVOICE_PRINT_PASSWORD}%g" ./ch-order-web.properties

invoice.print.loginName=123456
#\u53D1\u7968\u5BC6\u7801
invoice.print.password=123456
# 各中心要根据情况自己修改成与dubbo.properties中对应的配置项
sed -i "s%dubbo.registry.address=.*%dubbo.registry.address=${REST_REGISTRY_ADDR}%g" ./dubbo.properties

sed -i "s%ftp.ip=.*%ftp.ip=${ftp.ip}%g" ./ofcConfig.properties
sed -i "s%ftp.userName=.*%ftp.userName=${ftp.userName}%g" ./ofcConfig.properties
sed -i "s%ftp.userPwd=.*%ftp.userPwd=${ftp.userPwd}%g" ./ofcConfig.properties
sed -i "s%ftp.ip=.*%ftp.ip=${ftp.ip}%g" ./ofcConfig.properties
sed -i "s%ftp.port=.*%ftp.port=${ftp.port}%g" ./ofcConfig.properties
sed -i "s%ftp.schedule=.*%ftp.schedule=${ftp.schedule}%g" ./ofcConfig.properties
sed -i "s%ftp.path=.*%ftp.path=${ftp.path}%g" ./ofcConfig.properties
sed -i "s%ftp.localpath=.*%ftp.localpath=${ftp.localpath}%g" ./ofcConfig.properties

popd


nohup ${CATALINA_HOME}/bin/catalina.sh run >> /${APP_NAME}.log