package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.Game
import org.springframework.stereotype.Repository

@Repository
interface GameRepository : BaseUniqueRepository<Game> {

    fun findByName(name: String): Game?

    fun findByShorthand(shorthand: String): Game?

}
