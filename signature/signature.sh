#!/bin/sh


# 转换平台签名命令

./keytool-importkeypair -k platform.jks -p 27999929 -pk8 511platform.pk8 -cert 511platform.x509.pem -alias platform

# platform.jks : 签名文件

# 27999929 : 签名文件密码

# platform.pk8、platform.x509.pem : 系统签名文件

# platform : 签名文件别名
