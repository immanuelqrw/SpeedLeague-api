package com.immanuelqrw.speedleague.api.dto.input

import com.immanuelqrw.speedleague.api.entity.Region


data class Speedrun(

    val categoryName: String,

    val gameName: String,

    val systemName: String,

    val isEmulated: Boolean = false,

    val region: Region?,

    val versionName: String

)
