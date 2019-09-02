package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.output.Standing
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.entity.RaceRunner

fun calculateStandings(leagueName: String): List<Standing> {
    val league: League? = leagueService.findByName(leagueName)
    val races: List<Race>? = league?.races
    // ? Convert above to findRaces method

    val raceRunners: List<RaceRunner> = races?.map { race ->
        raceRunnerService.findByRace(race.id)
    }.flatten()

    val raceResults: Map<String, List<RaceRunner>> = raceRunners.groupBy { it.runner.name }
    val standings: List<Standing> = raceResults.map { (runnerName: String, raceRunners: List<RaceRunner>) ->
        val points: Int = raceRunners.sumBy { raceRunner -> racePoints(raceRunner.placement) }
        val raceCount: Int = raceRunners.size
        Standing(
            runnerName = runnerName,
            points = points,
            raceCount = raceRunners.size,
            pointsPerRace = points.toFloat() / raceCount,
            wins = raceRunners.filter { raceRunner -> raceRunner.placement == 1}.size
        )
    }

    // ? Possibly have multiple output sorts
    return standings.sortedBy { standing -> standing.points }
}
