package com.immanuelqrw.speedleague.api.dto.output

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.immanuelqrw.speedleague.api.entity.PlayoffRule
import com.immanuelqrw.speedleague.api.entity.Race
import java.time.LocalDateTime

data class League (

    val name: String,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val startedOn: LocalDateTime,

    val defaultTime: Long,

    val races: Set<Race>,

    val playoffRules: List<PlayoffRule>

)
