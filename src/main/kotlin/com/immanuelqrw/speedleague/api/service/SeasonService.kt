package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.output.QualifiedRunner
import com.immanuelqrw.speedleague.api.dto.output.Standing
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.LeagueRunner
import com.immanuelqrw.speedleague.api.entity.Runner
import com.immanuelqrw.speedleague.api.service.seek.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SeasonService {
    
    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var leagueRunnerService: LeagueRunnerService

    @Autowired
    private lateinit var runnerService: RunnerService

    @Autowired
    private lateinit var promotionService: PromotionService

    @Autowired
    private lateinit var relegationService: RelegationService

    @Autowired
    private lateinit var standingService: StandingService

    // private

    fun promoteAndRelegate(leagues: List<League>) {
        leagues.forEach { league ->
            val standings: List<Standing> = standingService.calculateStandings(league.name, league.season, league.tier.level)

            val relegatedRunners: List<QualifiedRunner> = relegationService.matchQualifiedRunners(league.name, league.season, league.tier.level, standings)
            if (relegatedRunners.isNotEmpty()) {
                val relegatedLeague: League = leagueService.find(league.name, league.season, league.tier.level + 1)

                relegatedRunners.forEach { relegatedRunner ->
                    val leagueRunner = LeagueRunner(
                        league = relegatedLeague,
                        runner = runnerService.findByName(relegatedRunner.runnerName)
                    )

                    leagueRunnerService.create(leagueRunner)
                }
            }

            val promotedRunners: List<QualifiedRunner> = promotionService.matchQualifiedRunners(league.name, league.season, league.tier.level, standings)
            if (promotedRunners.isNotEmpty()) {
                val promotedLeague: League = leagueService.find(league.name, league.season, league.tier.level - 1)

                promotedRunners.forEach { promotedRunner ->
                    val leagueRunner = LeagueRunner(
                        league = promotedLeague,
                        runner = runnerService.findByName(promotedRunner.runnerName)
                    )

                    leagueRunnerService.create(leagueRunner)
                }
            }
        }
    }

}
