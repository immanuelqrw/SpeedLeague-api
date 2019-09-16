package com.immanuelqrw.speedleague.api.entity

import javax.persistence.Embeddable

@Embeddable
data class Tier(

    val name: String,

    val level: Int

)
