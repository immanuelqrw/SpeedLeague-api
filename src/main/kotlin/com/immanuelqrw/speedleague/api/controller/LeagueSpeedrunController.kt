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

    private fun convertToOutput(leagueSpeedrun: LeagueSpeedrun): LeagueSpeedrunOutput {
        return leagueSpeedrun.speedrun.cart.run {
            LeagueSpeedrunOutput(
                leagueName = leagueSpeedrun.league.name,
                season = leagueSpeedrun.league.season,
                tierLevel = leagueSpeedrun.league.tier.level,
                tierName = leagueSpeedrun.league.tier.name,
                categoryName = leagueSpeedrun.speedrun.category.name,
                gameName = leagueSpeedrun.speedrun.cart.game.name,
                systemName = leagueSpeedrun.speedrun.cart.system.name,
                isEmulated = leagueSpeedrun.speedrun.cart.system.isEmulated,
                region = leagueSpeedrun.speedrun.cart.region,
                version = leagueSpeedrun.speedrun.cart.version
            )
        }
    }

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

            convertToOutput(createdLeagueSpeedrun)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<LeagueSpeedrunOutput> {
        return leagueSpeedrunService.findAll(search = search).map { leagueSpeedrun -> convertToOutput(leagueSpeedrun) }
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
            .map { leagueSpeedrun -> convertToOutput(leagueSpeedrun) }
    }

}
