package com.longshihan.mvpcomponent.utils.log;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.tencent.mars.xlog.Xlog;

import timber.log.Timber;

/**
 * Created by LONGHE001.
 *
 * @time 2018/12/21 0021
 * @des 重写mmap实现，不用xlog
 * @function
 */

public class XLogTree extends Timber.DebugTree {
    @Override
    protected boolean isLoggable(String tag, int priority) {
        return true; // 永久保持打印的状态
    }

    public XLogTree(String cacheDir, String logDir) {
        // 设置打印的级别
        Xlog.open(true,Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, cacheDir, logDir, "MarsSample", "");
        // 关闭控制台的输出
        Xlog.setConsoleLogOpen(false);
         // 初始化XLog
        com.tencent.mars.xlog.Log.setLogImp(new Xlog());
    }

    /**
     * 直接调用Xlog的native方法进行写盘操作
     */
    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        switch (priority) {
            case android.util.Log.VERBOSE:
                Log.v(tag, message);
                break;
            case android.util.Log.DEBUG:
                Log.d(tag, message);
                break;
            case android.util.Log.INFO:
                Log.i(tag, message);
                break;
            case android.util.Log.WARN:
                Log.w(tag, message);
                break;
            case android.util.Log.ERROR:
                Log.e(tag, message);
                break;
        }
    }
}
