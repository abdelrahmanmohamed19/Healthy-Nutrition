package com.example.nutrition_app.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.nutrition_app.data.model.Meal
import com.example.nutrition_app.util.Constants
import com.google.gson.Gson

class LocalStorage(context: Context) {

    private var sharedPref: SharedPreferences? = null
    private var gson: Gson? = null

    init {
        gson = Gson()
        sharedPref = context.getSharedPreferences(Constants.Data.LocalStorage.NAME, Context.MODE_PRIVATE)
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun < reified T> put(key: String, value: T) {
        val editor = sharedPref?.edit()

        when (T::class) {
            Boolean::class -> editor?.putBoolean(key, value as Boolean)
            Float::class -> editor?.putFloat(key, value as Float)
            Int::class -> editor?.putInt(key, value as Int)
            Long::class -> editor?.putLong(key, value as Long)
            String::class -> editor?.putString(key, value as String)
            Meal::class -> {
                val dataEncode  = gson?.toJson(value)
                editor?.putString(key, dataEncode as String)
            }
            else -> throw NotImplementedError("Extension not implemented for this type")
        }

        editor?.apply()
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun < reified T> get(key: String): T? {

       return when (T::class) {
            Boolean::class -> sharedPref?.getBoolean(key, false) as T?
            Float::class -> sharedPref?.getFloat(key, .0F) as T?
            Int::class -> sharedPref?.getInt(key, 0) as T?
            Long::class -> sharedPref?.getLong(key, 0) as T?
            String::class -> sharedPref?.getString(key, null) as T?
            Meal::class -> {
                val dataDecode = sharedPref?.getString(key, null)
                return  gson?.fromJson(dataDecode, T::class.java)
            }
            else -> throw NotImplementedError("Extension not implemented for this type")
        }

    }


}