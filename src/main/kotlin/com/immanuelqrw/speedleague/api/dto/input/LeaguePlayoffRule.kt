package com.immanuelqrw.speedleague.api.dto.input


data class LeaguePlayoffRule(

    val leagueName: String,

    val qualifierRules: List<QualifierRule>

)
