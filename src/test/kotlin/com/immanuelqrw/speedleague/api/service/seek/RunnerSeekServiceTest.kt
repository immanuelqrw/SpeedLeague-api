package com.immanuelqrw.speedleague.api.service.seek

import com.immanuelqrw.speedleague.api.entity.Runner
import com.immanuelqrw.speedleague.api.repository.RunnerRepository
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RunnerSeekServiceTest {

    private val validName: String = "Shoo"
    private val invalidName: String = "Arjay"

    @Mock
    private lateinit var validRunner: Runner

    @Mock
    private lateinit var runnerRepository: RunnerRepository

    @InjectMocks
    private lateinit var runnerSeekService: RunnerSeekService

    @BeforeAll
    fun setUp() {
        whenever(runnerRepository.findByNameAndRemovedOnIsNull(validName)).thenReturn(validRunner)

        whenever(runnerRepository.findByNameAndRemovedOnIsNull(invalidName)).thenReturn(null)
    }

    @Nested
    inner class Success {

        @Test
        fun `given valid name - when findByName - then return valid runner`() {
            val expectedRunner: Runner = validRunner

            val actualRunner = runnerSeekService.findByName(validName)

            actualRunner shouldEqual expectedRunner
        }

    }

    @Nested
    inner class Failure {

        @Test
        fun `given invalid name - when findByName - then throw EntityNotFoundException`() {
            invoking { runnerSeekService.findByName(invalidName) } shouldThrow EntityNotFoundException::class
        }

    }

}
