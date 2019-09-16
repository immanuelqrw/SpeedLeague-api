package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.System
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class SystemService : BaseUniqueService<System>(System::class.java) {

    fun findByName(name: String): System {
        return findAll(search = "name:$name").firstOrNull() ?: throw EntityNotFoundException()
    }

}
