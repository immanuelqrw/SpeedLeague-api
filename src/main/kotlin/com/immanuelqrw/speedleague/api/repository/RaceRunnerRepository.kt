package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.Outcome
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface RaceRunnerRepository : BaseUniqueRepository<RaceRunner> {

    fun findByRaceNameAndRunnerNameAndRemovedOnIsNull(raceName: String, runnerName: String): RaceRunner?

    fun findAllByRaceNameAndRemovedOnIsNull(raceName: String): List<RaceRunner>

    fun findAllByRunnerNameAndRemovedOnIsNull(runnerName: String): List<RaceRunner>

    fun findAllByRaceStartedOnBeforeAndOutcomeAndRemovedOnIsNull(startedOn: LocalDateTime, outcome: Outcome): List<RaceRunner>

    fun findAllByRaceNameAndOutcomeAndRemovedOnIsNull(raceName: String, outcome: Outcome): List<RaceRunner>

}
