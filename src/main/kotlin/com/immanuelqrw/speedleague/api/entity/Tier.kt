package com.immanuelqrw.speedleague.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.immanuelqrw.core.entity.BaseUniqueEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Tier")
data class Tier(

    // ! Consider making name Unique to allow easier searching since level doesn't matter
    @Column(name = "name", nullable = false)
    val name: String,

    // ! Min(0)
    @Column(name = "level", nullable = false)
    val level: Int

) : BaseUniqueEntity()
