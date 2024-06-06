package com.alef.souqleader.ui

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

fun updateLocale(context: Context, locale: Locale) {
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)
    context.createConfigurationContext(configuration)
    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
}
