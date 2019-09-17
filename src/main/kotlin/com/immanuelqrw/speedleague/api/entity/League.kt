package com.immanuelqrw.speedleague.api.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
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
@Table(name = "League", uniqueConstraints = [UniqueConstraint(columnNames = ["name", "season", "tierLevel"])])
data class League(

    @Column(name = "name", nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: LeagueType = LeagueType.POOL,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "startedOn", nullable = false)
    val startedOn: LocalDateTime,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "endedOn")
    val endedOn: LocalDateTime? = null,

    // ! Min 0
    @Column(name = "defaultTime", nullable = false)
    val defaultTime: Long,

    // ! Min 0
    // ! Consider Renaming
    @Column(name = "defaultPoints", nullable = false)
    val defaultPoints: Int,

    @Column(name = "season", nullable = false)
    val season: Int,

    @Embedded
    @AttributeOverrides(value = [
        AttributeOverride(name = "name", column = Column(name = "tierName", nullable = false)),
        // ! Min(0)
        AttributeOverride(name = "level", column = Column(name = "tierLevel", nullable = false))
    ])
    val tier: Tier,

    @Column(name = "runnerLimit", nullable = false)
    val runnerLimit: Int,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "registrationEndedOn")
    val registrationEndedOn: LocalDateTime? = null

) : BaseUniqueEntity() {

    @JsonBackReference
    @OneToMany(mappedBy = "league", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var races: Set<Race> = emptySet()

    @JsonBackReference
    @OneToMany(mappedBy = "league", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var playoffRules: List<PlayoffRule> = emptyList()

    @JsonBackReference
    @OneToMany(mappedBy = "league", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var pointRules: Set<PointRule> = emptySet()

}
