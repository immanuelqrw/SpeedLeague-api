package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.DivisionShiftRule
import com.immanuelqrw.speedleague.api.entity.Shift
import com.immanuelqrw.speedleague.api.repository.DivisionShiftRuleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class DivisionShiftRuleSeekService : BaseUniqueService<DivisionShiftRule>(DivisionShiftRule::class.java) {

    @Autowired
    private lateinit var divisionShiftRuleRepository: DivisionShiftRuleRepository

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRule> {
        return divisionShiftRuleRepository.findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName, season, tierLevel)
    }

    fun findAllPromotionByLeague(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRule> {
        return divisionShiftRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndShiftAndRemovedOnIsNull(leagueName, season, tierLevel, Shift.PROMOTION)
    }

    fun findAllRelegationByLeague(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRule> {
        return divisionShiftRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndShiftAndRemovedOnIsNull(leagueName, season, tierLevel, Shift.RELEGATION)
    }

    fun findByLeague(leagueName: String): DivisionShiftRule? {
        return divisionShiftRuleRepository.findByLeagueNameAndRemovedOnIsNull(leagueName)
    }

    fun delete(divisionShiftRule: DivisionShiftRule) {
        divisionShiftRuleRepository.delete(divisionShiftRule)
    }

    fun deleteAll(divisionShiftRules: Iterable<DivisionShiftRule>) {
        divisionShiftRuleRepository.deleteAll(divisionShiftRules)
    }

}
