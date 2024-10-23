package com.alef.souqleader.ui

import android.app.UiModeManager
import android.util.Log
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
        val uiManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
        if (false) {
            uiManager.enableCarMode(0)
            uiManager.nightMode = UiModeManager.MODE_NIGHT_YES
        } else {
          //  uiManager.disableCarMode(UiModeManager.DISABLE_CAR_MODE_GO_HOME)
            uiManager.nightMode = UiModeManager.MODE_NIGHT_NO
        }
       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Kotpref.init(this)
        Kotpref.gson = Gson()


    }
}