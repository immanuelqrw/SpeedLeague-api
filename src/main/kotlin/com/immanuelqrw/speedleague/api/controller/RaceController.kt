package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.Race as RaceInput
import com.immanuelqrw.speedleague.api.dto.output.Race as RaceOutput
import com.immanuelqrw.speedleague.api.service.RaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/race")
class RaceController {

    @Autowired
    private lateinit var raceService: RaceService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody raceInput: RaceInput): RaceOutput {
        return raceService.create(raceInput)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<RaceOutput> {
        return raceService.findAll(search = search)
    }

    @GetMapping(path = ["/runner/{runnerName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllRaces(
        @PathVariable("runnerName")
        runnerName: String
    ): List<RaceOutput> {
        return raceService.findAllRaces(runnerName)
    }

}
