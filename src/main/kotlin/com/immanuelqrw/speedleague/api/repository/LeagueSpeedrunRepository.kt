package com.immanuelqrw.speedleague.api.repository

import com.immanuelqrw.core.api.repository.BaseUniqueRepository
import com.immanuelqrw.speedleague.api.entity.LeagueSpeedrun
import org.springframework.stereotype.Repository

@Repository
interface LeagueSpeedrunRepository : BaseUniqueRepository<LeagueSpeedrun>
