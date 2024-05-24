package com.example.kotestdemo

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.testContextManager
import io.kotest.matchers.equals.shouldBeEqual
import org.springframework.test.context.TestContext

class TestContextCoroutineTest: FunSpec({

    lateinit var testContext: TestContext

    beforeEach {
        Thread.currentThread().name.also { println("beforeEach thread name: $it") }
        testContext = testContextManager().testContext
        println("beforeEach: TestContext@"+System.identityHashCode(testContext))
    }

    test("test context on the same thread") {
        Thread.currentThread().name.also { println("test thread name: $it") }
        val localContext = testContextManager().testContext
        println("test: TestContext@"+System.identityHashCode(localContext))
        System.identityHashCode(localContext) shouldBeEqual System.identityHashCode(testContext)
    }

    test("test context on different threads").config(invocations = 2, threads = 2) {
        Thread.currentThread().name.also { println("test thread name: $it") }
        val localContext = testContextManager().testContext
        println("test: TestContext@"+System.identityHashCode(localContext))
        System.identityHashCode(localContext) shouldBeEqual System.identityHashCode(testContext)
    }

    afterEach {
        Thread.currentThread().name.also { println("afterEach thread name: $it") }
        val localContext = testContextManager().testContext
        println("afterEach: TestContext@"+System.identityHashCode(localContext))
    }

}) {
    override fun extensions(): List<Extension> = listOf(SpringExtension)
}
