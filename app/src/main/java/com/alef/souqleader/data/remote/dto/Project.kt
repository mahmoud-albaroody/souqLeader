package com.alef.souqleader.data.remote.dto

data class Project(
    val category_id: String?,
    val created_at: String?,
    val created_by: Int?,
    val description: String?,
    val id: Int,
    val images: List<Image>?,
    val map: String?,
    val region: Int?,
    val region_name: String?,
    val regions: Regions?,
    val start_price: String?,
    val status: String?,
    val title: String?,
    val updated_at: String?,
    val video: String?
)