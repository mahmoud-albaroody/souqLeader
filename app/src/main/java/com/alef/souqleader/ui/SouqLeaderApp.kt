package com.alef.souqleader.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson

import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SouqLeaderApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Kotpref.init(this)
        Kotpref.gson = Gson()

    }
}