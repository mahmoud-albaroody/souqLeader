package com.alef.souqleader.data.remote.dto

data class FilterRequest (
    var phone:String?=null,
    var status:String?=null,
    var name:String?=null,
    var note:String?=null,
    var channel:String?=null,
    var sales:String?=null,
    var project:String?=null,
    var budget:String?=null,
    var marketer:String?=null,
    var communication_way:String?=null,
    var region:String?=null,
    var created_from:String?=null,
    var created_to:String?=null,
    var action_date_from:String?=null,
    var action_date_to: String?=null,

)