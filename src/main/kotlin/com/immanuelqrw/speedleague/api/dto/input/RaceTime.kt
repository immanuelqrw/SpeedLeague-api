package com.immanuelqrw.speedleague.api.dto.input

data class RaceTime(

    val runnerName: String,

    val raceName: String,

    val time: String // ! Need to parse into seconds

)
