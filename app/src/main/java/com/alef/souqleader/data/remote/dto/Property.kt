package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class Property(
    val bathrooms: Int?,
    val bedrooms: Int?,
    val bua: Int?,
    val bulding_no: String?,
    val category: Int?,
    val created_at: String?,
    val created_by: Int?,
    val department: Int?,
    val description_ar: String?,
    val description_en: String?,
    val finishing: Int?,
    val floor: Int?,
    val gallery: List<Gallery>?,
    val id: Int?,
    val land_space: String?,
    val lift: Int?,
    val map: String?,
    val meter_price: Int?,
    val owner: String?,
    val owner_mobile: String?,
    val payment_method: String?,
    val pdf: String?,
    val phase: String?,
    val photo: String?,
    val price: String?,
    val primary_resale: String?,
    val property__payment_method: PropertyPaymentMethod?,
    val property_category: PropertyCategory?,
    val property_department: PropertyDepartment?,
    val property_finishing: PropertyFinishing?,
    val property_unit_type: PropertyUnitType?,
    val property_view: PropertyView?,
    val region: Int?,
    val region_name: String?,
    val regions: Regions?,
    val title_ar: String?,
    val title_en: String?,
    val unit_code: String?,
    val unit_no: String?,
    val unit_type: String?,
    val updated_at: String?,
    val video: String?,
    val view: Int?
){
    fun getTitle(): String? {
        return if (AccountData.lang=="ar"){
            title_ar
        }else{
            title_en
        }
    }
}