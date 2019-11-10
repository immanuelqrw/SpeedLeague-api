package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.Outcome
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface RaceRunnerRepository : BaseUniqueRepository<RaceRunner> {

    fun findByRaceNameAndRunnerName(raceName: String, runnerName: String): RaceRunner?

    fun findAllByRaceName(raceName: String): List<RaceRunner>

    fun findAllByRunnerName(runnerName: String): List<RaceRunner>

    fun findAllByRaceStartedOnBeforeAndOutcome(startedOn: LocalDateTime, outcome: Outcome): List<RaceRunner>

    fun findAllByRaceNameAndOutcome(raceName: String, outcome: Outcome): List<RaceRunner>

}
