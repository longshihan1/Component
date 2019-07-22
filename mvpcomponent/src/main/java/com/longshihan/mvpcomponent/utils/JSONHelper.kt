package com.longshihan.mvpcomponent.utils

import org.json.JSONException
import org.json.JSONObject
import org.json.JSONStringer
import timber.log.Timber
import java.lang.reflect.Array
import java.util.HashMap
import kotlin.jvm.*

/**
 * Created by LONGHE001.
 * @time 2019/7/22 0022
 * @des
 * @function
 */
object JSONHelper {
     val TAG:String=JSONHelper::class.java.simpleName

    /**
     * 将对象转换成Json字符串
     *
     * @param obj
     * @return
     */
    fun toJSON(obj: Any): String {
        val js = JSONStringer()
        serialize(js, obj)
        return js.toString()
    }

    /**
     * 序列化为JSON *
     */
    private fun serialize(js: JSONStringer, o: Any) {
        if (isNull(o)) {
            try {
                js.value(null)
            } catch (e: JSONException) {
                Timber.e(TAG, e)
            }

            return
        }

        val clazz = o.javaClass
        if (isObject(clazz)) { // 对象
            serializeObject(js, o)
        } else if (isArray(clazz)) { // 数组
            serializeArray(js, o)
        } else if (isCollection(clazz)) { // 集合
            val collection = o as Collection<*>
            serializeCollect(js, collection)
        } else if (isMap(clazz)) { // 集合
            val collection = o as HashMap<*, *>
            serializeMap(js, collection)
        } else { // 单个值
            try {
                js.value(o)
            } catch (e: JSONException) {
                Timber.e(TAG, e)
            }

        }
    }

    /**
     * 序列化Map
     *
     * @param js  json对象
     * @param map map对象
     */
    private fun serializeMap(js: JSONStringer, map: Map<*, *>) {
        try {
            js.`object`()
            val valueMap = map as Map<String, Any>
            val it = valueMap.entries
                    .iterator()
            while (it.hasNext()) {
                val entry = it
                        .next() as java.util.Map.Entry<String, Any>
                js.key(entry.key)
                serialize(js, entry.value)
            }
            js.endObject()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 序列化数组 *
     */
    private fun serializeArray(js: JSONStringer, array: Any) {
        try {
            js.array()
            for (i in 0 until Array.getLength(array)) {
                val o = Array.get(array, i)
                serialize(js, o)
            }
            js.endArray()
        } catch (e: Exception) {
            Timber.e(TAG, e)
        }

    }

    /**
     * 序列化集合 *
     */
    private fun serializeCollect(js: JSONStringer,
                                 collection: Collection<*>) {
        try {
            js.array()
            for (o in collection) {
                serialize(js, o!!)
            }
            js.endArray()
        } catch (e: Exception) {
            Timber.e(TAG, e)
        }

    }

    /**
     * 序列化对象
     *
     *
     * *
     */
    private fun serializeObject(js: JSONStringer, obj: Any) {
        try {
            js.`object`()
            for (f in obj.javaClass.declaredFields) {
                // 不将序列化后的字段加入
                if (f.name != "serialVersionUID") {
                    f.isAccessible = true
                    val o = f.get(obj)
                    js.key(f.name)
                    serialize(js, o)
                }
            }
            js.endObject()
        } catch (e: Exception) {
            Timber.e(TAG, e)
        }

    }

    /**
     * 判断对象是否为空 *
     */
    private fun isNull(obj: Any?): Boolean {
        return if (obj is JSONObject) {
            JSONObject.NULL == obj
        } else obj == null
    }

    /**
     * 判断是否是值类型 *
     */
    fun isSingle(clazz: Class<*>): Boolean {
        return isBoolean(clazz) || isNumber(clazz) || isString(clazz)
    }

    /**
     * 是否布尔值 *
     */
    fun isBoolean(clazz: Class<*>?): Boolean {
        return clazz != null && (java.lang.Boolean.TYPE.isAssignableFrom(clazz) || Boolean::class.java
                .isAssignableFrom(clazz))
    }

    /**
     * 是否数值 *
     */
    fun isNumber(clazz: Class<*>?): Boolean {
        return clazz != null && (java.lang.Byte.TYPE.isAssignableFrom(clazz)
                || java.lang.Short.TYPE.isAssignableFrom(clazz)
                || Integer.TYPE.isAssignableFrom(clazz)
                || java.lang.Long.TYPE.isAssignableFrom(clazz)
                || java.lang.Float.TYPE.isAssignableFrom(clazz)
                || java.lang.Double.TYPE.isAssignableFrom(clazz) || Number::class.java
                .isAssignableFrom(clazz))
    }

    /**
     * 判断是否是字符串 *
     */
    fun isString(clazz: Class<*>?): Boolean {
        return clazz != null && (String::class.java.isAssignableFrom(clazz)
                || Character.TYPE.isAssignableFrom(clazz) || Char::class.java
                .isAssignableFrom(clazz))
    }

    /**
     * 判断是否是对象 *
     */
    private fun isObject(clazz: Class<*>?): Boolean {
        return (clazz != null && !isSingle(clazz) && !isArray(clazz)
                && !isCollection(clazz) && !isMap(clazz))
    }

    /**
     * 判断是否是数组 *
     */
    fun isArray(clazz: Class<*>?): Boolean {
        return clazz != null && clazz.isArray
    }

    /**
     * 判断是否是集合 *
     */
    fun isCollection(clazz: Class<*>?): Boolean {
        return clazz != null && Collection::class.java.isAssignableFrom(clazz)
    }

    /**
     * 判断是否是Map
     *
     * @param clazz
     * @return
     */
    fun isMap(clazz: Class<*>?): Boolean {
        return clazz != null && Map::class.java.isAssignableFrom(clazz)
    }

    /**
     * 判断是否是列表
     *
     * @param clazz
     * @return
     */
    fun isList(clazz: Class<*>?): Boolean {
        return clazz != null && List::class.java.isAssignableFrom(clazz)
    }


}