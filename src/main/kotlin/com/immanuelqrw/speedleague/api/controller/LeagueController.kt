package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.core.api.controller.BaseUniqueController
import com.immanuelqrw.speedleague.api.entity.League
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Example controller class
 */
@RestController
@RequestMapping("/league")
class LeagueController : BaseUniqueController<League>()
