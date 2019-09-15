package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.DistinctSystem as DistinctSystemInput
import com.immanuelqrw.speedleague.api.dto.output.DistinctSystem as DistinctSystemOutput
import com.immanuelqrw.speedleague.api.entity.DistinctSystem
import com.immanuelqrw.speedleague.api.entity.Version
import com.immanuelqrw.speedleague.api.entity.System as SystemEntity
import com.immanuelqrw.speedleague.api.service.seek.DistinctSystemService
import com.immanuelqrw.speedleague.api.service.seek.SystemService
import com.immanuelqrw.speedleague.api.service.seek.VersionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/distinctSystem")
class DistinctSystemController {

    @Autowired
    private lateinit var distinctSystemService: DistinctSystemService

    @Autowired
    private lateinit var systemService: SystemService

    @Autowired
    private lateinit var versionService: VersionService

    private fun convertToOutput(distinctSystem: DistinctSystem): DistinctSystemOutput {
        return distinctSystem.run {
            DistinctSystemOutput(
                systemName = system.name,
                isEmulated = system.isEmulated,
                region = region,
                versionName = version?.name
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody distinctSystemInput: DistinctSystemInput): DistinctSystemOutput {
        return distinctSystemInput.run {
            val system: SystemEntity = systemService.findByName(systemName)?.let { it } ?: run {
                val systemToCreate = SystemEntity(
                    name = systemName,
                    isEmulated = isEmulated
                )
                systemService.create(systemToCreate)
            }
            
            val version: Version = versionService.findByName(versionName)?.let { it } ?: run {
                val versionToCreate = Version(
                    name = versionName
                )
                versionService.create(versionToCreate)
            }
            
            val distinctSystem = DistinctSystem(
                system = system,
                region = region,
                version = version
            )
            val createdDistinctSystem: DistinctSystem = distinctSystemService.create(distinctSystem)

            convertToOutput(createdDistinctSystem)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<DistinctSystemOutput> {
        return distinctSystemService.findAll(search = search).map { distinctSystem -> convertToOutput(distinctSystem) }
    }

}
