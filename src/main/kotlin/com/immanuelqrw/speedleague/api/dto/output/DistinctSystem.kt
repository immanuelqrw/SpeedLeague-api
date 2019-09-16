package com.immanuelqrw.speedleague.api.dto.output

import com.immanuelqrw.speedleague.api.entity.Region


data class DistinctSystem(

    val systemName: String,

    val isEmulated: Boolean,

    val region: Region,

    val version: String

)
