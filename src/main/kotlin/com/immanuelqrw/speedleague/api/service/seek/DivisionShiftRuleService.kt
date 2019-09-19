package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.DivisionShiftRule
import com.immanuelqrw.speedleague.api.entity.Shift
import com.immanuelqrw.speedleague.api.repository.DivisionShiftRuleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class DivisionShiftRuleService : BaseUniqueService<DivisionShiftRule>(DivisionShiftRule::class.java) {

    @Autowired
    private lateinit var divisionShiftRuleRepository: DivisionShiftRuleRepository

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRule> {
        return findAll().filter { divisionShiftRule ->
            divisionShiftRule.league.name == leagueName &&
            divisionShiftRule.league.season == season &&
            divisionShiftRule.league.tier.level == tierLevel
        }
    }

    fun findAllPromotionByLeague(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRule> {
        return findAll().filter { divisionShiftRule ->
            divisionShiftRule.league.name == leagueName &&
            divisionShiftRule.league.season == season &&
            divisionShiftRule.league.tier.level == tierLevel &&
            divisionShiftRule.shift == Shift.PROMOTION
        }
    }

    fun findAllRelegationByLeague(leagueName: String, season: Int, tierLevel: Int): List<DivisionShiftRule> {
        return findAll().filter { divisionShiftRule ->
            divisionShiftRule.league.name == leagueName &&
            divisionShiftRule.league.season == season &&
            divisionShiftRule.league.tier.level == tierLevel &&
            divisionShiftRule.shift == Shift.RELEGATION
        }
    }

    fun findByLeague(leagueId: UUID): DivisionShiftRule? {
        return findAll(search = "leagueId:$leagueId").firstOrNull()
    }

    fun delete(divisionShiftRule: DivisionShiftRule) {
        divisionShiftRuleRepository.delete(divisionShiftRule)
    }

    fun deleteAll(divisionShiftRules: Iterable<DivisionShiftRule>) {
        divisionShiftRuleRepository.deleteAll(divisionShiftRules)
    }

}
