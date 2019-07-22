package com.longshihan.mvpcomponent.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils
import java.lang.reflect.Field
import java.lang.reflect.Modifier

import timber.log.Timber

object PreferenceUtils {
    private var mContext: Context? = null

    val TAG = PreferenceUtils::class.java.simpleName

    fun init(context: Context) {
        mContext = context
    }

    fun setValue(key: String, value: String) {
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        sp.edit().putString(key, value).apply()
    }

    fun getValue(key: String, defValue: String): String? {
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        return sp.getString(key, defValue)
    }

    fun setValue(key: String, value: Float) {
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        sp.edit().putFloat(key, value).apply()
    }

    fun getValue(key: String, defValue: Float): Float {
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        return sp.getFloat(key, defValue)
    }

    fun setValue(key: String, value: Double) {
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        sp.edit().putLong(key, java.lang.Double.doubleToLongBits(value)).apply()
    }

    fun getValue(key: String, defValue: Double): Double {
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        return java.lang.Double.longBitsToDouble(sp.getLong(key, java.lang.Double.doubleToLongBits(defValue)))
    }

    fun setValue(name: String, key: String, value: String) {
        val sp = mContext!!.getSharedPreferences(name, 0)
        sp.edit().putString(key, value).apply()
    }

    fun getValue(name: String, key: String, defValue: String): String? {
        val sp = mContext!!.getSharedPreferences(name, 0)
        return sp.getString(key, defValue)
    }

    fun setValue(name: String, key: String, value: Float) {
        val sp = mContext!!.getSharedPreferences(name, 0)
        sp.edit().putFloat(key, value).apply()
    }

    fun getValue(name: String, key: String, defValue: Float): Float {
        val sp = mContext!!.getSharedPreferences(name, 0)
        return sp.getFloat(key, defValue)
    }

    fun setValue(key: String, value: Boolean) {
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        sp.edit().putBoolean(key, value).apply()
    }

    fun getValue(key: String, defValue: Boolean): Boolean {
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        return sp.getBoolean(key, defValue)
    }

    fun setValue(name: String, key: String, value: Boolean) {
        val sp = mContext!!.getSharedPreferences(name, 0)
        sp.edit().putBoolean(key, value).apply()
    }

    fun getValue(name: String, key: String, defValue: Boolean): Boolean {
        val sp = mContext!!.getSharedPreferences(name, 0)
        return sp.getBoolean(key, defValue)
    }

    fun setValue(name: String, key: String, value: Int) {
        val sp = mContext!!.getSharedPreferences(name, 0)
        sp.edit().putInt(key, value).apply()
    }

    fun getValue(name: String, key: String, defValue: Int): Int {
        val sp = mContext!!.getSharedPreferences(name, 0)
        return sp.getInt(key, defValue)
    }

    fun setValue(key: String, value: Int) {
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        sp.edit().putInt(key, value).apply()
    }

    fun getValue(key: String, defValue: Int): Int {
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        return sp.getInt(key, defValue)
    }

    fun setValue(key: String, value: Long) {
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        sp.edit().putLong(key, value).apply()
    }

    fun getValue(key: String, defValue: Long): Long {
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        return sp.getLong(key, defValue)
    }

    private fun isParcelableCreator(field: Field): Boolean {
        return Modifier.toString(field.modifiers) == "public static final" && "CREATOR" == field.name
    }

    private fun isObject(clazz: Class<*>?): Boolean {
        return clazz != null && !isSingle(clazz) && !isArray(clazz) && !isCollection(clazz) && !isMap(clazz)
    }

    private fun isSingle(clazz: Class<*>): Boolean {
        return isBoolean(clazz) || isNumber(clazz) || isString(clazz)
    }

    fun isBoolean(clazz: Class<*>?): Boolean {
        return clazz != null && (java.lang.Boolean.TYPE.isAssignableFrom(clazz) || Boolean::class.java.isAssignableFrom(clazz))
    }

    fun isNumber(clazz: Class<*>?): Boolean {
        return clazz != null && (java.lang.Byte.TYPE.isAssignableFrom(clazz) || java.lang.Short.TYPE.isAssignableFrom(clazz) || Integer.TYPE.isAssignableFrom(clazz) || java.lang.Long.TYPE.isAssignableFrom(clazz) || java.lang.Float.TYPE.isAssignableFrom(clazz) || java.lang.Double.TYPE.isAssignableFrom(clazz) || Number::class.java.isAssignableFrom(clazz))
    }

    fun isString(clazz: Class<*>?): Boolean {
        return clazz != null && (String::class.java.isAssignableFrom(clazz) || Character.TYPE.isAssignableFrom(clazz) || Char::class.java.isAssignableFrom(clazz))
    }

    fun isArray(clazz: Class<*>?): Boolean {
        return clazz != null && clazz.isArray
    }

    fun isCollection(clazz: Class<*>?): Boolean {
        return clazz != null && Collection::class.java.isAssignableFrom(clazz)
    }

    fun isMap(clazz: Class<*>?): Boolean {
        return clazz != null && Map::class.java.isAssignableFrom(clazz)
    }

    fun clearSettings(context: Context) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        sp.edit().clear().apply()
    }

    fun clearSettings(name: String) {
        val sp = mContext!!.getSharedPreferences(name, 0)
        sp.edit().clear().apply()
    }

    fun clearObject(clazz: Class<*>) {
        val fields = clazz.fields

        for (i in fields.indices) {
            val type = fields[i].type
            if (isObject(type)) {
                clearObject(type)
            }
        }

        clearSettings(clazz.name)
    }


}
