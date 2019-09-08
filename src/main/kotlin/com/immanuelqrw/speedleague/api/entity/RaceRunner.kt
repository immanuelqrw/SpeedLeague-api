package com.immanuelqrw.speedleague.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.immanuelqrw.core.entity.BaseUniqueEntity
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "`RaceRunner`", uniqueConstraints = [UniqueConstraint(columnNames = ["`raceId`", "`runnerId`"])])
data class RaceRunner(

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "`raceId`", referencedColumnName = "`id`", nullable = false)
    val race: Race,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "`runnerId`", referencedColumnName = "`id`", nullable = false)
    val runner: Runner,

    @Column(name = "`time`")
    var time: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "`outcome`", nullable = false)
    var outcome: Outcome = Outcome.PENDING_VERIFICATION,

    @Column(name = "`placement`")
    var placement: Int? = null // ? Convert placement to Enum

) : BaseUniqueEntity()
