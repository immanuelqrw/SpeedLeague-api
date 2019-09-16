package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.LeagueRunner
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class LeagueRunnerService : BaseUniqueService<LeagueRunner>(LeagueRunner::class.java) {

    fun findByLeagueAndRunner(leagueName: String, runnerName: String): LeagueRunner? {
        return findAll().firstOrNull { leagueRunner ->
            leagueRunner.league.name == leagueName && leagueRunner.runner.name == runnerName
        }
    }

    fun findAllByLeague(leagueName: String): List<LeagueRunner> {
        return findAll().filter { leagueRunner ->
            leagueRunner.league.name == leagueName
        }
    }

    fun findAllByRunner(runnerName: String): List<LeagueRunner> {
        return findAll().filter { leagueRunner ->
            leagueRunner.runner.name == runnerName
        }
    }

    fun findByLeague(leagueId: UUID): LeagueRunner? {
        return findAll(search = "leagueId:$leagueId").firstOrNull()
    }

    fun findByRunner(runnerId: UUID): LeagueRunner? {
        return findAll(search = "runnerId:$runnerId").firstOrNull()
    }

}
