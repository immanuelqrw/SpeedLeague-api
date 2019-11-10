package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.PlayoffRule
import com.immanuelqrw.speedleague.api.repository.PlayoffRuleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class PlayoffRuleSeekService : BaseUniqueService<PlayoffRule>(PlayoffRule::class.java) {

    @Autowired
    private lateinit var playoffRuleRepository: PlayoffRuleRepository

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<PlayoffRule> {
        return playoffRuleRepository
            .findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevelAndRemovedOnIsNull(leagueName, season, tierLevel)
    }

    fun findByLeague(leagueName: String): PlayoffRule? {
        return playoffRuleRepository.findByLeagueNameAndRemovedOnIsNull(leagueName)
    }

    fun delete(playoffRule: PlayoffRule) {
        playoffRuleRepository.delete(playoffRule)
    }

    fun deleteAll(playoffRules: Iterable<PlayoffRule>) {
        playoffRuleRepository.deleteAll(playoffRules)
    }

}
