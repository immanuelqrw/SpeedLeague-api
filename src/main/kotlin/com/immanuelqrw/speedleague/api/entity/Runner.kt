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
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "`Runner`")
data class Runner(

    @Column(name = "`name`", unique = true, nullable = false)
    val name: String,

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @CreatedDate
    @CreationTimestamp
    @Column(name = "`joinedOn`", updatable = false, nullable = false)
    val joinedOn: LocalDateTime = LocalDateTime.now()

) : BaseUniqueEntity()
