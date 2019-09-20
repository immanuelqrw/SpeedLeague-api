package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Game
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class GameSeekService : BaseUniqueService<Game>(Game::class.java) {

    fun findByName(name: String): Game {
        return findAll(search = "name:$name").firstOrNull() ?: throw EntityNotFoundException()
    }

    fun findByShorthand(shorthand: String): Game {
        return findAll(search = "shorthand:$shorthand").firstOrNull() ?: throw EntityNotFoundException()
    }

}
