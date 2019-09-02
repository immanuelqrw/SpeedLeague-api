package com.immanuelqrw.speedleague.api.entity

import com.immanuelqrw.core.entity.BaseUniqueEntity
import java.time.LocalDateTime

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "`League`")
data class League(

    @Column(unique = true)
    val name: String,

    // ? Parse from String
    val startedOn: LocalDateTime

) : BaseUniqueEntity()
