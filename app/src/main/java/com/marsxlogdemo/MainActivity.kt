package com.marsxlogdemo

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.tencent.mars.xlog.FileLog
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        writeLogBtn!!.setOnClickListener { FileLog.d("test", "write log.") }
        writeToFileBtn!!.setOnClickListener { retrieveLogFiles() }
        // 申请权限
        checkPermission()

        //初始化 xlog
        initFileLog()
    }

    private fun retrieveLogFiles() {
        var files = FileLog.retrieveLogFiles()
        var sb = StringBuilder()
        files.apply {
            for (file in files) {
                val fileSize = File(file).length()
                sb.append("$file,size:$fileSize\n")
            }
            Toast.makeText(applicationContext, sb.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initFileLog() {
        FileLog.init(applicationContext, "")
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