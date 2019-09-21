package com.immanuelqrw.speedleague.api.dto.output

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.immanuelqrw.speedleague.api.entity.LeagueType
import java.time.LocalDateTime

data class LeagueSchedule (

    val name: String,

    val season: Int,

    val tierLevel: Int,

    val matches: List<Match>

)
