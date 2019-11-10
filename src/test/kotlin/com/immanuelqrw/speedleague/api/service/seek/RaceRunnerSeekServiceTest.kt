package com.immanuelqrw.speedleague.api.service.seek

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RaceRunnerSeekServiceTest {

    @BeforeAll
    fun setUp() {}

    @Test
    fun findByRaceAndRunner() {}

    @Test
    fun findAllByRace() {}

    @Test
    fun findAllByRunner() {}

    @Test
    fun registerRaceTime() {}
}
