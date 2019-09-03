package com.immanuelqrw.speedleague.api.service.search

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.entity.Runner
import org.springframework.stereotype.Service

@Service
class RunnerService : BaseUniqueService<Runner>() {

    fun findByName(name: String): Runner? {
        return findAll("name:$name").firstOrNull()
    }

}
