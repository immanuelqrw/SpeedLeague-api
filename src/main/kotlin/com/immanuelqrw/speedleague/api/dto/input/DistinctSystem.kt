package com.immanuelqrw.speedleague.api.dto.input

import com.immanuelqrw.speedleague.api.entity.Region


data class DistinctSystem(

    val systemName: String,

    val isEmulated: Boolean = false,

    val region: Region = Region.ANY,

    val versionName: String = "ANY"

)
