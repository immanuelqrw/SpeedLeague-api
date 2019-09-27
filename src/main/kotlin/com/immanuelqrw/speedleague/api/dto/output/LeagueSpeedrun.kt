package com.immanuelqrw.speedleague.api.dto.output

import com.immanuelqrw.speedleague.api.entity.Region


data class LeagueSpeedrun(

    val leagueName: String,

    val season: Int,

    val tierLevel: Int,

    val category: String,

    val gameName: String,

    val systemName: String,

    val isEmulated: Boolean,

    val region: Region,

    val version: String

)
