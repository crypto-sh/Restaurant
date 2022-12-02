package com.restaurant.core.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager



interface PreferenceHandler {

    fun put(key: String, value: String)
    fun put(key: String, value: Boolean)
    fun put(key: String, value: Long)
    fun put(key: String, value: Int)
    fun put(key: String, value: Float)

    fun getString(key: String, default : String = ""): String
    fun getBoolean(key: String, default: Boolean = false): Boolean
    fun getLong(key: String, default: Long = 0): Long
    fun getInt(key: String, default: Int = 0): Int
    fun getFloat(key: String, default: Float = 0f): Float

    fun removeSetting(key: String)
    fun deleteSetting(key: String)

    fun getStringResource(resId : Int) : String

    fun clearAll()
}

class PreferenceHandlerImpl(
    private val applicationContext: Context
) : PreferenceHandler {

    private val stringValues : HashMap<String, String> = hashMapOf()
    private val longValues   : HashMap<String, Long> = hashMapOf()
    private val intValues    : HashMap<String, Int> = hashMapOf()
    private val floatValues  : HashMap<String, Float> = hashMapOf()
    private val booleanValues: HashMap<String, Boolean> = hashMapOf()

    var preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

    override fun put(key: String, value: String) {
        synchronized(this) {
            stringValues[key] = value
            preferences.edit().putString(key, value).apply()
        }
    }

    override fun put(key: String, value: Boolean) {
        synchronized(this) {
            booleanValues[key] = value
            preferences.edit().putBoolean(key, value).apply()
        }
    }

    override fun put(key: String, value: Long) {
        synchronized(this) {
            longValues[key] = value
            preferences.edit().putLong(key, value).apply()
        }
    }

    override fun put(key: String, value: Int) {
        synchronized(this) {
            intValues[key] = value
            preferences.edit().putInt(key, value).apply()
        }
    }

    override fun put(key: String, value: Float) {
        synchronized(this){
            floatValues[key] = value
            preferences.edit().putFloat(key, value).apply()
        }
    }



    override fun getString(key: String, default: String): String {
        synchronized(this) {
            return if (stringValues.containsKey(key))
                stringValues[key] ?: default
            else
                preferences.getString(key, default) ?: default
        }
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        synchronized(this) {
            return if (booleanValues.containsKey(key))
                booleanValues[key] ?: default
            else
                preferences.getBoolean(key, default)
        }
    }

    override fun getLong(key: String, default: Long): Long {
        synchronized(this) {
            return if (longValues.containsKey(key))
                longValues[key] ?: default
            else
                preferences.getLong(key, default)
        }
    }

    override fun getInt(key: String, default: Int): Int {
        synchronized(this) {
            return if (intValues.containsKey(key))
                intValues[key] ?: default
            else
                preferences.getInt(key, default)
        }
    }

    override fun getFloat(key: String, default: Float): Float {
        synchronized(this) {
            return if (floatValues.containsKey(key))
                floatValues[key] ?: default
            else
                preferences.getFloat(key, default)
        }

    }

    override fun deleteSetting(key: String) {
        preferences.edit().remove(key).apply()
        stringValues.clear()
        longValues.clear()
        intValues.clear()
        floatValues.clear()
        booleanValues.clear()
    }

    override fun getStringResource(resId: Int): String = applicationContext.getString(resId)

    override fun removeSetting(key: String) {
        preferences.edit().remove(key).apply()
        stringValues.clear()
        longValues.clear()
        intValues.clear()
        floatValues.clear()
        booleanValues.clear()
    }

    override fun clearAll() {
        preferences.edit().clear().apply()
        stringValues.clear()
        longValues.clear()
        intValues.clear()
        floatValues.clear()
        booleanValues.clear()
    }
}