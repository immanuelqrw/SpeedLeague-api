package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.input.LeaguePromotionRule
import com.immanuelqrw.speedleague.api.dto.output.QualifiedRunner
import com.immanuelqrw.speedleague.api.dto.output.Standing
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.PromotionRule
import com.immanuelqrw.speedleague.api.entity.Qualifier
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import com.immanuelqrw.speedleague.api.service.seek.PromotionRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PromotionService {
    
    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var promotionRuleService: PromotionRuleService

    private fun attachPromotionRules(league: League, leagueRule: LeaguePromotionRule): List<PromotionRule> {
        return leagueRule.qualifierRules.mapIndexed { index, qualifierRule ->
            val promotionRule = PromotionRule(
                qualifier = qualifierRule.qualifier,
                count = qualifierRule.count,
                league = league,
                order = index + 1
            )

            promotionRuleService.create(promotionRule)
        }
    }

    fun addPromotionRules(leagueRule: LeaguePromotionRule): List<PromotionRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            attachPromotionRules(league, leagueRule)
        }
    }

    fun replacePromotionRules(leagueRule: LeaguePromotionRule): List<PromotionRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            promotionRuleService.deleteAll(league.promotionRules)

            attachPromotionRules(league, leagueRule)
        }
    }

    private fun findTopRunners(top: Int, standings: List<Standing>): MutableMap<Qualifier, List<QualifiedRunner>> {
        return mutableMapOf(
            Qualifier.POINTS to standings.sortedByDescending { it.points }.take(top),
            Qualifier.POINTS_PER_RACE to standings.sortedByDescending { it.pointsPerRace }.take(top),
            Qualifier.WINS to standings.sortedByDescending { it.wins }.take(top),
            Qualifier.AVERAGE_TIME to standings.sortedByDescending { it.averageTime }.take(top)
        )
    }

    fun matchQualifiedRunners(leagueName: String, season: Int, tierLevel: Int, standings: List<Standing>): List<QualifiedRunner> {
        val league: League = leagueService.find(leagueName, season, tierLevel)

        val top: Int = league.promotions
        val topRunners: MutableMap<Qualifier, List<QualifiedRunner>> = findTopRunners(top, standings)

        val qualifiedRunners: LinkedHashSet<QualifiedRunner> = linkedSetOf()

        // ! There might be an issue with infinite loop if less runners than top
        val orderedPromotionRules: List<PromotionRule> = league.promotionRules.sortedBy { it.order }
        while (qualifiedRunners.size < top) {
            orderedPromotionRules.forEach { promotionRule ->
                val qualifier: Qualifier = promotionRule.qualifier
                val possiblyQualifiedRunners: List<QualifiedRunner> = topRunners[qualifier]
                    ?: throw IllegalStateException("Each Qualifier should have a list of possible runners")
                // - Consider outputting each qualification method
                val orderedQualifiedRunners = possiblyQualifiedRunners.take(promotionRule.count)

                qualifiedRunners.addAll(orderedQualifiedRunners)
                topRunners[qualifier] = possiblyQualifiedRunners.drop(promotionRule.count)
            }
        }

        return qualifiedRunners.take(top)

    }

}
