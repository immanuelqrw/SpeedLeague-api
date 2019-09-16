package com.immanuelqrw.speedleague.api.dto.input

import com.immanuelqrw.core.util.DateTimeFormatter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class Race(

    val leagueName: String,

    val season: Int,

    val tierLevel: Int,

    val tierName: String,

    val raceName: String?,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val startedOn: LocalDateTime

)
