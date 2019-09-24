package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.output.Race as RaceOutput
import com.immanuelqrw.speedleague.api.entity.Outcome
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OpenRaceService {

    @Autowired
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    fun findOpenRaces(startedOn: LocalDateTime): List<RaceOutput> {
        val raceRunners: Iterable<RaceRunner> = raceRunnerSeekService.findAllActive().filter { raceRunner ->
            raceRunner.race.startedOn > startedOn
        }

        return raceRunners
            .filter { raceRunner -> isRaceOpen(raceRunner.race.name) }
            .map { raceRunner -> raceRunner.race.output }
    }

    fun isRaceOpen(raceName: String): Boolean {
        val raceRunners: List<RaceRunner> = raceRunnerSeekService.findAllByRace(raceName)

        return raceRunners.all { raceRunner ->
            raceRunner.outcome != Outcome.PENDING_VERIFICATION
        }
    }

}
