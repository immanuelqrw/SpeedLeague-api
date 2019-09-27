package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.LeagueSpeedrun
import com.immanuelqrw.speedleague.api.entity.LeagueType
import com.immanuelqrw.speedleague.api.entity.Region
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class LeagueSpeedrunSeekService : BaseUniqueService<LeagueSpeedrun>(LeagueSpeedrun::class.java) {

    // ! Need to add findByVersion -- possibly at Controller level for convenience

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.league.season == season &&
            leagueSpeedrun.league.tier.level == tierLevel
        }
    }

    fun findAllByGame(gameName: String): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.game.name == gameName
        }
    }

    fun findAllBySystem(systemName: String): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.system.name == systemName
        }
    }

    fun findAllByRegion(region: Region): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.region == region
        }
    }

    fun findAllByLeagueAndGame(leagueName: String, gameName: String): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName && leagueSpeedrun.speedrun.cart.game.name == gameName
        }
    }

    fun findAllByLeagueAndSystem(leagueName: String, systemName: String): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName && leagueSpeedrun.speedrun.cart.system.name == systemName
        }
    }

    fun findAllByLeagueAndRegion(leagueName: String, region: Region): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName && leagueSpeedrun.speedrun.cart.region == region
        }
    }

    fun findAllByCategoryAndGame(category: String, gameName: String): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.category == category && leagueSpeedrun.speedrun.cart.game.name == gameName
        }
    }

    fun findAllByGameAndSystem(gameName: String, systemName: String): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.game.name == gameName && leagueSpeedrun.speedrun.cart.system.name == systemName
        }
    }

    fun findAllByGameAndRegion(gameName: String, region: Region): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.game.name == gameName && leagueSpeedrun.speedrun.cart.region == region
        }
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.system.name == systemName && leagueSpeedrun.speedrun.cart.region == region
        }
    }

    fun findByLeagueAndGameAndSystem(leagueName: String, gameName: String, systemName: String): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.system.name == systemName
        }
    }

    fun findByLeagueAndGameAndRegion(leagueName: String, gameName: String, region: Region): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.region == region
        }
    }

    fun findByLeagueAndSystemAndRegion(leagueName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.speedrun.cart.system.name == systemName &&
            leagueSpeedrun.speedrun.cart.region == region
        }
    }

    fun findByCategoryAndGameAndSystem(category: String, gameName: String, systemName: String): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.category == category &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.system.name == systemName
        }
    }

    fun findByCategoryAndGameAndRegion(category: String, gameName: String, region: Region): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.category == category &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.region == region
        }
    }

    fun findByGameAndSystemAndRegion(gameName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.system.name == systemName &&
            leagueSpeedrun.speedrun.cart.region == region
        }
    }

    fun findAllByCategoryAndGameAndSystemAndRegion(category: String, gameName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.speedrun.category == category &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.system.name == systemName &&
            leagueSpeedrun.speedrun.cart.region == region
        }
    }

    fun findAllByLeagueAndGameAndSystemAndRegion(leagueName: String, gameName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.system.name == systemName &&
            leagueSpeedrun.speedrun.cart.region == region
        }
    }

    fun findAllByLeagueAndCategoryAndGameAndRegion(leagueName: String, category: String, gameName: String, region: Region): List<LeagueSpeedrun> {
        return findAllActive().filter { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.speedrun.category == category &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.region == region
        }
    }

    fun find(leagueName: String, category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): LeagueSpeedrun {
        return findAllActive().firstOrNull { leagueSpeedrun ->
            leagueSpeedrun.league.name == leagueName &&
            leagueSpeedrun.speedrun.category == category &&
            leagueSpeedrun.speedrun.cart.game.name == gameName &&
            leagueSpeedrun.speedrun.cart.system.name == systemName &&
            leagueSpeedrun.speedrun.cart.system.isEmulated == isEmulated &&
            leagueSpeedrun.speedrun.cart.region == region &&
            leagueSpeedrun.speedrun.cart.version == version
        } ?: throw EntityNotFoundException()
    }

    fun findAll(leagueName: String?, leagueType: LeagueType?, category: String?, gameName: String?, systemName: String?, isEmulated: Boolean?, region: Region?, version: String?): List<LeagueSpeedrun> {
        return findAllActive()
            .asSequence()
            .filter { leagueSpeedrun ->  leagueName?.let { leagueSpeedrun.league.name == leagueName } ?: true }
            .filter { leagueSpeedrun ->  leagueType?.let { leagueSpeedrun.league.type == leagueType } ?: true }
            .filter { leagueSpeedrun ->  category?.let { leagueSpeedrun.speedrun.category == category } ?: true }
            .filter { leagueSpeedrun ->  gameName?.let { leagueSpeedrun.speedrun.cart.game.name == gameName } ?: true }
            .filter { leagueSpeedrun ->  systemName?.let { leagueSpeedrun.speedrun.cart.system.name == systemName } ?: true }
            .filter { leagueSpeedrun ->  isEmulated?.let { leagueSpeedrun.speedrun.cart.system.isEmulated == isEmulated } ?: true }
            .filter { leagueSpeedrun ->  region?.let { leagueSpeedrun.speedrun.cart.region == region } ?: true }
            .filter { leagueSpeedrun ->  version?.let { leagueSpeedrun.speedrun.cart.version == version } ?: true }
            .toList()
    }

}
