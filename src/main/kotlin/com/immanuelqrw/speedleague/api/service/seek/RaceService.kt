package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Race
import org.springframework.stereotype.Service

@Service
class RaceService : BaseUniqueService<Race>(Race::class.java) {

    fun findByName(name: String): Race? {
        return findAll(search = "name:$name").firstOrNull()
    }

}
