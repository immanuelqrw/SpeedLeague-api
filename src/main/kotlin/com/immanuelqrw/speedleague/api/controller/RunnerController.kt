package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.LeagueRunner
import com.immanuelqrw.speedleague.api.entity.Runner
import com.immanuelqrw.speedleague.api.service.seek.LeagueRunnerService
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import com.immanuelqrw.speedleague.api.dto.input.Runner as RunnerInput
import com.immanuelqrw.speedleague.api.dto.input.LeagueRunner as LeagueRunnerInput
import com.immanuelqrw.speedleague.api.dto.output.Runner as RunnerOutput
import com.immanuelqrw.speedleague.api.dto.output.LeagueRunner as LeagueRunnerOutput
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerService
import com.immanuelqrw.speedleague.api.service.seek.RunnerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/runner")
class RunnerController {

    @Autowired
    private lateinit var runnerService: RunnerService

    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var raceRunnerService: RaceRunnerService

    @Autowired
    private lateinit var leagueRunnerService: LeagueRunnerService

    private fun convertToOutput(runner: Runner): RunnerOutput {
        return runner.run {
            RunnerOutput(
                name = name,
                joinedOn = joinedOn
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody runnerInput: RunnerInput): RunnerOutput {
        return runnerInput.run {
            val runner = Runner(
                name = name
            )
            val createdRunner: Runner = runnerService.create(runner)

            convertToOutput(createdRunner)
        }
    }

    @PostMapping(path = ["/registerToLeague"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun register(@RequestBody leagueRunnerInput: LeagueRunnerInput): LeagueRunnerOutput {

        return leagueRunnerInput.run {
            val league: League = leagueService.findByName(leagueName)
            val runner: Runner = runnerService.findByName(runnerName)

            val leagueRunner = LeagueRunner(
                league = league,
                runner = runner,
                joinedOn = joinedOn
            )
            val createdLeagueRunner: LeagueRunner = leagueRunnerService.create(leagueRunner)

            LeagueRunnerOutput(
                leagueName = createdLeagueRunner.league.name,
                runnerName = createdLeagueRunner.runner.name,
                joinedOn = createdLeagueRunner.joinedOn
            )
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<RunnerOutput> {
        return runnerService.findAll(search = search).map { runner -> convertToOutput(runner) }
    }

    @GetMapping(path = ["/race/{raceName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("raceName")
        raceName: String
    ): List<RunnerOutput> {
        return raceRunnerService.findAllByRace(raceName).map { convertToOutput(it.runner) }
    }

}
