package com.example.kotestdemo

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.testContextManager
import io.kotest.matchers.shouldBe

class TestContextExceptionTest: FunSpec({

    beforeEach {
        Thread.currentThread().name.also { println("beforeEach thread name: $it") }
    }

    test("test context on the same thread") {
        Thread.currentThread().name.also { println("test thread name: $it") }
        1+1 shouldBe 3
    }

    afterEach {
        Thread.currentThread().name.also { println("afterEach thread name: $it") }
        println("Test exception: "+testContextManager().testContext.testException)
    }

}) {
    override fun extensions(): List<Extension> = listOf(SpringExtension)
}