package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Runner
import org.springframework.stereotype.Service

@Service
class RunnerService : BaseUniqueService<Runner>(Runner::class.java) {

    fun findByName(name: String): Runner? {
        return findAll("name:$name").firstOrNull()
    }

}
