package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.PlayoffRule
import org.springframework.stereotype.Repository

@Repository
interface PlayoffRuleRepository : BaseUniqueRepository<PlayoffRule> {

    fun findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevel(leagueName: String, season: Int, tierLevel: Int): List<PlayoffRule>

    fun findByLeagueName(leagueName: String): PlayoffRule?

}
