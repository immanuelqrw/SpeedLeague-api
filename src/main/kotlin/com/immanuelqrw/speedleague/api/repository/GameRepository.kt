package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.Game
import org.springframework.stereotype.Repository

@Repository
interface GameRepository : BaseUniqueRepository<Game> {

    fun findByNameAndRemovedOnIsNull(name: String): Game?

    fun findByShorthandAndRemovedOnIsNull(shorthand: String): Game?

}
