package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.*
import com.immanuelqrw.speedleague.api.dto.update.LeagueDivisionShift as LeagueDivisionShiftUpdate
import com.immanuelqrw.speedleague.api.dto.input.League as LeagueInput
import com.immanuelqrw.speedleague.api.dto.output.League as LeagueOutput
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.LeagueSpeedrun
import com.immanuelqrw.speedleague.api.entity.Tier
import com.immanuelqrw.speedleague.api.service.*
import com.immanuelqrw.speedleague.api.service.seek.LeagueSeekService
import com.immanuelqrw.speedleague.api.service.seek.LeagueSpeedrunSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/league")
class LeagueController {

    @Autowired
    private lateinit var leagueSeekService: LeagueSeekService

    @Autowired
    private lateinit var playoffService: PlayoffService

    @Autowired
    private lateinit var pointService: PointService

    @Autowired
    private lateinit var seasonService: SeasonService

    @Autowired
    private lateinit var leagueSpeedrunSeekService: LeagueSpeedrunSeekService

    @Autowired
    private lateinit var divisionShiftService: DivisionShiftService

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
                registrationEndedOn = registrationEndedOn,
                promotions = promotions,
                relegations = relegations
            )
            val createdLeague: League = leagueSeekService.create(league)

            val leaguePlayoffRule = LeaguePlayoffRule(
                leagueName = name,
                season = createdLeague.season,
                tierLevel = createdLeague.tier.level,
                tierName = createdLeague.tier.name,
                qualifierRules = qualifierRules
            )

            playoffService.addRules(leaguePlayoffRule)

            val leaguePointRule = LeaguePointRule(
                leagueName = name,
                season = 1,
                tierLevel = 1,
                tierName = tierName,
                pointRules = pointRules
            )
            pointService.addRules(leaguePointRule)

            createdLeague.output
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<LeagueOutput> {
        return leagueSeekService.findAll(search = search).map { league -> league.output }
    }

    @PostMapping(path = ["/endSeason"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun endSeason(@RequestBody endSeason: EndSeason): LeagueOutput {
        return endSeason.run {
            val oldLeague: League = leagueSeekService.find(leagueName, season, tierLevel)
            oldLeague.endedOn ?: run {
                oldLeague.endedOn = endedOn
                leagueSeekService.create(oldLeague)
            }

            oldLeague.output
        }
    }

    @PostMapping(path = ["/startNewSeason"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun startNewSeason(@RequestBody startSeason: StartSeason): List<LeagueOutput> {
        return startSeason.run {
            val allLeagues: List<League> = leagueSeekService.findAllTiers(leagueName, season)
            val newLeagues: List<LeagueOutput> = allLeagues.map { oldLeague ->
                oldLeague.endedOn ?: run {
                    oldLeague.endedOn = endedOn
                    leagueSeekService.create(oldLeague)
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
                val createdLeague: League = leagueSeekService.create(league)

                qualifierRules?.let {
                    val leaguePlayoffRule = LeaguePlayoffRule(
                        leagueName = createdLeague.name,
                        season = createdLeague.season,
                        tierLevel = createdLeague.tier.level,
                        tierName = createdLeague.tier.name,
                        qualifierRules = it
                    )

                    playoffService.addRules(leaguePlayoffRule)
                }

                pointRules?.let {
                    val leaguePointRule = LeaguePointRule(
                        leagueName = createdLeague.name,
                        season = createdLeague.season,
                        tierLevel = createdLeague.tier.level,
                        tierName = createdLeague.tier.name,
                        pointRules = it
                    )

                    pointService.addRules(leaguePointRule)
                }

                copySpeedrunsToNewLeague(oldLeague, createdLeague)

                createdLeague.output
            }

            seasonService.shiftDivisions(allLeagues)

            newLeagues
        }
    }

    @PostMapping(path = ["/addLowerTier"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addLowerTier(@RequestBody lowerTier: LowerTier): LeagueOutput {
        return lowerTier.run {
            val parentLeague: League = leagueSeekService.find(leagueName, season, parentTierLevel)

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
                registrationEndedOn = registrationEndedOn,
                promotions = divisionShifts
            )
            val childLeague: League = leagueSeekService.create(league)

            // Update Parent league
            parentLeague.relegations = divisionShifts
            leagueSeekService.create(parentLeague)

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

            playoffService.addRules(leaguePlayoffRule)

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

            pointService.addRules(leaguePointRule)

            if (relegationRules != null && promotionRules != null) {

                val leagueRelegationRule = LeagueDivisionShiftRule(
                    leagueName = parentLeague.name,
                    season = parentLeague.season,
                    tierLevel = parentLeague.tier.level,
                    tierName = parentLeague.tier.name,
                    relegationRules = relegationRules
                )

                divisionShiftService.addRelegationRules(leagueRelegationRule)

                val leaguePromotionRule = LeagueDivisionShiftRule(
                    leagueName = childLeague.name,
                    season = childLeague.season,
                    tierLevel = childLeague.tier.level,
                    tierName = childLeague.tier.name,
                    promotionRules = promotionRules
                )

                divisionShiftService.addPromotionRules(leaguePromotionRule)
            }

            copySpeedrunsToNewLeague(parentLeague, childLeague)

            childLeague.output
        }
    }

    @PatchMapping(path = ["/divisionShifts"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun modifyDivisionShifts(@RequestBody leagueDivisionShift: LeagueDivisionShiftUpdate): LeagueOutput {

        val modifiedLeague: League = leagueSeekService.updateDivisionShifts(leagueDivisionShift)

        return modifiedLeague.output
    }

    private fun copySpeedrunsToNewLeague(sourceLeague: League, destinationLeague: League) {
        val sourceLeagueSpeedruns: Iterable<LeagueSpeedrun> = sourceLeague.run {
            leagueSpeedrunSeekService.findAllByLeague(name, season, tier.level)
        }

        sourceLeagueSpeedruns.forEach { oldLeagueSpeedrun ->
            val leagueSpeedrun = LeagueSpeedrun(
                league = destinationLeague,
                speedrun = oldLeagueSpeedrun.speedrun
            )

            leagueSpeedrunSeekService.create(leagueSpeedrun)
        }
    }

}
