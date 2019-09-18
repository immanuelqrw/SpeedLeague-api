package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.*
import com.immanuelqrw.speedleague.api.dto.input.League as LeagueInput
import com.immanuelqrw.speedleague.api.dto.output.League as LeagueOutput
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.Tier
import com.immanuelqrw.speedleague.api.repository.LeagueRepository
import com.immanuelqrw.speedleague.api.service.PlayoffService
import com.immanuelqrw.speedleague.api.service.PointService
import com.immanuelqrw.speedleague.api.service.SeasonService
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/league")
class LeagueController {

    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var playoffService: PlayoffService

    @Autowired
    private lateinit var pointService: PointService

    @Autowired
    private lateinit var seasonService: SeasonService

    private fun convertToOutput(league: League): LeagueOutput {
        return league.run {
            LeagueOutput(
                name = name,
                type = type,
                startedOn = startedOn,
                endedOn = endedOn,
                defaultTime = defaultTime,
                defaultPoints = defaultPoints,
                season = season,
                tierLevel = tier.level,
                tierName = tier.name,
                runnerLimit = runnerLimit,
                registrationEndedOn = registrationEndedOn
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leagueInput: LeagueInput): LeagueOutput {
        return leagueInput.run {

            val league = League(
                name = name,
                type = type,
                startedOn = startedOn,
                endedOn = null,
                defaultTime = defaultTime,
                defaultPoints = defaultPoints,
                season = 1,
                tier = Tier(name = tierName, level = 1),
                runnerLimit = runnerLimit,
                registrationEndedOn = registrationEndedOn
            )
            val createdLeague: League = leagueService.create(league)

            val leaguePlayoffRule = LeaguePlayoffRule(
                leagueName = name,
                season = createdLeague.season,
                tierLevel = createdLeague.tier.level,
                tierName = createdLeague.tier.name,
                qualifierRules = qualifierRules
            )

            playoffService.addPlayoffRules(leaguePlayoffRule)

            val leaguePointRule = LeaguePointRule(
                leagueName = name,
                season = 1,
                tierLevel = 1,
                tierName = tierName,
                pointRules = pointRules
            )
            pointService.addPointRules(leaguePointRule)

            convertToOutput(createdLeague)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<LeagueOutput> {
        return leagueService.findAll(search = search).map { league -> convertToOutput(league) }
    }

    @PostMapping(path = ["/endSeason"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun endSeason(@RequestBody endSeason: EndSeason): LeagueOutput {
        return endSeason.run {
            val oldLeague: League = leagueService.find(leagueName, season, tierLevel)
            oldLeague.endedOn ?: run {
                oldLeague.endedOn = endedOn
                leagueService.create(oldLeague)
            }

            convertToOutput(oldLeague)
        }
    }

    @PostMapping(path = ["/startNewSeason"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun startNewSeason(@RequestBody startSeason: StartSeason): List<LeagueOutput> {
        return startSeason.run {
            val allLeagues: List<League> = leagueService.findAllTiers(leagueName, season)
            val newLeagues: List<LeagueOutput> = allLeagues.map { oldLeague ->
                oldLeague.endedOn?.let {
                    oldLeague.endedOn = LocalDateTime.now()
                    leagueService.create(oldLeague)
                }

                val league = League(
                    name = oldLeague.name,
                    type = oldLeague.type,
                    startedOn = startedOn,
                    endedOn = null,
                    defaultTime = oldLeague.defaultTime,
                    defaultPoints = oldLeague.defaultPoints,
                    season = season + 1,
                    tier = Tier(name = oldLeague.tier.name, level = oldLeague.tier.level),
                    runnerLimit =  oldLeague.runnerLimit,
                    registrationEndedOn = registrationEndedOn
                )
                val createdLeague: League = leagueService.create(league)

                qualifierRules?.let {
                    val leaguePlayoffRule = LeaguePlayoffRule(
                        leagueName = createdLeague.name,
                        season = createdLeague.season,
                        tierLevel = createdLeague.tier.level,
                        tierName = createdLeague.tier.name,
                        qualifierRules = it
                    )

                    playoffService.addPlayoffRules(leaguePlayoffRule)
                }

                pointRules?.let {
                    val leaguePointRule = LeaguePointRule(
                        leagueName = createdLeague.name,
                        season = createdLeague.season,
                        tierLevel = createdLeague.tier.level,
                        tierName = createdLeague.tier.name,
                        pointRules = it
                    )

                    pointService.addPointRules(leaguePointRule)
                }

                convertToOutput(createdLeague)
            }

            seasonService.promoteAndRelegate(allLeagues)

            newLeagues
        }
    }

    @PostMapping(path = ["/addLowerTier"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addLowerTier(@RequestBody lowerTier: LowerTier): LeagueOutput {
        return lowerTier.run {
            val parentLeague: League = leagueService.find(leagueName, season, parentTierLevel)

            val league = League(
                name = leagueName,
                type = parentLeague.type,
                startedOn = startedOn,
                endedOn = null,
                defaultTime = defaultTime ?: parentLeague.defaultTime,
                defaultPoints = defaultPoints ?: parentLeague.defaultPoints,
                season = season,
                tier = Tier(name = tierName, level = parentLeague.tier.level + 1),
                runnerLimit = runnerLimit ?: parentLeague.runnerLimit,
                registrationEndedOn = registrationEndedOn
            )
            val childLeague: League = leagueService.create(league)

            val childQualifierRules: List<QualifierRule> = qualifierRules ?: parentLeague.playoffRules.map { playoffRule ->
                QualifierRule(
                    qualifier = playoffRule.qualifier,
                    count = playoffRule.count
                )
            }
            val leaguePlayoffRule = LeaguePlayoffRule(
                leagueName = childLeague.name,
                season = childLeague.season,
                tierLevel = childLeague.tier.level,
                tierName = childLeague.tier.name,
                qualifierRules = childQualifierRules
            )

            playoffService.addPlayoffRules(leaguePlayoffRule)

            val childPointRules: List<PointRule> = pointRules ?: parentLeague.pointRules.map { pointRule ->
                PointRule(
                    placement = pointRule.placement,
                    amount = pointRule.amount
                )
            }
            val leaguePointRule = LeaguePointRule(
                leagueName = childLeague.name,
                season = childLeague.season,
                tierLevel = childLeague.tier.level,
                tierName = childLeague.tier.name,
                pointRules = childPointRules
            )

            pointService.addPointRules(leaguePointRule)

            // ! Add promotion and relegation rules

            convertToOutput(childLeague)
        }
    }

}
