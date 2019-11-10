package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.System
import com.immanuelqrw.speedleague.api.repository.SystemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class SystemSeekService : BaseUniqueService<System>(System::class.java) {

    @Autowired
    private lateinit var systemRepository: SystemRepository

    fun findByName(name: String): System {
        return systemRepository.findByName(name) ?: throw EntityNotFoundException()
    }

}
