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

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody speedrunInput: SpeedrunInput): SpeedrunOutput {
        return speedrunInput.run {
            val cart: Cart = cartService.find(gameName, systemName, isEmulated, region, version)
            val category: Category = categoryService.findByName(categoryName)

            val speedrun = Speedrun(
                cart = cart,
                category = category
            )
            val createdSpeedrun: Speedrun = speedrunService.create(speedrun)

            createdSpeedrun.output
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<SpeedrunOutput> {
        return speedrunService.findAll(search = search).map { speedrun -> speedrun.output }
    }

    @GetMapping(path = ["/deepSearch"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("category")
        categoryName: String?,
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
    ): Iterable<SpeedrunOutput> {
        return speedrunService
            .findAll(categoryName, gameName, systemName, isEmulated, region, version)
            .map { speedrun -> speedrun.output }
    }

}
