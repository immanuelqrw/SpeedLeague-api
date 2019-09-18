package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.PlayoffRule
import com.immanuelqrw.speedleague.api.repository.PlayoffRuleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlayoffRuleService : BaseUniqueService<PlayoffRule>(PlayoffRule::class.java) {

    @Autowired
    private lateinit var playoffRuleRepository: PlayoffRuleRepository

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<PlayoffRule> {
        return findAll().filter { playoffRule ->
            playoffRule.league.name == leagueName &&
            playoffRule.league.season == season &&
            playoffRule.league.tier.level == tierLevel
        }
    }

    fun findByLeague(leagueId: UUID): PlayoffRule? {
        return findAll(search = "leagueId:$leagueId").firstOrNull()
    }

    fun delete(playoffRule: PlayoffRule) {
        playoffRuleRepository.delete(playoffRule)
    }

    fun deleteAll(playoffRules: Iterable<PlayoffRule>) {
        playoffRuleRepository.deleteAll(playoffRules)
    }

}
