package com.immanuelqrw.speedleague.api.dto.output

import com.immanuelqrw.speedleague.api.entity.Outcome

data class RaceTime(

    val raceName: String,

    val runnerName: String,

    val time: Long?,

    val outcome: Outcome,

    val placement: Int?

)
