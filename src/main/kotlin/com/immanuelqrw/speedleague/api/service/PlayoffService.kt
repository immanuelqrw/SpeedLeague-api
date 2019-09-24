package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.input.LeaguePlayoffRule as LeaguePlayoffRuleInput
import com.immanuelqrw.speedleague.api.dto.output.PlayoffRule as PlayoffRuleOutput
import com.immanuelqrw.speedleague.api.dto.output.QualifiedRunner
import com.immanuelqrw.speedleague.api.dto.output.Standing
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.PlayoffRule
import com.immanuelqrw.speedleague.api.entity.Qualifier
import com.immanuelqrw.speedleague.api.service.seek.LeagueSeekService
import com.immanuelqrw.speedleague.api.service.seek.PlayoffRuleSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlayoffService {
    
    @Autowired
    private lateinit var leagueSeekService: LeagueSeekService

    @Autowired
    private lateinit var playoffRuleSeekService: PlayoffRuleSeekService

    private fun attachRules(league: League, leagueRule: LeaguePlayoffRuleInput): List<PlayoffRule> {
        return leagueRule.qualifierRules.mapIndexed { index, qualifierRule ->
            val playoffRule = PlayoffRule(
                qualifier = qualifierRule.qualifier,
                count = qualifierRule.count,
                league = league,
                order = index + 1
            )

            playoffRuleSeekService.create(playoffRule)
        }
    }

    fun addRules(leagueRule: LeaguePlayoffRuleInput): List<PlayoffRule> {
        return leagueRule.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)

            // - Add more descriptive error message
            leagueSeekService.validateLeagueChange(league.endedOn)

            attachRules(league, leagueRule)
        }
    }

    private fun replaceRules(leagueRule: LeaguePlayoffRuleInput): List<PlayoffRule> {
        return leagueRule.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)

            // - Add more descriptive error message
            leagueSeekService.validateLeagueChange(league.endedOn)

            playoffRuleSeekService.deleteAll(league.playoffRules)

            attachRules(league, leagueRule)
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

    fun matchQualifiedRunners(leagueName: String, season: Int, tierLevel: Int, top: Int, standings: List<Standing>): List<QualifiedRunner> {
        val topRunners: MutableMap<Qualifier, List<QualifiedRunner>> = findTopRunners(top, standings)

        val league: League = leagueSeekService.find(leagueName, season, tierLevel)

        val qualifiedRunners: LinkedHashSet<QualifiedRunner> = linkedSetOf()

        // ! There might be an issue with infinite loop if less runners than top
        val orderedPlayoffRules: List<PlayoffRule> = league.playoffRules.sortedBy { it.order }
        while (qualifiedRunners.size < top) {
            orderedPlayoffRules.forEach { playoffRule ->
                val qualifier: Qualifier = playoffRule.qualifier
                val possiblyQualifiedRunners: List<QualifiedRunner> = topRunners[qualifier]
                    ?: throw IllegalStateException("Each Qualifier should have a list of possible runners")
                // - Consider outputting each qualification method
                val orderedQualifiedRunners = possiblyQualifiedRunners.take(playoffRule.count)

                qualifiedRunners.addAll(orderedQualifiedRunners)
                topRunners[qualifier] = possiblyQualifiedRunners.drop(playoffRule.count)
            }
        }

        return qualifiedRunners.take(top)

    }

    fun create(leaguePlayoffRuleInput: LeaguePlayoffRuleInput): List<PlayoffRuleOutput> {
        return addRules(leaguePlayoffRuleInput).map { playoffRule ->
            playoffRule.output
        }.sortedBy { it.order }
    }

    fun findAll(search: String?): Iterable<PlayoffRuleOutput> {
        return playoffRuleSeekService.findAllActive(search = search).map { playoffRule ->
            playoffRule.output
        }.sortedBy { it.order }
    }

    fun findAll(leagueName: String, season: Int, tierLevel: Int): List<PlayoffRuleOutput> {
        return playoffRuleSeekService.findAllByLeague(leagueName, season, tierLevel).map { playoffRule ->
            playoffRule.output
        }.sortedBy { it.order }
    }

    fun replace(leaguePlayoffRuleInput: LeaguePlayoffRuleInput): List<PlayoffRuleOutput> {
        return replaceRules(leaguePlayoffRuleInput).map { playoffRule ->
            playoffRule.output
        }.sortedBy { it.order }
    }

}
