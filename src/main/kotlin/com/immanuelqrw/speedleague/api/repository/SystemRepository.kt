package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.System
import org.springframework.stereotype.Repository

@Repository
interface SystemRepository : BaseUniqueRepository<System> {

    fun findByName(name: String): System?

}
