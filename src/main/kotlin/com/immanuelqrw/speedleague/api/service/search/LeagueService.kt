package com.immanuelqrw.speedleague.api.service.search

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.League
import org.springframework.stereotype.Service

@Service
class LeagueService : BaseUniqueService<League>() {

    fun findByName(name: String): League? {
        return findAll("name:$name").firstOrNull()
    }

}
