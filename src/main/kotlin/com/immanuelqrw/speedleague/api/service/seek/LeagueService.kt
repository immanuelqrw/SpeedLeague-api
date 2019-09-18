package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.Tier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class LeagueService : BaseUniqueService<League>(League::class.java) {

    fun find(name: String, season: Int, tierLevel: Int): League {
        return findAll(search = "name:$name;season:$season;tier.level:$tierLevel")
            .firstOrNull()
            ?: throw EntityNotFoundException()
    }

    fun findBottomLeague(name: String, season: Int): League {
        return findAll(search = "name:$name;season:$season;")
            .maxBy { league -> league.tier.level }
            ?: throw EntityNotFoundException()
    }

}
