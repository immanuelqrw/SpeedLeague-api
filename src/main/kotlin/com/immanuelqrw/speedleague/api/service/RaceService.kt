package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.dto.input.Race as RaceInput
import com.immanuelqrw.speedleague.api.dto.output.Race as RaceOutput
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.exception.LeagueHasEndedException
import com.immanuelqrw.speedleague.api.service.seek.LeagueSeekService
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerSeekService
import com.immanuelqrw.speedleague.api.service.seek.RaceSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RaceService {

    @Autowired
    private lateinit var raceSeekService: RaceSeekService

    @Autowired
    private lateinit var leagueSeekService: LeagueSeekService

    @Autowired
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    fun create(raceInput: RaceInput): RaceOutput {
        return raceInput.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)

            val currentTime: LocalDateTime = LocalDateTime.now()
            league.endedOn?.let {
                if (currentTime >= it) {
                    throw LeagueHasEndedException("Race $raceName cannot be created due to league being over [Ended on $it]")
                }
            }

            // If League has ended, disallow races
            val race = Race(
                name = raceName ?: "racename", // ! Replace with name generator
                league = league,
                startedOn = startedOn
            )
            val createdRace: Race = raceSeekService.create(race)

            createdRace.output
        }
    }

    fun findAll(search: String?): Iterable<RaceOutput> {
        return raceSeekService.findAll(search = search).map { race -> race.output }
    }

    fun findAllRaces(runnerName: String): List<RaceOutput> {
        return raceRunnerSeekService.findAllByRunner(runnerName).map { raceRunner ->
            raceRunner.race.output
        }
    }

}
