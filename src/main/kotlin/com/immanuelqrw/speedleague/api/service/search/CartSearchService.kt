package com.immanuelqrw.speedleague.api.service.search

import com.immanuelqrw.core.api.service.SearchService
import com.immanuelqrw.speedleague.api.entity.Cart
import com.immanuelqrw.speedleague.api.entity.Speedrun
import org.springframework.stereotype.Service

@Service
class CartSearchService : SearchService<Cart>()
