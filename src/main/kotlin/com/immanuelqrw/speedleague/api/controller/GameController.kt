package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.Game as GameInput
import com.immanuelqrw.speedleague.api.dto.output.Game as GameOutput
import com.immanuelqrw.speedleague.api.entity.Game
import com.immanuelqrw.speedleague.api.service.seek.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/game")
class GameController {

    @Autowired
    private lateinit var gameService: GameService

    private fun convertToOutput(game: Game): GameOutput {
        return game.run {
            GameOutput(
                name = name,
                shorthand = shorthand
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody gameInput: GameInput): GameOutput {
        return gameInput.run {
            val game = Game(
                name = name,
                shorthand = shorthand
            )
            val createdGame: Game = gameService.create(game)

            convertToOutput(createdGame)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<GameOutput> {
        return gameService.findAll(search = search).map { game -> convertToOutput(game) }
    }

}
