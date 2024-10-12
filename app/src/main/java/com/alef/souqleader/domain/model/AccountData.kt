package com.alef.souqleader.domain.model

import com.alef.souqleader.data.remote.dto.ModulePermission
import com.alef.souqleader.data.remote.dto.Permissions
import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonPref


object AccountData : KotprefModel() {

    var auth_token: String? by nullableStringPref(null)
    var isFirstTime: Boolean by booleanPref(true)
    var name: String by stringPref("")
    var log: String by stringPref("")
    var role_name: String by stringPref("")
    var photo: String by stringPref("")
    var firebase_token: String? by nullableStringPref(null)
    var role_id: Int by intPref(0)
    var domain: String by stringPref("")
    var BASE_URL: String by stringPref( "https://souqleader.com/")
    var email: String by stringPref("")
    var userId: Int by intPref(0)
    var lang: String by stringPref("en")
    var password: String? by nullableStringPref(null)
    var permissionList: List<ModulePermission> by gsonPref(emptyList<ModulePermission>())
}


