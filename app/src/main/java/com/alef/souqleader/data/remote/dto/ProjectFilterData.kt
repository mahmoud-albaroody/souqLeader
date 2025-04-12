package com.alef.souqleader.data.remote.dto

data class ProjectFilterData (
    val categories:ArrayList<Category>,
    val countries: ArrayList<Country>,
    val finishing: ArrayList<PropertyFinishing>?,
    val type:ArrayList<Type>?,
    val view:ArrayList<PropertyView>?,
    val department:ArrayList<PropertyDepartment>?
)