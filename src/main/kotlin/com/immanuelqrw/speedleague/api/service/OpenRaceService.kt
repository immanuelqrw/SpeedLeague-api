package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.entity.Outcome
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerService
import com.immanuelqrw.speedleague.api.service.seek.RaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OpenRaceService {

    @Autowired
    private lateinit var raceRunnerService: RaceRunnerService

    fun findOpenRaces(startedOn: LocalDateTime): List<Race> {
        val raceRunners: Iterable<RaceRunner> = raceRunnerService.findAll().filter { raceRunner ->
            raceRunner.race.startedOn > startedOn
        }

        return raceRunners
            .filter { raceRunner -> isRaceOpen(raceRunner.race.name) }
            .map { raceRunner -> raceRunner.race }
    }

    fun isRaceOpen(raceName: String): Boolean {
        val raceRunners: List<RaceRunner> = raceRunnerService.findByRace(raceName)

        return raceRunners.all { raceRunner ->
            raceRunner.outcome != Outcome.PENDING_VERIFICATION
        }
    }

}
