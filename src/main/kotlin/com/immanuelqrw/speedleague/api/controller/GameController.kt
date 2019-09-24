package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.Game as GameInput
import com.immanuelqrw.speedleague.api.dto.output.Game as GameOutput
import com.immanuelqrw.speedleague.api.service.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/game")
class GameController {

    @Autowired
    private lateinit var gameService: GameService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody gameInput: GameInput): GameOutput {
        return gameService.create(gameInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<GameOutput> {
        return gameService.findAll(search = search)
    }

    @GetMapping(path = ["/shorthand/{shorthand}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findByShorthand(
        @PathVariable("shorthand")
        shorthand: String
    ): GameOutput {
        return gameService.findByShorthand(shorthand)
    }

}
