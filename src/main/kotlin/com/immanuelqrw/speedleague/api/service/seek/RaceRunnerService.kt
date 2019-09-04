package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RaceRunnerService : BaseUniqueService<RaceRunner>(RaceRunner::class.java) {

    @Autowired
    private lateinit var raceService: RaceService

    @Autowired
    private lateinit var runnerService: RunnerService

    fun findByRaceAndRunner(raceName: String, runnerName: String): RaceRunner? {
        return findAll().firstOrNull { raceRunner ->
            raceRunner.race.name == raceName && raceRunner.runner.name == runnerName
        }
    }

    fun findByRace(raceName: String): List<RaceRunner> {
        return findAll().filter { raceRunner ->
            raceRunner.race.name == raceName
        }
    }

    fun findByRunner(runnerName: String): List<RaceRunner> {
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

}
