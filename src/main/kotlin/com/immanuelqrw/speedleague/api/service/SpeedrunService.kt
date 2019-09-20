package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.entity.*
import com.immanuelqrw.speedleague.api.dto.input.Speedrun as SpeedrunInput
import com.immanuelqrw.speedleague.api.dto.output.Speedrun as SpeedrunOutput
import com.immanuelqrw.speedleague.api.service.seek.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpeedrunService {

    @Autowired
    private lateinit var speedrunSeekService: SpeedrunSeekService

    @Autowired
    private lateinit var cartSeekService: CartSeekService

    @Autowired
    private lateinit var categorySeekService: CategorySeekService

    fun create(speedrunInput: SpeedrunInput): SpeedrunOutput {
        return speedrunInput.run {
            val cart: Cart = cartSeekService.find(gameName, systemName, isEmulated, region, version)
            val category: Category = categorySeekService.findByName(categoryName)

            val speedrun = Speedrun(
                cart = cart,
                category = category
            )
            val createdSpeedrun: Speedrun = speedrunSeekService.create(speedrun)

            createdSpeedrun.output
        }
    }

    fun findAll(search: String?): Iterable<SpeedrunOutput> {
        return speedrunSeekService.findAll(search = search).map { speedrun -> speedrun.output }
    }

    fun findAll(
        categoryName: String?,
        gameName: String?,
        systemName: String?,
        isEmulated: Boolean?,
        region: Region?,
        version: String?
    ): Iterable<SpeedrunOutput> {
        return speedrunSeekService
            .findAll(categoryName, gameName, systemName, isEmulated, region, version)
            .map { speedrun -> speedrun.output }
    }

}
