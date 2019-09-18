package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.RelegationRule
import com.immanuelqrw.speedleague.api.repository.RelegationRuleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RelegationRuleService : BaseUniqueService<RelegationRule>(RelegationRule::class.java) {

    @Autowired
    private lateinit var relegationRuleRepository: RelegationRuleRepository

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<RelegationRule> {
        return findAll().filter { relegationRule ->
            relegationRule.league.name == leagueName &&
            relegationRule.league.season == season &&
            relegationRule.league.tier.level == tierLevel
        }
    }

    fun findByLeague(leagueId: UUID): RelegationRule? {
        return findAll(search = "leagueId:$leagueId").firstOrNull()
    }

    fun delete(relegationRule: RelegationRule) {
        relegationRuleRepository.delete(relegationRule)
    }

    fun deleteAll(relegationRules: Iterable<RelegationRule>) {
        relegationRuleRepository.deleteAll(relegationRules)
    }

}
