package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.output.Standing
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class StandingService {
    
    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var raceRunnerService: RaceRunnerService

    private fun findRaces(leagueName: String): List<Race> {
        val league: League = leagueService.findByName(leagueName)
        return league.races
    }

    private fun racePoints(place: Int?): Int {
        return when (place) {
            null -> 0
            1 -> 10
            2 -> 7
            3 -> 5
            4 -> 4
            5 -> 3
            6 -> 2
            else -> 1
        }
    }

    fun calculateStandings(leagueName: String): List<Standing> {
        val races: List<Race> = findRaces(leagueName)

        val raceRunners: List<RaceRunner> = races.mapNotNull { race: Race ->
            val raceId: UUID = race.id
            raceRunnerService.findByRace(raceId)
        }

        val raceResults: Map<String, List<RaceRunner>> = raceRunners.groupBy { it.runner.name }
        val standings: List<Standing> = raceResults.map { (runnerName: String, raceRunners: List<RaceRunner>) ->
            val points: Int = raceRunners.sumBy { raceRunner -> racePoints(raceRunner.placement) }
            val raceCount: Int = raceRunners.size
            Standing(
                runnerName = runnerName,
                points = points,
                raceCount = raceRunners.size,
                pointsPerRace = points.toFloat() / raceCount,
                wins = raceRunners.filter { raceRunner -> raceRunner.placement == 1 }.size
            )
        }

        // ? Possibly have multiple output sorts
        return standings.sortedBy { standing -> standing.points }
    }

}
