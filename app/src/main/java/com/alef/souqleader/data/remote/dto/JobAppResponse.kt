package com.alef.souqleader.data.remote.dto

import retrofit2.http.Field

data class JobAppRequest(
    var jobId: String?=null,
    var categoryId: String?=null,
    var typeId: String?=null,
    var careerLevelId: String?=null,
    var workplaceId: String?=null,
    val name: String?=null,
    var countryId: String?=null,
    var cityId: String?=null,
    var areaId: String?=null,
)