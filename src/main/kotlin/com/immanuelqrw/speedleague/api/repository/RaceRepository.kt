package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.Race
import org.springframework.stereotype.Repository

@Repository
interface RaceRepository : BaseUniqueRepository<Race> {

    fun findByNameAndRemovedOnIsNull(name: String): Race?

}
