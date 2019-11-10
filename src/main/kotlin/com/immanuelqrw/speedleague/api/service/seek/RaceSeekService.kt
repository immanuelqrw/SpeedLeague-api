package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.repository.RaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class RaceSeekService : BaseUniqueService<Race>(Race::class.java) {

    @Autowired
    private lateinit var raceRepository: RaceRepository

    fun findByName(name: String): Race {
        return raceRepository.findByNameAndRemovedOnIsNull(name) ?: throw EntityNotFoundException()
    }

}
