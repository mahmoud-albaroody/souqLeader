package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class Property(
    val bathrooms: Int?=null,
    val bedrooms: Int?=null,
    val bua: Int?=null,
    val bulding_no: String?=null,
    val category: Int?=null,
    val created_at: String?=null,
    val created_by: Int?=null,
    val department: Int?=null,
    val description_ar: String?=null,
    val description_en: String?=null,
    val finishing: Int?=null,
    val floor: Int?=null,
    val gallery: List<Gallery>?=null,
    val id: Int?=null,
    val land_space: String?=null,
    val lift: Int?=null,
    val map: String?=null,
    val meter_price: Int?=null,
    val owner: String?=null,
    val owner_mobile: String?=null,
    val payment_method: String?=null,
    val pdf: String?=null,
    val phase: String?=null,
    val photo: String?=null,
    val price: String?=null,
    val primary_resale: String?=null,
    val property__payment_method: PropertyPaymentMethod?=null,
    val property_category: PropertyCategory?=null,
    val property_department: PropertyDepartment?=null,
    val property_finishing: PropertyFinishing?=null,
    val property_unit_type: PropertyUnitType?=null,
    val property_view: PropertyView?=null,
    val region: Int?=null,
    val region_name: String?=null,
    val regions: Regions?=null,
    val title_ar: String?=null,
    val title_en: String?=null,
    val unit_code: String?=null,
    val unit_no: String?=null,
    val unit_type: String?=null,
    val updated_at: String?=null,
    val video: String?=null,
    val view: Int?=null
){
    fun getTitle(): String? {
        return if (AccountData.lang=="ar"){
            title_ar
        }else{
            title_en
        }
    }
}