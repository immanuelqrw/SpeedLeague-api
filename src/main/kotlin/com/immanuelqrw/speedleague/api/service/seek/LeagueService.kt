package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.League
import org.springframework.stereotype.Service

@Service
class LeagueService : BaseUniqueService<League>(League::class.java) {

    fun findByName(name: String): League? {
        return findAll(search = "name:$name").firstOrNull()
    }

}
