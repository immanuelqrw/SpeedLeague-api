package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import com.immanuelqrw.speedleague.api.entity.Runner
import com.immanuelqrw.speedleague.api.exception.NotRegisteredException
import com.immanuelqrw.speedleague.api.service.seek.LeagueRunnerSeekService
import com.immanuelqrw.speedleague.api.dto.input.RaceTime as RaceTimeInput
import com.immanuelqrw.speedleague.api.dto.output.RaceTime as RaceTimeOutput
import com.immanuelqrw.speedleague.api.dto.update.RaceTime as RaceTimeRegister
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerSeekService
import com.immanuelqrw.speedleague.api.service.seek.RaceSeekService
import com.immanuelqrw.speedleague.api.service.seek.RunnerSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RaceTimeService {

    @Autowired
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @Autowired
    private lateinit var leagueRunnerSeekService: LeagueRunnerSeekService

    @Autowired
    private lateinit var raceSeekService: RaceSeekService

    @Autowired
    private lateinit var runnerSeekService: RunnerSeekService

    private fun validateLeagueRegistration(race: Race, runner: Runner) {
        val raceLeague: League = race.league
        val runnerLeagues: List<League> = leagueRunnerSeekService.findAllByRunner(runner.name).map { leagueRunner -> leagueRunner.league}

        if (raceLeague !in runnerLeagues) {
            throw NotRegisteredException("Runner ${runner.name} is not a part of League ${race.league.name}")
        }
    }

    fun register(raceTimeInput: RaceTimeInput): RaceTimeOutput {
        return raceTimeInput.run {
            val race: Race = raceSeekService.findByName(raceName)
            val runner: Runner = runnerSeekService.findByName(runnerName)

            validateLeagueRegistration(race, runner)

            val raceRunner = RaceRunner(
                race = race,
                runner = runner,
                time = time,
                outcome = outcome
            )
            val createdRaceRunner: RaceRunner = raceRunnerSeekService.create(raceRunner)

            createdRaceRunner.output
        }
    }

    fun registerTime(raceTimeRegister: RaceTimeRegister): RaceTimeOutput {
        val modifiedRaceRunner: RaceRunner = raceRunnerSeekService.registerRaceTime(raceTimeRegister)

        return modifiedRaceRunner.output
    }

}
