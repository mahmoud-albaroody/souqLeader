package com.alef.souqleader.data.remote.dto

data class FilterRequest(
    var phone: String? = null,
    var status: String? = null,
    var name: String? = null,
    var note: String? = null,
    var channel: String? = null,
    var sales: String? = null,
    var project: String? = null,
    var budget_from: String? = null,
    var budget_to: String? = null,
    var marketer: String? = null,
    var communication_way: String? = null,
    var region: String? = null,
    var created_from: String? = null,
    var created_to: String? = null,
    var action_date_from: String? = null,
    var action_date_to: String? = null,
    var page: Int? = null,
    var searchField: String? = null

)

data class ProjectFilterRequest(
    var finishing: String? = null,
    var region: String? = null,
    var name: String? = null,
    var view: String? = null,
    var category: String? = null,
    var title: String? = null,
    var status: String? = null,
    var type: String? = null,
    var typeInventory: String? = null,
    var department:String?=null,
    var countryId: String? = null,
    var cityId: String? = null,
    var areaId: String? = null,
    )

