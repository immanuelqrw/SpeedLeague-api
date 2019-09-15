package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.LeagueSpeedrun
import com.immanuelqrw.speedleague.api.entity.Region
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class LeagueSpeedrunService : BaseUniqueService<LeagueSpeedrun>(LeagueSpeedrun::class.java) {

    // ! Need to add findByVersion -- possibly at Controller level for convenience

    fun findAllByLeague(leagueName: String): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName
        }
    }

    fun findAllByGame(gameName: String): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.game.name == gameName
        }
    }

    fun findAllBySystem(systemName: String): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.distinctSystem.system.name == systemName
        }
    }

    fun findAllByRegion(region: Region): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.distinctSystem.region == region
        }
    }

    fun findAllByLeagueAndGame(leagueName: String, gameName: String): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName && leagueSpeedrun.speedrun.cart.game.name == gameName
        }
    }

    fun findAllByLeagueAndSystem(leagueName: String, systemName: String): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName && leagueSpeedrun.speedrun.cart.distinctSystem.system.name == systemName
        }
    }

    fun findAllByLeagueAndRegion(leagueName: String, region: Region): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName && leagueSpeedrun.speedrun.cart.distinctSystem.region == region
        }
    }

    fun findAllByCategoryAndGame(categoryName: String, gameName: String): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.category.name == categoryName && leagueSpeedrun.speedrun.cart.game.name == gameName
        }
    }

    fun findAllByGameAndSystem(gameName: String, systemName: String): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.game.name == gameName && leagueSpeedrun.speedrun.cart.distinctSystem.system.name == systemName
        }
    }

    fun findAllByGameAndRegion(gameName: String, region: Region): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.game.name == gameName && leagueSpeedrun.speedrun.cart.distinctSystem.region == region
        }
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.distinctSystem.system.name == systemName && leagueSpeedrun.speedrun.cart.distinctSystem.region == region
        }
    }

    fun findByLeagueAndGameAndSystem(leagueName: String, gameName: String, systemName: String): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.system.name == systemName
        }
    }

    fun findByLeagueAndGameAndRegion(leagueName: String, gameName: String, region: Region): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.region == region
        }
    }

    fun findByLeagueAndSystemAndRegion(leagueName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.system.name == systemName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.region == region
        }
    }

    fun findByCategoryAndGameAndSystem(categoryName: String, gameName: String, systemName: String): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.category.name == categoryName &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.system.name == systemName
        }
    }

    fun findByCategoryAndGameAndRegion(categoryName: String, gameName: String, region: Region): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.category.name == categoryName &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.region == region
        }
    }

    fun findByGameAndSystemAndRegion(gameName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.system.name == systemName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.region == region
        }
    }

    fun findAllByCategoryAndGameAndSystemAndRegion(categoryName: String, gameName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.category.name == categoryName &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.system.name == systemName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.region == region
        }
    }

    fun findAllByLeagueAndGameAndSystemAndRegion(leagueName: String, gameName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.system.name == systemName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.region == region
        }
    }

    fun findAllByLeagueAndCategoryAndGameAndRegion(leagueName: String, categoryName: String, gameName: String, region: Region): List<LeagueSpeedrun> {
        return findAll().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.speedrun.category.name == categoryName &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.region == region
        }
    }

    fun find(leagueName: String, categoryName: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, versionName: String): LeagueSpeedrun {
        return findAll().firstOrNull { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.speedrun.category.name == categoryName &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.system.name == systemName &&
            leagueSpeedrun.speedrun.cart.distinctSystem.system.isEmulated == isEmulated &&
            leagueSpeedrun.speedrun.cart.distinctSystem.region == region &&
            leagueSpeedrun.speedrun.cart.distinctSystem.version.name == versionName
        } ?: throw EntityNotFoundException()
    }

    fun findAll(leagueName: String?, categoryName: String?, gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, versionName: String?): List<LeagueSpeedrun> {
        return findAll()
            .asSequence()
            .filter { leagueSpeedrun ->  leagueName?.let { leagueSpeedrun.league.name == leagueName } ?: true }
            .filter { leagueSpeedrun ->  categoryName?.let { leagueSpeedrun.speedrun.category.name == categoryName } ?: true }
            .filter { leagueSpeedrun ->  gameName?.let { leagueSpeedrun.speedrun.cart.game.name == gameName } ?: true }
            .filter { leagueSpeedrun ->  systemName?.let { leagueSpeedrun.speedrun.cart.distinctSystem.system.name == systemName } ?: true }
            .filter { leagueSpeedrun ->  isEmulated?.let { leagueSpeedrun.speedrun.cart.distinctSystem.system.isEmulated == isEmulated } ?: true }
            .filter { leagueSpeedrun ->  region?.let { leagueSpeedrun.speedrun.cart.distinctSystem.region == region } ?: true }
            .filter { leagueSpeedrun ->  versionName?.let { leagueSpeedrun.speedrun.cart.distinctSystem.version.name == versionName } ?: true }
            .toList()
    }

}
