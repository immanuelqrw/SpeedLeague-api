package com.immanuelqrw.speedleague.api.service.search

import com.immanuelqrw.core.api.service.SearchService
import com.immanuelqrw.speedleague.api.entity.Game
import org.springframework.stereotype.Service

@Service
class GameSearchService : SearchService<Game>()
