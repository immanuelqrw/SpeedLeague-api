package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.LeagueRunner
import org.springframework.stereotype.Repository

@Repository
interface LeagueRunnerRepository : BaseUniqueRepository<LeagueRunner> {

    fun findAllByRunnerName(runnerName: String): List<LeagueRunner>

    fun findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevel(leagueName: String, season: Int, tierLevel: Int): List<LeagueRunner>

    fun findByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRunnerName(leagueName: String, season: Int, tierLevel: Int, runnerName: String): LeagueRunner?

}
