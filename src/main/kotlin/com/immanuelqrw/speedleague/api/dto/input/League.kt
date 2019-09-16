package com.immanuelqrw.speedleague.api.dto.input

import com.immanuelqrw.core.util.DateTimeFormatter
import com.immanuelqrw.speedleague.api.entity.LeagueType
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class League(

    val name: String,

    val type: LeagueType = LeagueType.POOL,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val startedOn: LocalDateTime,

    val defaultTime: Long,

    val defaultPoints: Int,

    val season: Int,

    val tierLevel: Int,

    val tierName: String,

    val qualifierRules: List<QualifierRule>,

    val pointRules: List<PointRule>

)
