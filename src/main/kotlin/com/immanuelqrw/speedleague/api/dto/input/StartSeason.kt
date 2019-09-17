package com.immanuelqrw.speedleague.api.dto.input

import com.immanuelqrw.core.util.DateTimeFormatter
import org.apache.tomcat.jni.Local
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime


data class StartSeason(

    val leagueName: String,

    val season: Int,

    val tierLevel: Int,

    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    val startedOn: LocalDateTime = LocalDateTime.now(),

    val defaultTime: Long? = null,

    val defaultPoints: Int? = null,

    val runnerLimit: Int? = null,

    val registrationEndedOn: LocalDateTime? = null,

    val qualifierRules: List<QualifierRule>? = null,

    val pointRules: List<PointRule>? = null

)
