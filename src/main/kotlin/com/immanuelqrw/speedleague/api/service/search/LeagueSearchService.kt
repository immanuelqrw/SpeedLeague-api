package com.immanuelqrw.speedleague.api.service.search

import com.immanuelqrw.core.api.service.SearchService
import com.immanuelqrw.speedleague.api.entity.League
import org.springframework.stereotype.Service

@Service
class LeagueSearchService : SearchService<League>()
