package com.immanuelqrw.speedleague.api.dto.input


data class LeaguePlayoffRule(

    val leagueName: String,

    val season: Int,

    val tierLevel: Int,

    val tierName: String,

    val qualifierRules: List<QualifierRule>

)
