package com.immanuelqrw.speedleague.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.immanuelqrw.core.entity.BaseUniqueEntity
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "DistinctSystem", uniqueConstraints = [UniqueConstraint(columnNames = ["systemId", "region", "versionId"])])
data class DistinctSystem(

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "systemId", referencedColumnName = "id", nullable = false)
    val system: System,

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = true)
    val region: Region?,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "versionId", referencedColumnName = "id", nullable = true)
    val version: Version?

) : BaseUniqueEntity()
