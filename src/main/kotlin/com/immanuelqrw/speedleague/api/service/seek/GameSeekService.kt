package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Game
import com.immanuelqrw.speedleague.api.repository.GameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class GameSeekService : BaseUniqueService<Game>(Game::class.java) {

    @Autowired
    private lateinit var gameRepository: GameRepository

    fun findByName(name: String): Game {
        return findAllActive(search = "name:$name").firstOrNull() ?: throw EntityNotFoundException()
    }

    fun findByShorthand(shorthand: String): Game {
        return gameRepository.findByShorthand(shorthand)
    }

}
