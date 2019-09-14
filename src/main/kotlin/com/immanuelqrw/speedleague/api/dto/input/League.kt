package com.immanuelqrw.speedleague.api.dto.input

import com.immanuelqrw.core.util.DateTimeFormatter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class League(

    val name: String,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val startedOn: LocalDateTime,

    val defaultTime: Long,

    val defaultPoints: Int,

    val qualifierRules: List<QualifierRule>,

    val pointRules: List<PointRule>

)
