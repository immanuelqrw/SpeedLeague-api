package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.LeagueRunner
import com.immanuelqrw.speedleague.api.entity.Runner
import com.immanuelqrw.speedleague.api.exception.LeagueIsFullException
import com.immanuelqrw.speedleague.api.service.seek.LeagueRunnerSeekService
import com.immanuelqrw.speedleague.api.service.seek.LeagueSeekService
import com.immanuelqrw.speedleague.api.dto.input.Runner as RunnerInput
import com.immanuelqrw.speedleague.api.dto.input.LeagueRunner as LeagueRunnerInput
import com.immanuelqrw.speedleague.api.dto.output.Runner as RunnerOutput
import com.immanuelqrw.speedleague.api.dto.output.LeagueRunner as LeagueRunnerOutput
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerSeekService
import com.immanuelqrw.speedleague.api.service.seek.RunnerSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RunnerService {

    @Autowired
    private lateinit var runnerSeekService: RunnerSeekService

    @Autowired
    private lateinit var leagueSeekService: LeagueSeekService

    @Autowired
    private lateinit var raceRunnerSeekService: RaceRunnerSeekService

    @Autowired
    private lateinit var leagueRunnerSeekService: LeagueRunnerSeekService

    @Autowired
    private lateinit var leagueService: LeagueService

    fun create(runnerInput: RunnerInput): RunnerOutput {
        return runnerInput.run {
            val runner = Runner(
                name = name
            )
            val createdRunner: Runner = runnerSeekService.create(runner)

            createdRunner.output
        }
    }

    fun register(leagueRunnerInput: LeagueRunnerInput): LeagueRunnerOutput {
        return leagueRunnerInput.run {
            // Can only be registered to bottom league
            val league: League = leagueSeekService.findBottomLeague(leagueName, season)
            val runner: Runner = runnerSeekService.findByName(runnerName)

            leagueService.validateLeagueChange(league.endedOn,"Runner $runnerName cannot be registered - League has ended [End Date: ${league.endedOn}]")

            val currentRunnerCount: Int = leagueRunnerSeekService.findAllByLeague(league.name, league.season, league.tier.level).size
            if (currentRunnerCount >= league.runnerLimit) {
                throw LeagueIsFullException("Runner $runnerName cannot be registered due to league being full [${league.runnerLimit} runners]")
            }

            val leagueRunner = LeagueRunner(
                league = league,
                runner = runner,
                joinedOn = joinedOn
            )
            val createdLeagueRunner: LeagueRunner = leagueRunnerSeekService.create(leagueRunner)

            LeagueRunnerOutput(
                leagueName = createdLeagueRunner.league.name,
                runnerName = createdLeagueRunner.runner.name,
                joinedOn = createdLeagueRunner.joinedOn,
                season = createdLeagueRunner.league.season,
                tierLevel = createdLeagueRunner.league.tier.level,
                tierName = createdLeagueRunner.league.tier.name
            )
        }
    }

    fun findAll(search: String?): Iterable<RunnerOutput> {
        return runnerSeekService.findAllActive(search = search).map { runner -> runner.output }
    }

    fun findAllRunners(raceName: String): List<RunnerOutput> {
        return raceRunnerSeekService.findAllByRace(raceName).map { it.runner.output }
    }

    fun findAllRunners(leagueName: String, season: Int, tierLevel: Int): List<RunnerOutput> {
        return leagueRunnerSeekService.findAllByLeague(leagueName, season, tierLevel).map { it.runner.output }
    }

}
