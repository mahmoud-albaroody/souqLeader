package com.alef.souqleader.data.remote.dto

import retrofit2.http.Field

data class JobAppRequest(
    val jobId: String?=null,
    val categoryId: String?=null,
    val typeId: String?=null,
    val careerLevelId: String?=null,
    val workplaceId: String?=null,
    val name: String?=null,
    val countryId: String?=null,
    val cityId: String?=null,
    val areaId: String?=null,
)