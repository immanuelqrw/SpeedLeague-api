package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Region
import com.immanuelqrw.speedleague.api.entity.Speedrun
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class SpeedrunService : BaseUniqueService<Speedrun>(Speedrun::class.java) {

    fun findAllByGame(gameName: String): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.game.name == gameName
        }
    }

    fun findAllBySystem(systemName: String): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.distinctSystem.system.name == systemName
        }
    }

    fun findAllByRegion(region: Region): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.distinctSystem.region == region
        }
    }

    fun findAllByCategoryAndGame(categoryName: String, gameName: String): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.category.name == categoryName && speedrun.cart.game.name == gameName
        }
    }

    fun findAllByGameAndSystem(gameName: String, systemName: String): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.game.name == gameName && speedrun.cart.distinctSystem.system.name == systemName
        }
    }

    fun findAllByGameAndRegion(gameName: String, region: Region?): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.game.name == gameName && speedrun.cart.distinctSystem.region == region
        }
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region?): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.distinctSystem.system.name == systemName && speedrun.cart.distinctSystem.region == region
        }
    }

    fun findByCategoryAndGameAndSystem(categoryName: String, gameName: String, systemName: String): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.category.name == categoryName &&
            speedrun.cart.game.name == gameName &&
            speedrun.cart.distinctSystem.system.name == systemName
        }
    }

    fun findByCategoryAndGameAndRegion(categoryName: String, gameName: String, region: Region?): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.category.name == categoryName &&
            speedrun.cart.game.name == gameName &&
            speedrun.cart.distinctSystem.region == region
        }
    }

    fun findByGameAndSystemAndRegion(gameName: String, systemName: String, region: Region?): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.game.name == gameName &&
            speedrun.cart.distinctSystem.system.name == systemName &&
            speedrun.cart.distinctSystem.region == region
        }
    }

    fun findByCategoryAndGameAndSystemAndRegion(categoryName: String, gameName: String, systemName: String, region: Region?): Speedrun {
        return findAll().firstOrNull { speedrun ->
            speedrun.category.name == categoryName &&
            speedrun.cart.game.name == gameName &&
            speedrun.cart.distinctSystem.system.name == systemName &&
            speedrun.cart.distinctSystem.region == region
        } ?: throw EntityNotFoundException()
    }

}
