package com.immanuelqrw.speedleague.api.entity

import com.immanuelqrw.core.entity.BaseUniqueEntity
import javax.persistence.*

@Entity
@Table(name = "`RaceRunner`", uniqueConstraints = [UniqueConstraint(columnNames = ["`raceId`", "`runnerId`"])])
data class RaceRunner(

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "`raceId`", referencedColumnName = "`id`")
    val race: Race,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "`runnerId`", referencedColumnName = "`id`")
    val runner: Runner,

    val time: Long?,

    val result: Result?,

    val placement: Int? // Convert placement to Enum?

) : BaseUniqueEntity()
