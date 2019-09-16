package com.immanuelqrw.speedleague.api.dto.input

import com.immanuelqrw.speedleague.api.entity.Region


data class Cart(

    val gameName: String,

    val systemName: String,

    val isEmulated: Boolean = false,

    val region: Region = Region.ANY,

    val version: String = "ANY"

)
