package com.immanuelqrw.speedleague.api.service.search

import com.immanuelqrw.core.api.service.SearchService
import com.immanuelqrw.speedleague.api.entity.Version
import org.springframework.stereotype.Service

@Service
class VersionSearchService : SearchService<Version>()
