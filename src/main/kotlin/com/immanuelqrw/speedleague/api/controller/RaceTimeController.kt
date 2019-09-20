package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import com.immanuelqrw.speedleague.api.entity.Runner
import com.immanuelqrw.speedleague.api.exception.NotRegisteredException
import com.immanuelqrw.speedleague.api.service.seek.LeagueRunnerService
import com.immanuelqrw.speedleague.api.dto.input.RaceTime as RaceTimeInput
import com.immanuelqrw.speedleague.api.dto.output.RaceTime as RaceTimeOutput
import com.immanuelqrw.speedleague.api.dto.update.RaceTime as RaceTimeRegister
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerService
import com.immanuelqrw.speedleague.api.service.seek.RaceService
import com.immanuelqrw.speedleague.api.service.seek.RunnerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/raceTime")
class RaceTimeController {

    @Autowired
    private lateinit var raceRunnerService: RaceRunnerService

    @Autowired
    private lateinit var leagueRunnerService: LeagueRunnerService

    @Autowired
    private lateinit var raceService: RaceService

    @Autowired
    private lateinit var runnerService: RunnerService

    private fun validateLeagueRegistration(race: Race, runner: Runner) {
        val raceLeague: League = race.league
        val runnerLeagues: List<League> = leagueRunnerService.findAllByRunner(runner.name).map {leagueRunner -> leagueRunner.league}

        if (raceLeague !in runnerLeagues) {
            throw NotRegisteredException("Runner ${runner.name} is not a part of League ${race.league.name}")
        }
    }

    @PostMapping(path = ["/register"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun register(@RequestBody raceTimeInput: RaceTimeInput): RaceTimeOutput {

        return raceTimeInput.run {
            val race: Race = raceService.findByName(raceName)
            val runner: Runner = runnerService.findByName(runnerName)

            validateLeagueRegistration(race, runner)

            val raceRunner = RaceRunner(
                race = race,
                runner = runner,
                time = time,
                outcome = outcome
            )
            val createdRaceRunner: RaceRunner = raceRunnerService.create(raceRunner)

            createdRaceRunner.output
        }
    }

    @PatchMapping(path = ["/verifyTime"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerTime(@RequestBody raceTimeRegister: RaceTimeRegister): RaceTimeOutput {
        val modifiedRaceRunner: RaceRunner = raceRunnerService.registerRaceTime(raceTimeRegister)

        return modifiedRaceRunner.output
    }

}
