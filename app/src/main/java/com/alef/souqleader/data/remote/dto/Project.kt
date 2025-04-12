package com.alef.souqleader.data.remote.dto


data class Project(
    val category_id: String?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val description: String?=null,
    val id: Int?=null,
    val images: List<Image>?=null,
    val map: String?=null,
    val region_name: String?=null,
    val region: Regions?=null,
    val start_price: String?=null,
    val status: String?=null,
    val title: String?=null,
    val updated_at: String?=null,
    val video: String?=null,
    val category: ArrayList<Category>?=null,
)