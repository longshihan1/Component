package com.longshihan.mvpcomponent.utils

import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList

/**
 * Created by LONGHE001.
 * @time 2019/7/22 0022
 * @des
 * @function
 */
object FileUtil {
    val SDPATH = Environment.getExternalStorageDirectory().absolutePath

    val ADPATH = "$SDPATH/testart"

    fun createSdDir() {
        val file = File(ADPATH)
        if (!file.exists()) {
            file.mkdirs()
        } else {
            if (!file.isDirectory) {
                file.delete()
                file.mkdir()
            }
        }
    }

    fun isFileExist(paramString: String?): Boolean {
        if (paramString == null)
            return false
        val localFile = File("$ADPATH/$paramString")
        return localFile.exists()
    }

    @Throws(IOException::class)
    fun createFile(fileName: String): File {
        val file = File(ADPATH, fileName)
        file.createNewFile()
        return file
    }

    fun getAllAD(): List<String> {
        val file = File(ADPATH)
        val fileList = file.listFiles()
        val list = ArrayList<String>()
        if (null != fileList) {
            for (f in fileList) {
                list.add(f.absolutePath)
            }
        }
        return list
    }

    fun saveBitmapToSDCard(bitmap: Bitmap?, title: String): Uri? {
        val appDir = File("$ADPATH/Gank")
        if (!appDir.exists()) {
            appDir.mkdirs()
        }
        val fileName = title.replace("/", "-") + "-girl.jpg"
        val file = File(appDir, fileName)
        val outputStream: FileOutputStream
        try {
            outputStream = FileOutputStream(file)
            assert(bitmap != null)
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return Uri.fromFile(file)
    }
}