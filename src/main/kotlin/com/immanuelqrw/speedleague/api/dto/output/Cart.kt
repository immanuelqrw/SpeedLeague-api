package com.immanuelqrw.speedleague.api.dto.output

import com.immanuelqrw.speedleague.api.entity.Region


data class Cart(

    val gameName: String,

    val systemName: String,

    val isEmulated: Boolean,

    val region: Region,

    val versionName: String

)
