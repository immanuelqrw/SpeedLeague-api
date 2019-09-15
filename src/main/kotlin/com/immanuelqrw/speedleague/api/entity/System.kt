package com.immanuelqrw.speedleague.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.immanuelqrw.core.entity.BaseUniqueEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "System", uniqueConstraints = [UniqueConstraint(columnNames = ["name", "isEmulated"])])
data class System(

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "isEmulated", nullable = false)
    val isEmulated: Boolean = false

) : BaseUniqueEntity()
