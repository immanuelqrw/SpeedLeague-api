package com.immanuelqrw.speedleague.api.entity

import com.immanuelqrw.core.entity.BaseUniqueEntity
import com.immanuelqrw.core.util.DateTimeFormatter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "`League`")
data class League(

    @Column(name = "`name`", unique = true)
    val name: String,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "`startedOn`")
    val startedOn: LocalDateTime,

    @OneToMany(mappedBy = "league", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var races: List<Race> = emptyList()

) : BaseUniqueEntity()
