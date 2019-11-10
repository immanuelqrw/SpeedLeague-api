package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.PointRule
import org.springframework.stereotype.Repository

@Repository
interface PointRuleRepository : BaseUniqueRepository<PointRule> {

    fun findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevel(leagueName: String, season: Int, tierLevel: Int): List<PointRule>

    fun findByLeagueName(leagueName: String): PointRule?

}
