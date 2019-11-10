package com.immanuelqrw.speedleague.api.service

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SeasonServiceTest {

    @BeforeAll
    fun setUp() {}

    @Test
    fun shiftDivisions() {}

    @Test
    fun generateRoundRobin() {}
}
