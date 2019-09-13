package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import org.springframework.stereotype.Repository

@Repository
interface RaceRunnerRepository : BaseUniqueRepository<RaceRunner>
