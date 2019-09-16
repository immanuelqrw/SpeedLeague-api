package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.Tier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class LeagueService : BaseUniqueService<League>(League::class.java) {

    @Autowired
    private lateinit var tierService: TierService

    fun find(name: String, season: Int, tierName: String, tierLevel: Int): League {
        val tier: Tier? = tierService.findAll(search = "name:$tierName;level:$tierLevel").firstOrNull()

        return findAll(search = "name:$name;season=$season;tierId:${tier?.id}").firstOrNull() ?: throw EntityNotFoundException()
    }

}
