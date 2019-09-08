package com.immanuelqrw.speedleague.api.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.immanuelqrw.core.entity.BaseUniqueEntity
import com.immanuelqrw.core.util.DateTimeFormatter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "`League`")
data class League(

    @Column(name = "`name`", unique = true, nullable = false)
    val name: String,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "`startedOn`", nullable = false)
    val startedOn: LocalDateTime

) : BaseUniqueEntity() {

    @JsonBackReference
    @OneToMany(mappedBy = "league", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var races: List<Race> = emptyList()

}
