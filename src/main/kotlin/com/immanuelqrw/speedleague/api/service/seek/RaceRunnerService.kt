package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.dto.update.RaceTime as RaceTimeRegister
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import com.immanuelqrw.speedleague.api.repository.RaceRunnerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID
import javax.persistence.EntityNotFoundException

@Service
class RaceRunnerService : BaseUniqueService<RaceRunner>(RaceRunner::class.java) {

    @Autowired
    private lateinit var raceRunnerRepository: RaceRunnerRepository

    fun findByRaceAndRunner(raceName: String, runnerName: String): RaceRunner? {
        return findAll().firstOrNull { raceRunner ->
            raceRunner.race.name == raceName && raceRunner.runner.name == runnerName
        }
    }

    fun findAllByRace(raceName: String): List<RaceRunner> {
        return findAll().filter { raceRunner ->
            raceRunner.race.name == raceName
        }
    }

    fun findAllByRunner(runnerName: String): List<RaceRunner> {
        return findAll().filter { raceRunner ->
            raceRunner.runner.name == runnerName
        }
    }

    fun findByRace(raceId: UUID): RaceRunner? {
        return findAll(search = "raceId:$raceId").firstOrNull()
    }

    fun findByRunner(runnerId: UUID): RaceRunner? {
        return findAll(search = "runnerId:$runnerId").firstOrNull()
    }

    fun registerRaceTime(raceTimeRegister: RaceTimeRegister): RaceRunner {
        val raceRunner: RaceRunner = findByRaceAndRunner(raceTimeRegister.raceName, raceTimeRegister.runnerName)
            ?: throw EntityNotFoundException()

        raceRunner.time = raceTimeRegister.time
        raceRunner.outcome = raceTimeRegister.outcome

        return raceRunnerRepository.save(raceRunner)
    }

}
