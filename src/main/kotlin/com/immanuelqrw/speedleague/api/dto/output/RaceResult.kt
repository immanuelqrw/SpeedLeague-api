package com.immanuelqrw.speedleague.api.dto.output

import com.immanuelqrw.speedleague.api.entity.Outcome

data class RaceResult(

    val raceName: String,

    val runnerName: String,

    val outcome: Outcome,

    val time: Long?,

    val placement: Int?

)
