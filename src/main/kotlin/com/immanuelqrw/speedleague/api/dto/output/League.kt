package com.immanuelqrw.speedleague.api.dto.output

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDateTime

data class League (

    val name: String,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val startedOn: LocalDateTime,

    val defaultTime: Long,

    val defaultPoints: Int

)
