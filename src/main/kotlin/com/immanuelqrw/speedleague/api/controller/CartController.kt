package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.Cart as CartInput
import com.immanuelqrw.speedleague.api.dto.output.Cart as CartOutput
import com.immanuelqrw.speedleague.api.entity.Cart
import com.immanuelqrw.speedleague.api.entity.Game
import com.immanuelqrw.speedleague.api.entity.Region
import com.immanuelqrw.speedleague.api.entity.System
import com.immanuelqrw.speedleague.api.service.seek.CartService
import com.immanuelqrw.speedleague.api.service.seek.GameService
import com.immanuelqrw.speedleague.api.service.seek.SystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController {

    @Autowired
    private lateinit var cartService: CartService

    @Autowired
    private lateinit var gameService: GameService

    @Autowired
    private lateinit var systemService: SystemService

    private fun convertToOutput(cart: Cart): CartOutput {
        return cart.run {
            CartOutput(
                gameName = cart.game.name,
                systemName = cart.system.name,
                isEmulated = cart.system.isEmulated,
                region = cart.region,
                version = cart.version
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody cartInput: CartInput): CartOutput {
        return cartInput.run {
            val game: Game = gameService.findByName(gameName)
            val system: System = systemService.findByName(systemName)

            val cart = Cart(
                game = game,
                system = system,
                region = region,
                version = version
            )
            val createdCart: Cart = cartService.create(cart)

            convertToOutput(createdCart)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<CartOutput> {
        return cartService.findAll(search = search).map { cart -> convertToOutput(cart) }
    }

    @GetMapping(path = ["/deepSearch"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("game")
        gameName: String?,
        @RequestParam("system")
        systemName: String?,
        @RequestParam("isEmulated")
        isEmulated: Boolean?,
        @RequestParam("region")
        region: Region?,
        @RequestParam("version")
        version: String?
    ): Iterable<CartOutput> {
        return cartService
            .findAll(gameName, systemName, isEmulated, region, version)
            .map { cart -> convertToOutput(cart) }
    }

}
