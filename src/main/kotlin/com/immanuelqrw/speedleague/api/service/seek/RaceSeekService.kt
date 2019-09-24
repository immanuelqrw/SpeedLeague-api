package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Race
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class RaceSeekService : BaseUniqueService<Race>(Race::class.java) {

    fun findByName(name: String): Race {
        return findAllActive(search = "name:$name").firstOrNull() ?: throw EntityNotFoundException()
    }

}
