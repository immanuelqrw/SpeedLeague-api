package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.DivisionShiftRule
import com.immanuelqrw.speedleague.api.entity.Shift
import org.springframework.stereotype.Repository

@Repository
interface DivisionShiftRuleRepository : BaseUniqueRepository<DivisionShiftRule> {

    fun findByLeagueNameAndRemovedOnIsNull(leagueName: String): DivisionShiftRule?

    fun findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRule>

    fun findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndShiftAndRemovedOnIsNull(leagueName: String, season: Int, tierLevel: Int, shift: Shift): List<DivisionShiftRule>

}
