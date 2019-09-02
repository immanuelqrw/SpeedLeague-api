package com.immanuelqrw.speedleague.api.entity

import com.immanuelqrw.core.entity.BaseUniqueEntity
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "`Race`")
data class Race(

    val league: League,

    @Column(unique = true)
    val name: String,

    val startedOn: LocalDateTime

) : BaseUniqueEntity()
