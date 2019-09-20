package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.entity.*
import com.immanuelqrw.speedleague.api.dto.input.LeagueSpeedrun as LeagueSpeedrunInput
import com.immanuelqrw.speedleague.api.dto.output.LeagueSpeedrun as LeagueSpeedrunOutput
import com.immanuelqrw.speedleague.api.service.seek.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LeagueSpeedrunService {

    @Autowired
    private lateinit var leagueSpeedrunSeekService: LeagueSpeedrunSeekService

    @Autowired
    private lateinit var leagueSeekService: LeagueSeekService

    @Autowired
    private lateinit var speedrunSeekService: SpeedrunSeekService

    fun create(leagueSpeedrunInput: LeagueSpeedrunInput): LeagueSpeedrunOutput {
        return leagueSpeedrunInput.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)
            val speedrun: Speedrun = speedrunSeekService.find(categoryName, gameName, systemName, isEmulated, region, version)

            // - Add more descriptive error message
            leagueSeekService.validateLeagueChange(league.endedOn)
            val leagueSpeedrun = LeagueSpeedrun(
                league = league,
                speedrun = speedrun
            )
            val createdLeagueSpeedrun: LeagueSpeedrun = leagueSpeedrunSeekService.create(leagueSpeedrun)

            createdLeagueSpeedrun.output
        }
    }

    fun findAll(search: String?): Iterable<LeagueSpeedrunOutput> {
        return leagueSpeedrunSeekService.findAll(search = search).map { leagueSpeedrun -> leagueSpeedrun.output }
    }

    fun findAll(
        leagueName: String?,
        leagueType: LeagueType?,
        categoryName: String?,
        gameName: String?,
        systemName: String?,
        isEmulated: Boolean?,
        region: Region?,
        version: String?
    ): Iterable<LeagueSpeedrunOutput> {
        return leagueSpeedrunSeekService
            .findAll(leagueName, leagueType, categoryName, gameName, systemName, isEmulated, region, version)
            .map { leagueSpeedrun -> leagueSpeedrun.output }
    }

}
