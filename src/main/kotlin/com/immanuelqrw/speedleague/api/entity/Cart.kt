package com.immanuelqrw.speedleague.api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.immanuelqrw.core.entity.BaseUniqueEntity
import com.immanuelqrw.core.util.DateTimeFormatter
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "Cart", uniqueConstraints = [UniqueConstraint(columnNames = ["gameId", "systemId", "region", "version"])])
data class Cart(

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "gameId", referencedColumnName = "id", nullable = false)
    val game: Game,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "systemId", referencedColumnName = "id", nullable = false)
    val system: System,

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    val region: Region = Region.ANY,

    @Column(name = "version", nullable = false)
    val version: String = "ANY"

) : BaseUniqueEntity()
