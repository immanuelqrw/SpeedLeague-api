package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Game
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class GameService : BaseUniqueService<Game>(Game::class.java) {

    fun findByName(name: String): Game {
        return findAll(search = "name:$name").firstOrNull() ?: throw EntityNotFoundException()
    }

}
