package com.immanuelqrw.speedleague.api.dto.output

data class Standing(

    val runnerName: String,

    val points: Int,

    val pointsPerRace: Float,

    val raceCount: Int,

    val wins: Int // Amount of First Places

)
