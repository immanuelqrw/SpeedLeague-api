package com.immanuelqrw.speedleague.api.dto.output

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.immanuelqrw.speedleague.api.entity.LeagueType
import java.time.LocalDateTime

data class League (

    val name: String,

    val type: LeagueType = LeagueType.POOL,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val startedOn: LocalDateTime,

    val defaultTime: Long,

    val defaultPoints: Int,

    val season: Int,

    val tierLevel: Int,

    val tierName: String,

    val runnerLimit: Int

)
