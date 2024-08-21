package com.alef.souqleader.data.remote.dto

import java.io.Serializable


data class ModulePermission(
    val module_name: String,
    val permissions: Permissions
):Serializable
