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

    @Column(name = "`time`")
    val time: Long?,

    @Enumerated(EnumType.STRING)
    @Column(name = "`outcome`")
    val outcome: Outcome? = null,

    @Column(name = "`placement`")
    val placement: Int? = null // ? Convert placement to Enum

) : BaseUniqueEntity()
