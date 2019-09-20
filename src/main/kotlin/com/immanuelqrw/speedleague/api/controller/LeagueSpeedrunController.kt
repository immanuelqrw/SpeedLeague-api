package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.entity.*
import com.immanuelqrw.speedleague.api.dto.input.LeagueSpeedrun as LeagueSpeedrunInput
import com.immanuelqrw.speedleague.api.dto.output.LeagueSpeedrun as LeagueSpeedrunOutput
import com.immanuelqrw.speedleague.api.service.seek.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/leagueSpeedrun")
class LeagueSpeedrunController {

    @Autowired
    private lateinit var leagueSpeedrunService: LeagueSpeedrunService

    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var speedrunService: SpeedrunService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leagueSpeedrunInput: LeagueSpeedrunInput): LeagueSpeedrunOutput {
        return leagueSpeedrunInput.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)
            val speedrun: Speedrun = speedrunService.find(categoryName, gameName, systemName, isEmulated, region, version)

            val leagueSpeedrun = LeagueSpeedrun(
                league = league,
                speedrun = speedrun
            )
            val createdLeagueSpeedrun: LeagueSpeedrun = leagueSpeedrunService.create(leagueSpeedrun)

            createdLeagueSpeedrun.output
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<LeagueSpeedrunOutput> {
        return leagueSpeedrunService.findAll(search = search).map { leagueSpeedrun -> leagueSpeedrun.output }
    }

    @GetMapping(path = ["/deepSearch"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("league")
        leagueName: String?,
        @RequestParam("type")
        leagueType: LeagueType?,
        @RequestParam("category")
        categoryName: String?,
        @RequestParam("game")
        gameName: String?,
        @RequestParam("system")
        systemName: String?,
        @RequestParam("isEmulated")
        isEmulated: Boolean?,
        @RequestParam("region")
        region: Region?,
        @RequestParam("version")
        version: String?
    ): Iterable<LeagueSpeedrunOutput> {
        return leagueSpeedrunService
            .findAll(leagueName, leagueType, categoryName, gameName, systemName, isEmulated, region, version)
            .map { leagueSpeedrun -> leagueSpeedrun.output }
    }

}
