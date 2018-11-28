# Mars_Xlog
### 一、mars简介
mars 是微信官方的终端基础组件，是一个使用 C++ 编写的业务性无关，平台性无关的基础组件。 目前已接入微信 Android、iOS、Mac、Windows、WP 等客户端。

### 二、xlog简介
xlog是mars系统中可以独立使用的日志模块。

### 三、xlog优点

xlog相比其它日志模块，有如下优点：
1. 高性能高压缩率
2. 不丢失任何一行日志
3. 避免系统卡顿
4. 避免CPU波峰
5. 支持数据加密
### 四、API调用
本demo对xlog原始的API做了精简，更易于使用了。
1. 初始化
`FileLog.init(context, "")`
2. 写日志
`FileLog.d("tag", "write log.")`
2. 获取日志文件：
获取日志文件列表：`FileLog.retrieveLogFiles()`
获取打包到一个zip文件的日志：`FileLog.retrieveLogFilesAsZip()`
3. 为保证缓存内容再应用退出时写入了日志文件，建议退出前调用：
`FileLog.appenderClose()`
### 五、解码xlog日志文件（分两种情况）
需要先下载python 2.7（本文档写作使用的2.7.15 x86版本，非x86_64版本）
2.7.15 x86版本下载地址：https://www.python.org/ftp/python/2.7.15/python-2.7.15.msi

首先把log文件从手机里导出至Mars源码log/crypt/文件夹下，在命令行切换到该目录下。
导出文件命令样例：

```
adb pull /sdcard/mars/log_copy/FileLog_20181109.xlog "E:\mars\log\crypt"
```
- 文件未做加密
执行：`python decode_mars_nocrypt_log_file.py`
- 文件做了加密（初始化时pubKey传入了值，值需要和decode_mars_crypt_log_file.py中PUB_KEY值相同）
执行：`python decode_mars_crypt_log_file.py`
注意对于加密场景，执行上面命令前，需要安装pyelliptic，命令为：`pip install pyelliptic==1.5.7`。pip命令在C:\Python27\Scripts\目录下。

当前目录下就会生成解码后的FileLog_20181109.xlog.log

### 六、编译xlog静态库
可参考： https://github.com/luojiawei/Ljw_Mars_Xlog/blob/master/README.md
### 七、参考资料
https://github.com/Tencent/mars
http://blog.csdn.net/eclipsexys/article/details/53965065
https://mp.weixin.qq.com/s/cnhuEodJGIbdodh0IxNeXQ
http://blog.csdn.net/tencent_bugly/article/details/53157830
