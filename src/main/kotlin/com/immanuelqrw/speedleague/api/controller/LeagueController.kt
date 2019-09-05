package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.League as LeagueDTO
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/league")
class LeagueController {

    @Autowired
    private lateinit var leagueService: LeagueService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody entity: LeagueDTO): League {
        return entity.run {
            val league = League(
                name = name,
                startedOn = startedOn
            )
            leagueService.create(league)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(): Iterable<League> {
        return leagueService.findAll()
    }

}
