package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Runner
import com.immanuelqrw.speedleague.api.repository.RunnerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class RunnerSeekService : BaseUniqueService<Runner>(Runner::class.java) {

    @Autowired
    private lateinit var runnerRepository: RunnerRepository

    fun findByName(name: String): Runner {
        return runnerRepository.findByName(name) ?: throw EntityNotFoundException()
    }

}
