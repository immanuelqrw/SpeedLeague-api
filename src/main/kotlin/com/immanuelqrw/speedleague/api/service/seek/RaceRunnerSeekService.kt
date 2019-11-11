package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Outcome
import com.immanuelqrw.speedleague.api.dto.update.RaceTime as RaceTimeRegister
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import com.immanuelqrw.speedleague.api.repository.RaceRunnerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

@Service
class RaceRunnerSeekService : BaseUniqueService<RaceRunner>(RaceRunner::class.java) {

    @Autowired
    private lateinit var raceRunnerRepository: RaceRunnerRepository

    fun findByRaceAndRunner(raceName: String, runnerName: String): RaceRunner? {
        return raceRunnerRepository.findByRaceNameAndRunnerNameAndRemovedOnIsNull(raceName, runnerName)
            ?: throw EntityNotFoundException()
    }

    fun findAllByRace(raceName: String): List<RaceRunner> {
        return raceRunnerRepository.findAllByRaceNameAndRemovedOnIsNull(raceName)
    }

    fun findAllByRunner(runnerName: String): List<RaceRunner> {
        return raceRunnerRepository.findAllByRunnerNameAndRemovedOnIsNull(runnerName)
    }

    fun findAllByStartedOnAndOutcome(startedOn: LocalDateTime, outcome: Outcome): List<RaceRunner> {
        return raceRunnerRepository.findAllByRaceStartedOnBeforeAndOutcomeAndRemovedOnIsNull(startedOn, outcome)
    }

    fun findAllByRaceAndOutcome(raceName: String, outcome: Outcome): List<RaceRunner> {
        return raceRunnerRepository.findAllByRaceNameAndOutcomeAndRemovedOnIsNull(raceName, outcome)
    }

    fun registerRaceTime(raceTimeRegister: RaceTimeRegister): RaceRunner {
        val raceRunner: RaceRunner = findByRaceAndRunner(raceTimeRegister.raceName, raceTimeRegister.runnerName)
            ?: throw EntityNotFoundException()

        raceRunner.time = raceTimeRegister.time
        raceRunner.outcome = raceTimeRegister.outcome

        return create(raceRunner)
    }

}
