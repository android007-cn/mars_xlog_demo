package com.marsxlogdemo

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.cxyzy.androidutils.FileUtil
import com.cxyzy.androidutils.LogUtil
import com.cxyzy.androidutils.TimeCostUtil
import com.tencent.mars.xlog.FileLog
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private val testLogContent = "132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da132134646464646759613dffd1a3fd1a3f1a3ga13g13ag1a31gd3fag1d3ag1da"
    private val testTimes = 100000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        xlogWriteBtn!!.setOnClickListener { xlogSingleWriteLog() }
        xlogBatchWriteBtn!!.setOnClickListener { xlogBatchWriteLog() }
        commonWriteLogBtn!!.setOnClickListener { commonFileBatchWriteLog() }
        writeToFileBtn!!.setOnClickListener { retrieveLogFiles() }
        // 申请权限
        checkPermission()

        //初始化 xlog
        initFileLog()
    }

    private fun xlogSingleWriteLog() {
        infoTv.text = ""
        FileLog.d("test", "write xlog.")
    }

    private fun xlogBatchWriteLog() {
        infoTv.text = "测试写入$testTimes 次,请稍后"
        TimeCostUtil.startTick()
        for (i in 0..testTimes) {
            FileLog.d("test", testLogContent)
        }
        FileLog.appenderFlush(true)
        var timeCost = "耗时:" + TimeCostUtil.getTimeCost().toString()
        LogUtil.v("xlog", timeCost)
        infoTv.text = timeCost
    }

    private fun commonFileBatchWriteLog() {
        infoTv.text = "测试写入$testTimes 次,请稍后"
        TimeCostUtil.startTick()
        val filePath = "/data/user/0/com.marsxlogdemo/mars/log_copy" + "/1.log"
        for (i in 0..testTimes) {
            FileUtil.appendContent(filePath, testLogContent)
        }

        var timeCost = "耗时:" + TimeCostUtil.getTimeCost().toString()
        LogUtil.v("普通文件日志", timeCost)
        infoTv.text = timeCost
    }

    private fun retrieveLogFiles() {
        var files = FileLog.retrieveLogFiles()
        var sb = StringBuilder()
        files?.apply {
            for (file in files) {
                val fileSize = File(file).length()
                sb.append("$file,size:$fileSize\n")
            }
        }
        var result = sb.toString()
        if (result.isBlank()) {
            result = "文件内容为空"
        }
        infoTv.text = result
    }

    private fun initFileLog() {
        FileLog.init(applicationContext, "572d1e2710ae5fbca54c76a382fdd44050b3a675cb2bf39feebe85ef63d947aff0fa4943f1112e8b6af34bebebbaefa1a0aae055d9259b89a1858f7cc9af9df1")
    }

    override fun onDestroy() {
        super.onDestroy()
        //停止Log记录
        FileLog.appenderClose()
    }

    private fun checkPermission() {
        val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1000
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(
                    this,
                    "应用程序需要使用存储权限,请在下个窗口选择允许",
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(this, "您拒绝授权,并勾选了不在提醒", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this).setTitle("打开应用程序设置修改应用程序权限").build().show()
        } else {
            Toast.makeText(this, "您拒绝授权", Toast.LENGTH_SHORT).show()
            checkPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, "您同意了授权", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}