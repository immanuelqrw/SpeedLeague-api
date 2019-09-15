package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.core.api.service.BaseUniqueService
import com.immanuelqrw.speedleague.api.entity.DistinctSystem
import com.immanuelqrw.speedleague.api.entity.Region
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class DistinctSystemService : BaseUniqueService<DistinctSystem>(DistinctSystem::class.java) {

    fun findAllBySystem(systemName: String): List<DistinctSystem> {
        return findAll().filter { distinctSystem ->
            distinctSystem.system.name == systemName
        }
    }

    fun findAllByRegion(region: Region): List<DistinctSystem> {
        return findAll().filter { distinctSystem ->
            distinctSystem.region == region
        }
    }

    fun findAllByVersion(versionName: String): List<DistinctSystem> {
        return findAll().filter { distinctSystem ->
            distinctSystem.version.name == versionName
        }
    }

    fun findAllBySystemAndRegion(systemName: String, region: Region): List<DistinctSystem> {
        return findAll().filter { distinctSystem ->
            distinctSystem.system.name == systemName && distinctSystem.region == region
        }
    }

    fun findAllBySystemAndVersion(systemName: String, versionName: String): List<DistinctSystem> {
        return findAll().filter { distinctSystem ->
            distinctSystem.system.name == systemName && distinctSystem.version.name == versionName
        }
    }

    fun findAllByRegionAndVersion(region: Region, versionName: String): List<DistinctSystem> {
        return findAll().filter { distinctSystem ->
            distinctSystem.region == region && distinctSystem.version.name == versionName
        }
    }

    fun findBySystemAndRegionAndVersion(systemName: String, region: Region, versionName: String): DistinctSystem {
        return findAll().firstOrNull { distinctSystem ->
            distinctSystem.system.name == systemName &&
            distinctSystem.region == region &&
            distinctSystem.version.name == versionName
        } ?: throw EntityNotFoundException()
    }
    
    fun findAll(systemName: String?, isEmulated: Boolean?, region: Region?, versionName: String?): List<DistinctSystem> {
        return findAll()
            .filter { distinctSystem ->  systemName?.let { distinctSystem.system.name == systemName } ?: true }
            .filter { distinctSystem ->  isEmulated?.let { distinctSystem.system.isEmulated == isEmulated } ?: true }
            .filter { distinctSystem ->  region?.let { distinctSystem.region == region } ?: true }
            .filter { distinctSystem ->  versionName?.let { distinctSystem.version.name == versionName } ?: true }
    }

}
