package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.Runner
import org.springframework.stereotype.Repository

@Repository
interface RunnerRepository : BaseUniqueRepository<Runner> {

    fun findByName(name: String): Runner?

}
