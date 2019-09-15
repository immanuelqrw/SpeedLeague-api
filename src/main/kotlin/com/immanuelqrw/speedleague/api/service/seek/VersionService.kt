package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Version
import org.springframework.stereotype.Service

@Service
class VersionService : BaseUniqueService<Version>(Version::class.java) {

    fun findByName(name: String): Version? {
        return findAll(search = "name:$name").firstOrNull()
    }

}
