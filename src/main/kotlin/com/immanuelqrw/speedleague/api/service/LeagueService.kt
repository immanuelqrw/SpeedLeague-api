package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.input.*
import com.immanuelqrw.speedleague.api.dto.update.LeagueDivisionShift as LeagueDivisionShiftUpdate
import com.immanuelqrw.speedleague.api.dto.input.League as LeagueInput
import com.immanuelqrw.speedleague.api.dto.output.League as LeagueOutput
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.LeagueSpeedrun
import com.immanuelqrw.speedleague.api.entity.Tier
import com.immanuelqrw.speedleague.api.service.seek.LeagueSeekService
import com.immanuelqrw.speedleague.api.service.seek.LeagueSpeedrunSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LeagueService {

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

    fun create(leagueInput: LeagueInput): LeagueOutput {
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

    fun findAll(search: String?): Iterable<LeagueOutput> {
        return leagueSeekService.findAll(search = search).map { league -> league.output }
    }

    fun endSeason(endSeason: EndSeason): LeagueOutput {
        return endSeason.run {
            val oldLeague: League = leagueSeekService.find(leagueName, season, tierLevel)
            leagueSeekService.endLeague(oldLeague, endedOn)

            oldLeague.output
        }
    }

    fun startNewSeason(startSeason: StartSeason): List<LeagueOutput> {
        return startSeason.run {
            val allLeagues: List<League> = leagueSeekService.findAllTiers(leagueName, season)
            val newLeagues: List<LeagueOutput> = allLeagues.map { oldLeague ->
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

            allLeagues.forEach { oldLeague ->
                leagueSeekService.endLeague(oldLeague, endedOn)
            }

            newLeagues
        }
    }

    fun addLowerTier(lowerTier: LowerTier): LeagueOutput {
        return lowerTier.run {
            val parentLeague: League = leagueSeekService.find(leagueName, season, parentTierLevel)

            // - Add more descriptive error message
            leagueSeekService.validateLeagueChange(parentLeague.endedOn)

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

    fun modifyDivisionShifts(leagueDivisionShift: LeagueDivisionShiftUpdate): LeagueOutput {

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
