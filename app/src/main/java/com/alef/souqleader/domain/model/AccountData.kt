package com.alef.souqleader.domain.model

import com.chibatching.kotpref.KotprefModel


object AccountData : KotprefModel() {

    var auth_token: String? by nullableStringPref(null)
    var isFirstTime: Boolean by booleanPref(true)
    var name: String by stringPref("")
    var address: String by stringPref("")
    var lat: String by stringPref("0.0")
    var lng: String by stringPref("0.0")
    var addressId: String by stringPref("")
    var addressTitle: String by stringPref("")
    var lastName: String by stringPref("")
    var firstName: String by stringPref("")
    var birthDate: String by stringPref("")
    var isEmailVerified: Boolean by booleanPref(false)
    var isVisitor: Boolean by booleanPref(true)
    var showNewPassword: Boolean by booleanPref(false)

    var email: String by stringPref("")
    var walletBalance: String by stringPref("")
    var userId: Int by intPref(0)
    var countryKey: String by stringPref("")
    var currency: String by stringPref("EGP")
    var lang: String by stringPref("en")
    var confirm_expiration: String by stringPref("")
    var mobile: String? by nullableStringPref(null)
    var flgUserType: Int by intPref(0)
    var gender: Int by intPref(1)
    var isSwitch: Boolean by booleanPref(false)
    var password: String? by nullableStringPref(null)

    var profile_img: String? by nullableStringPref(null)
    var wallet: Int by intPref(0)
    var city_id: Int by intPref(0)
    var country_id: String by stringPref("")
    var addressType: String by stringPref("")
    var area_id: Int by intPref(0)
    var cityName: String by stringPref("")
    var areaName: String by stringPref("")
    var cityNameEn: String by stringPref("")
    var areaNameEn: String by stringPref("")

    var fragmentName: String by stringPref("")
    var orderId: String by stringPref("")
//    var currency by gsonPref(Currency())
}


