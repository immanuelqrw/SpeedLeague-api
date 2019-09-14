package com.immanuelqrw.speedleague.api.dto.output

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.immanuelqrw.speedleague.api.entity.Qualifier
import java.time.LocalDateTime

data class PlayoffRule(

    val qualifier: Qualifier,

    val count: Int,

    val leagueName: String,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val addedOn: LocalDateTime,

    val order: Int

)
