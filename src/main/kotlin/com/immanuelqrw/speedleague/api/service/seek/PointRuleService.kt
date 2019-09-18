package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.PointRule
import com.immanuelqrw.speedleague.api.repository.PointRuleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PointRuleService : BaseUniqueService<PointRule>(PointRule::class.java) {

    @Autowired
    private lateinit var pointRuleRepository: PointRuleRepository

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<PointRule> {
        return findAll().filter { pointRule ->
            pointRule.league.name == leagueName &&
            pointRule.league.season == season &&
            pointRule.league.tier.level == tierLevel
        }
    }

    fun findByLeague(leagueId: UUID): PointRule? {
        return findAll(search = "leagueId:$leagueId").firstOrNull()
    }

    fun delete(pointRule: PointRule) {
        pointRuleRepository.delete(pointRule)
    }

    fun deleteAll(pointRules: Iterable<PointRule>) {
        pointRuleRepository.deleteAll(pointRules)
    }

}
