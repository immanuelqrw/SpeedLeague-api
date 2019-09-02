package com.immanuelqrw.speedleague.api.entity

import com.immanuelqrw.core.entity.BaseUniqueEntity
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table(name = "`Runner`")
data class Runner(

    @Column(unique = true)
    val name: String,

    val joinedOn: LocalDateTime

) : BaseUniqueEntity()
