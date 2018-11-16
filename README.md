# Mars_Xlog
### 一、mars简介
mars 是微信官方的终端基础组件，是一个使用 C++ 编写的业务性无关，平台性无关的基础组件。 目前已接入微信 Android、iOS、Mac、Windows、WP 等客户端。

### 二、xlog简介
xlog是mars系统中可以独立使用的日志模块。

### 三、xlog优点

xlog相比其它日志模块，有如下优点：
1. 高性能高压缩率。
2. 不丢失任何一行日志。
3. 避免系统卡顿。
4. 避免CPU波峰。

### 六、解密log
log写入到/sdcard/mars/log/目录

导入log：

```
adb pull /sdcard/mars/log/MarsXlogDemo_20181109.xlog "E:\mars\log\crypt"
```
把log导出至Mars源码log/crypt/这个文件夹

执行脚本（decode_mars_nocrypt_log_file.py在log/crypt目录）


```
python decode_mars_nocrypt_log_file.py
```
当前目录下就会生成解密后的MarsXlogDemo_20181109.xlog.log
### 编译xlog静态库
可参考： https://github.com/luojiawei/Ljw_Mars_Xlog/blob/master/README.md
### 七、参考资料
https://github.com/Tencent/mars
http://blog.csdn.net/eclipsexys/article/details/53965065
https://mp.weixin.qq.com/s/cnhuEodJGIbdodh0IxNeXQ
http://blog.csdn.net/tencent_bugly/article/details/53157830
https://github.com/cxyzy1/Ljw_Mars_Xlog
