package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.input.LeagueDivisionShiftRule
import com.immanuelqrw.speedleague.api.dto.input.QualifierRule
import com.immanuelqrw.speedleague.api.dto.output.QualifiedRunner
import com.immanuelqrw.speedleague.api.dto.output.Standing
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.DivisionShiftRule
import com.immanuelqrw.speedleague.api.entity.Qualifier
import com.immanuelqrw.speedleague.api.entity.Shift
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import com.immanuelqrw.speedleague.api.service.seek.DivisionShiftRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DivisionShiftService {
    
    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var divisionShiftRuleService: DivisionShiftRuleService

    private fun attachDivisionShiftRules(league: League, qualifierRules: List<QualifierRule>, shift: Shift): List<DivisionShiftRule> {
        return qualifierRules.mapIndexed { index, qualifierRule ->
            val divisionShiftRule = DivisionShiftRule(
                qualifier = qualifierRule.qualifier,
                count = qualifierRule.count,
                shift = shift,
                league = league,
                order = index + 1
            )

            divisionShiftRuleService.create(divisionShiftRule)
        }
    }

    private fun attachDivisionShiftRules(league: League, leagueRule: LeagueDivisionShiftRule): List<DivisionShiftRule> {
        val promotionRules: List<DivisionShiftRule> = attachDivisionShiftRules(league, leagueRule.promotionRules, Shift.PROMOTION)
        val relegationRules: List<DivisionShiftRule> = attachDivisionShiftRules(league, leagueRule.relegationRules, Shift.RELEGATION)

        return promotionRules.union(relegationRules).toList()
    }

    fun addDivisionShiftRules(leagueRule: LeagueDivisionShiftRule): List<DivisionShiftRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            attachDivisionShiftRules(league, leagueRule)
        }
    }

    fun addPromotionRules(leagueRule: LeagueDivisionShiftRule): List<DivisionShiftRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            attachDivisionShiftRules(league, leagueRule.promotionRules, Shift.PROMOTION)
        }
    }

    fun addRelegationRules(leagueRule: LeagueDivisionShiftRule): List<DivisionShiftRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            attachDivisionShiftRules(league, leagueRule.relegationRules, Shift.RELEGATION)
        }
    }

    fun replaceDivisionShiftRules(leagueRule: LeagueDivisionShiftRule): List<DivisionShiftRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            divisionShiftRuleService.deleteAll(league.divisionShiftRules)

            attachDivisionShiftRules(league, leagueRule)
        }
    }

    fun replacePromotionRules(leagueRule: LeagueDivisionShiftRule): List<DivisionShiftRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            divisionShiftRuleService.deleteAll(league.promotionRules)

            attachDivisionShiftRules(league, leagueRule.promotionRules, Shift.PROMOTION)
        }
    }

    fun replaceRelegationRules(leagueRule: LeagueDivisionShiftRule): List<DivisionShiftRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            divisionShiftRuleService.deleteAll(league.relegationRules)

            attachDivisionShiftRules(league, leagueRule.relegationRules, Shift.RELEGATION)
        }
    }

    private fun findQualifiedRunners(top: Int, standings: List<Standing>, shift: Shift): MutableMap<Qualifier, List<QualifiedRunner>> {
        return when(shift) {
            Shift.PROMOTION -> {
                mutableMapOf(
                    Qualifier.POINTS to standings.sortedByDescending { it.points }.take(top),
                    Qualifier.POINTS_PER_RACE to standings.sortedByDescending { it.pointsPerRace }.take(top),
                    Qualifier.WINS to standings.sortedByDescending { it.wins }.take(top),
                    Qualifier.AVERAGE_TIME to standings.sortedByDescending { it.averageTime }.take(top)
                )
            }
            Shift.RELEGATION -> {
                mutableMapOf(
                    Qualifier.POINTS to standings.sortedBy { it.points }.take(top),
                    Qualifier.POINTS_PER_RACE to standings.sortedBy { it.pointsPerRace }.take(top),
                    Qualifier.WINS to standings.sortedBy { it.wins }.take(top),
                    Qualifier.AVERAGE_TIME to standings.sortedBy { it.averageTime }.take(top)
                )
            }
        }
    }

    fun matchQualifiedRunners(leagueName: String, season: Int, tierLevel: Int, standings: List<Standing>, shift: Shift): List<QualifiedRunner> {
        val league: League = leagueService.find(leagueName, season, tierLevel)
        val qualifiedRunners: LinkedHashSet<QualifiedRunner> = linkedSetOf()

        val top: Int = when(shift) {
            Shift.PROMOTION -> league.promotions
            Shift.RELEGATION -> league.relegations
        }
        val topRunners: MutableMap<Qualifier, List<QualifiedRunner>> = findQualifiedRunners(top, standings, shift)
        val orderedDivisionShiftRules: List<DivisionShiftRule> = when(shift) {
            Shift.PROMOTION -> league.promotionRules.sortedBy { it.order }
            Shift.RELEGATION -> league.relegationRules.sortedBy { it.order }
        }

        // ! There might be an issue with infinite loop if less runners than top
        while (qualifiedRunners.size < top) {
            orderedDivisionShiftRules.forEach { divisionShiftRule ->
                val qualifier: Qualifier = divisionShiftRule.qualifier
                val possiblyQualifiedRunners: List<QualifiedRunner> = topRunners[qualifier]
                    ?: throw IllegalStateException("Each Qualifier should have a list of possible runners")
                // - Consider outputting each qualification method
                val orderedQualifiedRunners = possiblyQualifiedRunners.take(divisionShiftRule.count)

                qualifiedRunners.addAll(orderedQualifiedRunners)
                topRunners[qualifier] = possiblyQualifiedRunners.drop(divisionShiftRule.count)
            }
        }

        return qualifiedRunners.take(top)

    }

}
