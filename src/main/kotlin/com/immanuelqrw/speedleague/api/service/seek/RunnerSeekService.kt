package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Runner
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class RunnerSeekService : BaseUniqueService<Runner>(Runner::class.java) {

    fun findByName(name: String): Runner {
        return findAllActive(search = "name:$name").firstOrNull() ?: throw EntityNotFoundException()
    }

}
