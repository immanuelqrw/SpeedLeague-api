package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.output.Race as RaceOutput
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.service.OpenRaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/openRace")
class OpenRaceController {

    @Autowired
    private lateinit var openRaceService: OpenRaceService

    private fun convertToOutput(race: Race): RaceOutput {
        return race.run {
            RaceOutput(
                name = name,
                leagueName = league.name,
                season = league.season,
                tierLevel = league.tier.level,
                tierName = league.tier.name,
                startedOn = startedOn
            )
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @RequestParam("startedOn")
        startedOn: LocalDateTime
    ): List<RaceOutput> {
        return openRaceService.findOpenRaces(startedOn).map { race -> convertToOutput(race) }
    }

    @GetMapping(path = ["/isRaceCompleted/{race}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("race")
        raceName: String
    ): Boolean {
        return openRaceService.isRaceOpen(raceName)
    }

}
