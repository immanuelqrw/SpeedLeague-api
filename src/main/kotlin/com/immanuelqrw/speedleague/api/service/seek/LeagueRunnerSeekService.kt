package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.LeagueRunner
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class LeagueRunnerSeekService : BaseUniqueService<LeagueRunner>(LeagueRunner::class.java) {

    fun findByLeagueAndRunner(leagueName: String, season: Int, tierLevel: Int, runnerName: String): LeagueRunner? {
        return findAll().firstOrNull { leagueRunner ->
            leagueRunner.league.name == leagueName &&
            leagueRunner.league.season == season &&
            leagueRunner.league.tier.level == tierLevel
            leagueRunner.runner.name == runnerName
        }
    }

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<LeagueRunner> {
        return findAll().filter { leagueRunner ->
            leagueRunner.league.name == leagueName &&
            leagueRunner.league.season == season &&
            leagueRunner.league.tier.level == tierLevel
        }
    }

    fun findAllByRunner(runnerName: String): List<LeagueRunner> {
        return findAll().filter { leagueRunner ->
            leagueRunner.runner.name == runnerName
        }
    }

}
