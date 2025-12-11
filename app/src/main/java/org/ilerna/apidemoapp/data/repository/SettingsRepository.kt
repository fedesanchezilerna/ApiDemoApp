package org.ilerna.apidemoapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

/**
 * SettingsRepository - Manages application settings using SharedPreferences
 * Provides methods to save and retrieve settings like theme and view mode preferences
 */
class SettingsRepository(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

    companion object {
        const val KEY_THEME_MODE = "theme_mode"
        const val KEY_VIEW_MODE = "view_mode"
    }

    /**
     * Save a setting value to SharedPreferences
     * @param key The key to store the value under
     * @param value The value to store (supports String, Int, Boolean, Float, Long)
     */
    @SuppressLint("UseKtx")
    fun <T> saveSettingValue(key: String, value: T) {
        with(sharedPreferences.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            apply()
        }
    }

    /**
     * Retrieve a setting value from SharedPreferences
     * @param key The key to retrieve the value for
     * @param defaultValue The default value to return if the key doesn't exist
     * @return The stored value or defaultValue if not found
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getSettingValue(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue) as T
            is Int -> sharedPreferences.getInt(key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue) as T
            is Long -> sharedPreferences.getLong(key, defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}
