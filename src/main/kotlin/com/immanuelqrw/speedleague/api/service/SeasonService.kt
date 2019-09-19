package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.output.QualifiedRunner
import com.immanuelqrw.speedleague.api.dto.output.Standing
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.LeagueRunner
import com.immanuelqrw.speedleague.api.entity.Shift
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
    private lateinit var standingService: StandingService

    @Autowired
    private lateinit var divisionShiftService: DivisionShiftService

    fun shiftDivisions(leagues: List<League>) {
        leagues.forEach { league ->
            val standings: List<Standing> = standingService.calculateStandings(league.name, league.season, league.tier.level)

            val relegatedRunners: List<QualifiedRunner> = divisionShiftService.matchQualifiedRunners(league.name, league.season, league.tier.level, standings, Shift.RELEGATION)
            if (relegatedRunners.isNotEmpty()) {
                val relegatedLeague: League = leagueService.find(league.name, league.season + 1, league.tier.level + 1)

                relegatedRunners.forEach { relegatedRunner ->
                    val leagueRunner = LeagueRunner(
                        league = relegatedLeague,
                        runner = runnerService.findByName(relegatedRunner.runnerName)
                    )

                    leagueRunnerService.create(leagueRunner)
                }
            }

            val promotedRunners: List<QualifiedRunner> = divisionShiftService.matchQualifiedRunners(league.name, league.season, league.tier.level, standings, Shift.PROMOTION)
            if (promotedRunners.isNotEmpty()) {
                val promotedLeague: League = leagueService.find(league.name, league.season + 1, league.tier.level - 1)

                promotedRunners.forEach { promotedRunner ->
                    val leagueRunner = LeagueRunner(
                        league = promotedLeague,
                        runner = runnerService.findByName(promotedRunner.runnerName)
                    )

                    leagueRunnerService.create(leagueRunner)
                }
            }

            val relegatedRunnerNames: Set<String> = relegatedRunners.map { relegatedRunner -> relegatedRunner.runnerName }.toSet()
            val promotedRunnerNames: Set<String> = promotedRunners.map { promotedRunner -> promotedRunner.runnerName }.toSet()
            val leagueRunners: Set<LeagueRunner> = leagueRunnerService.findAllByLeague(league.name, league.season, league.tier.level).toSet()

            leagueRunners.forEach { leagueRunner ->
                if (!relegatedRunnerNames.contains(leagueRunner.runner.name) && !promotedRunnerNames.contains(leagueRunner.runner.name)) {
                    val newLeague: League = leagueService.find(league.name, league.season + 1, league.tier.level)
                    val newLeagueRunner = LeagueRunner(
                        league = newLeague,
                        runner = leagueRunner.runner
                    )
                    leagueRunnerService.create(newLeagueRunner)
                }
            }
        }
    }

}
