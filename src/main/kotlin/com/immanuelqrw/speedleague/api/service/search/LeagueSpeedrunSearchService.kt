package com.immanuelqrw.speedleague.api.service.search

import com.immanuelqrw.core.api.service.SearchService
import com.immanuelqrw.speedleague.api.entity.LeagueSpeedrun
import org.springframework.stereotype.Service

@Service
class LeagueSpeedrunSearchService : SearchService<LeagueSpeedrun>()
