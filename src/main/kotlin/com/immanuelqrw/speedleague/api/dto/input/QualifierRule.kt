package com.immanuelqrw.speedleague.api.dto.input

import com.immanuelqrw.speedleague.api.entity.Qualifier

data class QualifierRule(

    val qualifier: Qualifier,

    val count: Int

)
