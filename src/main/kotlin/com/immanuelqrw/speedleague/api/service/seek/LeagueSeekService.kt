package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.dto.update.LeagueDivisionShift
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.repository.LeagueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class LeagueSeekService : BaseUniqueService<League>(League::class.java) {

    fun find(name: String, season: Int, tierLevel: Int): League {
        return findAll(search = "name:$name;season:$season;tier.level:$tierLevel")
            .firstOrNull()
            ?: throw EntityNotFoundException()
    }

    fun findAllTiers(name: String, season: Int): List<League> {
        return findAll(search = "name:$name;season:$season;").sortedBy { league -> league.tier.level }
    }

    fun findBottomLeague(name: String, season: Int): League {
        return findAll(search = "name:$name;season:$season;")
            .maxBy { league -> league.tier.level }
            ?: throw EntityNotFoundException()
    }

    fun updateDivisionShifts(leagueDivisionShift: LeagueDivisionShift): League {
        val modifiedLeague: League = leagueDivisionShift.run {
            val mainLeague: League = find(leagueName, season, tierLevel)

            promotions?.let {
                val promotedLeague: League = find(leagueName, season, tierLevel - 1)
                mainLeague.promotions = promotions
                promotedLeague.relegations = promotions

                create(promotedLeague)
            }

            relegations?.let {
                val relegatedLeague: League = find(leagueName, season, tierLevel + 1)
                mainLeague.relegations = relegations
                relegatedLeague.promotions = relegations

                create(relegatedLeague)
            }

            mainLeague
        }

        return create(modifiedLeague)
    }

}
