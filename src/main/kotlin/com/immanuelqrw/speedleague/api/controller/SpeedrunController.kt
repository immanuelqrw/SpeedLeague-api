package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.entity.*
import com.immanuelqrw.speedleague.api.dto.input.Speedrun as SpeedrunInput
import com.immanuelqrw.speedleague.api.dto.output.Speedrun as SpeedrunOutput
import com.immanuelqrw.speedleague.api.service.seek.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/speedrun")
class SpeedrunController {

    @Autowired
    private lateinit var speedrunService: SpeedrunService

    @Autowired
    private lateinit var cartService: CartService

    @Autowired
    private lateinit var categoryService: CategoryService

    private fun convertToOutput(speedrun: Speedrun): SpeedrunOutput {
        return speedrun.cart.run {
            SpeedrunOutput(
                categoryName = speedrun.category.name,
                gameName = speedrun.cart.game.name,
                systemName = speedrun.cart.distinctSystem.system.name,
                isEmulated = speedrun.cart.distinctSystem.system.isEmulated,
                region = speedrun.cart.distinctSystem.region,
                versionName = speedrun.cart.distinctSystem.version.name
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody speedrunInput: SpeedrunInput): SpeedrunOutput {
        return speedrunInput.run {
            val cart: Cart = cartService.find(gameName, systemName, isEmulated, region, versionName)
            val category: Category = categoryService.findByName(categoryName)

            val speedrun = Speedrun(
                cart = cart,
                category = category
            )
            val createdSpeedrun: Speedrun = speedrunService.create(speedrun)

            convertToOutput(createdSpeedrun)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<SpeedrunOutput> {
        return speedrunService.findAll(search = search).map { speedrun -> convertToOutput(speedrun) }
    }

    @GetMapping(path = ["/deepSearch"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("categoryName")
        categoryName: String?,
        @RequestParam("gameName")
        gameName: String?,
        @RequestParam("systemName")
        systemName: String?,
        @RequestParam("isEmulated")
        isEmulated: Boolean?,
        @RequestParam("region")
        region: Region?,
        @RequestParam("versionName")
        versionName: String?
    ): Iterable<SpeedrunOutput> {
        return speedrunService
            .findAll(categoryName, gameName, systemName, isEmulated, region, versionName)
            .map { speedrun -> convertToOutput(speedrun) }
    }

}
