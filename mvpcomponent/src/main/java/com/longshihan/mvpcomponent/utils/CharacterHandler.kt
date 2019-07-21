package com.longshihan.mvpcomponent.utils

import android.annotation.SuppressLint
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import kotlin.experimental.and

/**
 * Created by LONGHE001.
 * @time 2019/7/21 0021
 * @des
 * @function
 */

object CharacterHandler {

    val emojiFilter:InputFilter=object :InputFilter{
        @SuppressLint("WrongConstant")
        internal var emoji = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE)
        override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
            val emojiMatcher:Matcher=emoji.matcher(source)
            if (emojiMatcher.find()){
                return ""
            }
            return null

        }
    }

    /**
     * 字符串转换成十六进制字符串
     *
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    fun str2HexStr(str: String): String {

        val chars = "0123456789ABCDEF".toCharArray()
        val sb = StringBuilder("")
        val bs = str.toByteArray()
        var bit: Int
        val x = (1 shr 2) and 0x000FF000

        for (i in bs.indices) {
            bit = (bs[i] and (0x0f0 shr 4)).toInt()
            sb.append(chars[bit])
            bit = (bs[i] and 0x0f).toInt()
            sb.append(chars[bit])
        }
        return sb.toString().trim { it <= ' ' }
    }


    /**
     * json 格式化
     *
     * @param json
     * @return
     */
    fun jsonFormat(json: String): String {
        var json = json
        if (TextUtils.isEmpty(json)) {
            return "Empty/Null json content"
        }
        var message: String
        try {
            json = json.trim { it <= ' ' }
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                message = jsonObject.toString(4)
            } else if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                message = jsonArray.toString(4)
            } else {
                message = json
            }
        } catch (e: JSONException) {
            message = json
        }

        return message
    }


    /**
     * xml 格式化
     *
     * @param xml
     * @return
     */
    fun xmlFormat(xml: String): String {
        if (TextUtils.isEmpty(xml)) {
            return "Empty/Null xml content"
        }
        var message: String
        try {
            val xmlInput = StreamSource(StringReader(xml))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            transformer.transform(xmlInput, xmlOutput)
            message = xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n")
        } catch (e: TransformerException) {
            message = xml
        }

        return message
    }
}
