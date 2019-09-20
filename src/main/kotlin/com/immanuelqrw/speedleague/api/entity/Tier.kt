package com.immanuelqrw.speedleague.api.entity

import javax.persistence.Embeddable
import javax.validation.constraints.Min

@Embeddable
data class Tier(

    val name: String,

    @get:Min(0)
    val level: Int

)
