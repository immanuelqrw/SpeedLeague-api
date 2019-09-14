package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.output.Standing
import com.immanuelqrw.speedleague.api.entity.*
import com.immanuelqrw.speedleague.api.exception.RaceUnfinishedException
import com.immanuelqrw.speedleague.api.repository.RaceRunnerRepository
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import com.immanuelqrw.speedleague.api.service.seek.PointRuleService
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class StandingService {
    
    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var raceRunnerService: RaceRunnerService

    @Autowired
    private lateinit var raceRunnerRepository: RaceRunnerRepository

    private fun racePoints(pointRules: Set<PointRule>): Map<Int, Int> {
        return pointRules.map { pointRule ->
            pointRule.placement to pointRule.amount
        }.toMap()
    }

    fun calculateStandings(leagueName: String): List<Standing> {
        val league: League = leagueService.findByName(leagueName)
        val races: Set<Race> = league.races

        val raceRunners: List<RaceRunner> = races.mapNotNull { race: Race ->
            val raceId: UUID = race.id
            raceRunnerService.findByRace(raceId)
        }

        val raceResults: Map<String, List<RaceRunner>> = raceRunners.groupBy { it.runner.name }

        val pointRules: Set<PointRule> = league.pointRules
        val pointsForPlacement: Map<Int, Int> = racePoints(pointRules)

        val standings: List<Standing> = raceResults.map { (runnerName: String, raceRunners: List<RaceRunner>) ->
            val points: Int = raceRunners.sumBy { raceRunner ->
                val placement: Int? = raceRunner.placement

                placement?.let {
                    pointsForPlacement[placement] ?: league.defaultPoints
                } ?: run {
                    0
                }
            }
            val raceCount: Int = raceRunners.size
            Standing(
                runnerName = runnerName,
                points = points,
                raceCount = raceRunners.size,
                pointsPerRace = points.toFloat() / raceCount,
                wins = raceRunners.filter { raceRunner -> raceRunner.placement == 1 }.size,
                averageTime = raceRunners.map { raceRunner -> raceRunner.time ?: league.defaultTime }.sum()
            )
        }

        // ? Possibly have multiple output sorts
        return standings.sortedBy { standing -> standing.points }
    }

    fun calculateRacePlacements(raceName: String): List<RaceRunner> {
        val raceRunners: List<RaceRunner> = raceRunnerService.findByRace(raceName)

        raceRunners.forEach { raceRunner ->
            if (raceRunner.outcome == Outcome.PENDING_VERIFICATION) {
                throw RaceUnfinishedException("Runner [${raceRunner.runner.name}]'s outcome in Race [${raceRunner.race.name}] is still Pending Verification")
            }
        }

        val results: Map<Long, List<RaceRunner>> = raceRunners.groupBy { raceRunner ->
            if (raceRunner.outcome == Outcome.DID_NOT_FINISH) {
                Long.MAX_VALUE
            }
            raceRunner.time ?: throw RaceUnfinishedException("Runner [${raceRunner.runner.name}] does not have a time in Race [${raceRunner.race.name}]")
        }.toSortedMap()

        var placement = 1
        results.forEach { (time: Long, raceRunners: List<RaceRunner>) ->
            if (time == Long.MAX_VALUE) {
                return@forEach
            }

            raceRunners.forEach { raceRunner ->
                raceRunner.placement = placement
                raceRunnerRepository.save(raceRunner)
            }
            placement += raceRunners.size
        }

        return results.values.flatten()
    }

}
