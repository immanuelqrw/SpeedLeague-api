package com.immanuelqrw.speedleague.api.service.search

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Race
import org.springframework.stereotype.Service

@Service
class RaceService : BaseUniqueService<Race>() {

    fun findByName(name: String): Race? {
        return findAll("name:$name").firstOrNull()
    }

}
