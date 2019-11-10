package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.LeagueRunner
import com.immanuelqrw.speedleague.api.repository.LeagueRunnerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class LeagueRunnerSeekService : BaseUniqueService<LeagueRunner>(LeagueRunner::class.java) {

    @Autowired
    private lateinit var leagueRunnerRepository: LeagueRunnerRepository

    fun findByLeagueAndRunner(leagueName: String, season: Int, tierLevel: Int, runnerName: String): LeagueRunner? {
        return leagueRunnerRepository.findByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRunnerNameAndRemovedOnIsNull(leagueName, season, tierLevel, runnerName)
    }

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<LeagueRunner> {
        return leagueRunnerRepository.findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName, season, tierLevel)
    }

    fun findAllByRunner(runnerName: String): List<LeagueRunner> {
        return leagueRunnerRepository.findAllByRunnerNameAndRemovedOnIsNull(runnerName)
    }

}
