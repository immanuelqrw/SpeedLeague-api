package com.immanuelqrw.speedleague.api.entity

import com.immanuelqrw.core.entity.BaseUniqueEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "`League`")
data class League(

    @Column(unique = true)
    val name: String,

    // ? Parse from String
    val startedOn: LocalDateTime,

    @OneToMany(mappedBy = "`league`", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var races: List<Race> = emptyList()

) : BaseUniqueEntity()
