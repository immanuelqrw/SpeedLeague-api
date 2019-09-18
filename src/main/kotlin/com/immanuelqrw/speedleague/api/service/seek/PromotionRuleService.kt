package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.PromotionRule
import com.immanuelqrw.speedleague.api.repository.PromotionRuleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PromotionRuleService : BaseUniqueService<PromotionRule>(PromotionRule::class.java) {

    @Autowired
    private lateinit var promotionRuleRepository: PromotionRuleRepository

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<PromotionRule> {
        return findAll().filter { promotionRule ->
            promotionRule.league.name == leagueName &&
            promotionRule.league.season == season &&
            promotionRule.league.tier.level == tierLevel
        }
    }

    fun findByLeague(leagueId: UUID): PromotionRule? {
        return findAll(search = "leagueId:$leagueId").firstOrNull()
    }

    fun delete(promotionRule: PromotionRule) {
        promotionRuleRepository.delete(promotionRule)
    }

    fun deleteAll(promotionRules: Iterable<PromotionRule>) {
        promotionRuleRepository.deleteAll(promotionRules)
    }

}
