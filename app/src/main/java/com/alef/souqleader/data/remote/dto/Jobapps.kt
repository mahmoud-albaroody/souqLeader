package com.alef.souqleader.data.remote.dto

data class Jobapps(
    val age: Int,
    val area: Area,
    val city: City,
    val country: Country,
    val created_at: String,
    val cv: String,
    val education: List<Education>,
    val email: String,
    val expected_salary: String,
    val experince: Int,
    val id: Int,
    val is_locked: Int,
    val language: List<Language>,
    val name: String,
    val phone: String,
    val skills: List<Skill>,
    val work_experience: List<WorkExperience>
)