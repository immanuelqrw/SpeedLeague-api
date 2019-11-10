package com.immanuelqrw.speedleague.api.service

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DivisionShiftServiceTest {

    @BeforeAll
    fun setUp() {}

    @Test
    fun addDivisionShiftRules() {}

    @Test
    fun addPromotionRules() {}

    @Test
    fun addRelegationRules() {}

    @Test
    fun matchQualifiedRunners() {}

    @Test
    fun create() {}

    @Test
    fun findAll() {}

    @Test
    fun testFindAll() {}

    @Test
    fun replace() {}

    @Test
    fun createPromotionRules() {}

    @Test
    fun findAllPromotionRules() {}

    @Test
    fun replacePromotionRules() {}

    @Test
    fun createRelegationRules() {}

    @Test
    fun findAllRelegationRules() {}

    @Test
    fun replaceRelegationRules() {}
}
