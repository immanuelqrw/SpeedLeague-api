package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Region
import com.immanuelqrw.speedleague.api.entity.Speedrun
import com.immanuelqrw.speedleague.api.repository.SpeedrunRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class SpeedrunSeekService : BaseUniqueService<Speedrun>(Speedrun::class.java) {

    @Autowired
    private lateinit var speedrunRepository: SpeedrunRepository

    fun findAllByGame(gameName: String): List<Speedrun> {
        return speedrunRepository.findAllByCartGameName(gameName)
    }

    fun findAllBySystem(systemName: String): List<Speedrun> {
        return speedrunRepository.findAllByCartSystemName(systemName)
    }

    fun findAllByRegion(region: Region): List<Speedrun> {
        return speedrunRepository.findAllByCartRegion(region)
    }

    fun findAllByCategoryAndGame(category: String, gameName: String): List<Speedrun> {
        return speedrunRepository.findAllByCategoryAndCartGameName(category, gameName)
    }

    fun findAllByGameAndSystem(gameName: String, systemName: String): List<Speedrun> {
        return speedrunRepository.findAllByCartGameNameAndCartSystemName(gameName, systemName)
    }

    fun findAllByGameAndRegion(gameName: String, region: Region): List<Speedrun> {
        return speedrunRepository.findAllByCartGameNameAndCartRegion(gameName, region)
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region): List<Speedrun> {
        return speedrunRepository.findAllByCartSystemNameAndCartRegion(systemName, region)
    }

    fun findAllByCategoryAndGameAndSystem(category: String, gameName: String, systemName: String): List<Speedrun> {
        return speedrunRepository.findAllByCategoryAndCartGameNameAndCartSystemName(category, gameName, systemName)
    }

    fun findAllByCategoryAndGameAndRegion(category: String, gameName: String, region: Region): List<Speedrun> {
        return speedrunRepository.findAllByCategoryAndCartGameNameAndCartRegion(category, gameName, region)
    }

    fun findAllByGameAndSystemAndRegion(gameName: String, systemName: String, region: Region): List<Speedrun> {
        return speedrunRepository.findAllByCartGameNameAndCartSystemNameAndCartRegion(gameName, systemName, region)
    }

    fun find(category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): Speedrun {
        return speedrunRepository
            .findFirstByCategoryAndCartGameNameAndCartSystemNameAndCartSystemIsEmulatedAndCartRegionAndCartVersion(category, gameName, systemName, isEmulated, region, version)
            ?: throw EntityNotFoundException()
    }

    fun findAll(category: String?, gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<Speedrun> {
        return findAllActive()
            .asSequence()
            .filter { speedrun ->  category?.let { speedrun.category == category } ?: true }
            .filter { speedrun ->  gameName?.let { speedrun.cart.game.name == gameName } ?: true }
            .filter { speedrun ->  systemName?.let { speedrun.cart.system.name == systemName } ?: true }
            .filter { speedrun ->  isEmulated?.let { speedrun.cart.system.isEmulated == isEmulated } ?: true }
            .filter { speedrun ->  region?.let { speedrun.cart.region == region } ?: true }
            .filter { speedrun ->  version?.let { speedrun.cart.version == version } ?: true }
            .toList()
    }

}
