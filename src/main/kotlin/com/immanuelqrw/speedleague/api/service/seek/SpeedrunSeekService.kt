package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.Region
import com.immanuelqrw.speedleague.api.entity.Speedrun
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class SpeedrunSeekService : BaseUniqueService<Speedrun>(Speedrun::class.java) {

    fun findAllByGame(gameName: String): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.game.name == gameName
        }
    }

    fun findAllBySystem(systemName: String): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.system.name == systemName
        }
    }

    fun findAllByRegion(region: Region): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.region == region
        }
    }

    fun findAllByCategoryAndGame(categoryName: String, gameName: String): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.category.name == categoryName && speedrun.cart.game.name == gameName
        }
    }

    fun findAllByGameAndSystem(gameName: String, systemName: String): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.game.name == gameName && speedrun.cart.system.name == systemName
        }
    }

    fun findAllByGameAndRegion(gameName: String, region: Region): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.game.name == gameName && speedrun.cart.region == region
        }
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.system.name == systemName && speedrun.cart.region == region
        }
    }

    fun findByCategoryAndGameAndSystem(categoryName: String, gameName: String, systemName: String): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.category.name == categoryName &&
            speedrun.cart.game.name == gameName &&
            speedrun.cart.system.name == systemName
        }
    }

    fun findByCategoryAndGameAndRegion(categoryName: String, gameName: String, region: Region): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.category.name == categoryName &&
            speedrun.cart.game.name == gameName &&
            speedrun.cart.region == region
        }
    }

    fun findByGameAndSystemAndRegion(gameName: String, systemName: String, region: Region): List<Speedrun> {
        return findAll().filter { speedrun ->
            speedrun.cart.game.name == gameName &&
            speedrun.cart.system.name == systemName &&
            speedrun.cart.region == region
        }
    }

    fun find(categoryName: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): Speedrun {
        return findAll().firstOrNull { speedrun ->
            speedrun.category.name == categoryName &&
            speedrun.cart.game.name == gameName &&
            speedrun.cart.system.name == systemName &&
            speedrun.cart.system.isEmulated == isEmulated &&
            speedrun.cart.region == region &&
            speedrun.cart.version == version
        } ?: throw EntityNotFoundException()
    }

    fun findAll(categoryName: String?, gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<Speedrun> {
        return findAll()
            .asSequence()
            .filter { speedrun ->  categoryName?.let { speedrun.category.name == categoryName } ?: true }
            .filter { speedrun ->  gameName?.let { speedrun.cart.game.name == gameName } ?: true }
            .filter { speedrun ->  systemName?.let { speedrun.cart.system.name == systemName } ?: true }
            .filter { speedrun ->  isEmulated?.let { speedrun.cart.system.isEmulated == isEmulated } ?: true }
            .filter { speedrun ->  region?.let { speedrun.cart.region == region } ?: true }
            .filter { speedrun ->  version?.let { speedrun.cart.version == version } ?: true }
            .toList()
    }

}
