package com.immanuelqrw.speedleague.api.dto.update

import com.immanuelqrw.speedleague.api.entity.Outcome

data class RaceTime(

    val runnerName: String,

    val raceName: String,

    val time: Long, // - Convert in frontend

    val outcome: Outcome

)
