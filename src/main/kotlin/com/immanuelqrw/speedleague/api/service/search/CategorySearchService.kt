package com.immanuelqrw.speedleague.api.service.search

import com.immanuelqrw.core.api.service.SearchService
import com.immanuelqrw.speedleague.api.entity.Category
import org.springframework.stereotype.Service

@Service
class CategorySearchService : SearchService<Category>()
