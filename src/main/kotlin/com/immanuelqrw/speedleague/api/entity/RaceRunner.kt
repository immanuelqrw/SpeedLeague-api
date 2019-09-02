package com.immanuelqrw.speedleague.api.entity

import com.immanuelqrw.core.entity.BaseUniqueEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "`RaceRunner`")
data class RaceRunner(

    // ! Make Race and Runner unique constraint
    val race: Race,

    val runner: Runner,

    val time: Long?,

    val result: Result?,

    val placement: Int? // Convert placement to Enum?

) : BaseUniqueEntity()
