package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.LeagueSpeedrun
import com.immanuelqrw.speedleague.api.entity.LeagueType
import com.immanuelqrw.speedleague.api.entity.Region
import com.immanuelqrw.speedleague.api.repository.LeagueSpeedrunRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class LeagueSpeedrunSeekService : BaseUniqueService<LeagueSpeedrun>(LeagueSpeedrun::class.java) {

    @Autowired
    private lateinit var leagueSpeedrunRepository: LeagueSpeedrunRepository

    // ! Need to add findByVersion -- possibly at Controller level for convenience

    fun findAllByLeague(leagueName: String, season: Int, tierLevel: Int): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository.findAllByLeagueNameAndLeagueSeasonAndLeagueTierLevel(leagueName, season, tierLevel)
    }

    fun findAllByGame(gameName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository.findAllBySpeedrunCartGameName(gameName)
    }

    fun findAllBySystem(systemName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository.findAllBySpeedrunCartSystemName(systemName)
    }

    fun findAllByRegion(region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository.findAllBySpeedrunCartRegion(region)
    }

    fun findAllByLeagueAndGame(leagueName: String, gameName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartGameName(leagueName, gameName)
    }

    fun findAllByLeagueAndSystem(leagueName: String, systemName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartSystemName(leagueName, systemName)
    }

    fun findAllByLeagueAndRegion(leagueName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartRegion(leagueName, region)
    }

    fun findAllByCategoryAndGame(category: String, gameName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCategoryAndSpeedrunCartGameName(category, gameName)
    }

    fun findAllByGameAndSystem(gameName: String, systemName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCartGameNameAndSpeedrunCartSystemName(gameName, systemName)
    }

    fun findAllByGameAndRegion(gameName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCartGameNameAndSpeedrunCartRegion(gameName, region)
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCartSystemNameAndSpeedrunCartRegion(systemName, region)
    }

    fun findAllByLeagueAndGameAndSystem(leagueName: String, gameName: String, systemName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartSystemName(leagueName, gameName, systemName)
    }

    fun findAllByLeagueAndGameAndRegion(leagueName: String, gameName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartRegion(leagueName, gameName, region)
    }

    fun findAllByLeagueAndSystemAndRegion(leagueName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartSystemNameAndSpeedrunCartRegion(leagueName, systemName, region)
    }

    fun findAllByCategoryAndGameAndSystem(category: String, gameName: String, systemName: String): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemName(category, gameName, systemName)
    }

    fun findAllByCategoryAndGameAndRegion(category: String, gameName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartRegion(category, gameName, region)
    }

    fun findAllByGameAndSystemAndRegion(gameName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegion(gameName, systemName, region)
    }

    fun findAllByCategoryAndGameAndSystemAndRegion(category: String, gameName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllBySpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegion(category, gameName, systemName, region)
    }

    fun findAllByLeagueAndGameAndSystemAndRegion(leagueName: String, gameName: String, systemName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartRegion(leagueName, gameName, systemName, region)
    }

    fun findAllByLeagueAndCategoryAndGameAndRegion(leagueName: String, category: String, gameName: String, region: Region): List<LeagueSpeedrun> {
        return leagueSpeedrunRepository
            .findAllByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartRegion(leagueName, category, gameName, region)
    }

    fun find(leagueName: String, category: String, gameName: String, systemName: String, isEmulated: Boolean, region: Region, version: String): LeagueSpeedrun {
        return leagueSpeedrunRepository
            .findFirstByLeagueNameAndSpeedrunCategoryAndSpeedrunCartGameNameAndSpeedrunCartSystemNameAndSpeedrunCartSystemIsEmulatedAndSpeedrunCartRegionAndSpeedrunCartVersion(leagueName, category, gameName, systemName, isEmulated, region, version)
            ?: throw EntityNotFoundException()
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
