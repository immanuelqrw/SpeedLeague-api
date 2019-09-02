package com.immanuelqrw.speedleague.api.dto.input

import java.time.LocalDateTime


data class Race(
    val leagueName: String,
    val startedOn: LocalDateTime
)
