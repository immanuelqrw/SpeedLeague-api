package com.immanuelqrw.speedleague.api.entity

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.immanuelqrw.core.entity.BaseUniqueEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "`League`")
data class League(

    @Column(unique = true)
    val name: String,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    val startedOn: LocalDateTime,

    @OneToMany(mappedBy = "league", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var races: List<Race> = emptyList()

) : BaseUniqueEntity()
