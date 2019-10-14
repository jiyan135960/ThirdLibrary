package com.example.thirdlibrary

import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * Created by zyx on 19-9-28.
 */

class CoroutinesTest {

    @Test
    fun globalScopeTest() {
        GlobalScope.launch {
            delay(2000L)
            println("2,")
        }
        println("1,")
        Thread {
            runBlocking {
                delay(1000L)
            }
            println("3,")
        }.start()
        runBlocking {
            launch {
                delay(1500L)
                println("5,")
            }.join()
            coroutineScope {
                delay(1500L)
                println("7,")
            }
            println("6,")
            delay(5000L)
        }
        println("4,")
    }

    @Test
    fun threadLoopDotTest() {
        repeat(1000000) {
            Thread {
                if (it % 10000 == 0) printMemory()
                print('.')
            }.start()
        }
    }

    @Test
    fun coroutinesLoopDotTest() {
        runBlocking {
            repeat(1000000) {
                launch {
                    if (it % 10000 == 0) printMemory()
                    print(".")
                }
            }
        }
    }

    @Test
    fun globalLoopDotTest() {
        runBlocking {
            repeat(1000000) {
                GlobalScope.launch {
                    if (it % 10000 == 0) printMemory()
                    print(".")
                }.join()
            }
        }
    }


    @Test
    fun mainLoopDotTest() {
        repeat(1000000) {
            if (it % 10000 == 0) printMemory()
            print('.')
        }
    }

    @Before
    fun start() {
        printMemory()
    }

    private suspend fun printSuspendMemory() {
        println("")
        println("thread:${Thread.currentThread()}")
        println("${Runtime.getRuntime().freeMemory() / 1024 / 1024}M")
        delay(1000L)
        println("${Runtime.getRuntime().totalMemory() / 1024 / 1024}M")
        delay(1000L)
        println("${Runtime.getRuntime().maxMemory() / 1024 / 1024}M")
    }

    private fun printMemory() {
        println("")
        println("thread:${Thread.currentThread()}")
        println("${Runtime.getRuntime().freeMemory() / 1024 / 1024}M")
        println("${Runtime.getRuntime().totalMemory() / 1024 / 1024}M")
        println("${Runtime.getRuntime().maxMemory() / 1024 / 1024}M")
    }

    @Test
    fun cancelTest() {
        runBlocking {
            val job = launch {
                repeat(1000) {
                    println("job:$it ")
                    delay(500L)
                }
            }
            delay(1300L)
            println("canceling")
            job.cancel()
            job.join()
            println("quit")
        }
    }

    @Test
    fun notCancelTest() {
        runBlocking {
            val job = launch {
                try {
                    withContext(NonCancellable) {
                        repeat(1000) {
                            println("job:$it ")
                            delay(500L)
                        }
                    }
                } finally {
                    println("job: I'm running finally")
                    delay(5000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
            delay(1300L)
            println("canceling")
            job.cancel()
            job.join()
            println("quit")
        }
    }

    @Test
    fun timeOutTest() {
        runBlocking {
            val job = launch {
                try {
                    withTimeout(5000L) {
                        repeat(1000) {
                            println("job:$it ")
                            delay(500L)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    println("job: I'm running finally")
                    delay(5000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
            delay(13000L)
        }
    }

    @Test
    fun testSuspend() {
        runBlocking {
            val time = measureTimeMillis {
                val first = async(start = CoroutineStart.LAZY) { firstThing() }
                val second = async(start = CoroutineStart.LAZY) { secondThing() }
                println("answer first: ${first.start()}")
                println("answer second: ${second.start()}")
                println("The answer is: ${first.await() + second.await()}")
            }
            println("time: $time")
        }
    }

    @Test
    fun testAsync() {
        runBlocking {
            println("${testCoroutineScope()}")
        }
    }

    private suspend fun testCoroutineScope() =
        coroutineScope {
            val one = async {
                try {
                    delay(Long.MAX_VALUE) // 模拟一个长时间的运算
                    42
                } finally {
                    println("First child was cancelled")
                }
            }
            val two = async<Int> {
                println("Second child throws an exception")
                throw ArithmeticException()
            }
            one.await() + two.await()
        }

    private suspend fun firstThing(): Int {
        delay(1000L)
        println("first")
        return 20
    }


    private suspend fun secondThing(): Int {
        delay(1000L)
        println("second")
        return 30
    }


    @Test
    fun testCoroutineDispatcher() {
        runBlocking {
            launch {
                // 运行在父协程的上下文中，即 runBlocking 主协程
                println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.IO) {
                // IO调度器
                println("IO                    : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Unconfined) {
                // 不受限的——将工作在主线程中
                println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Default) {
                // 将会获取默认调度器
                println("Default               : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(newSingleThreadContext("MyOwnThread")) {
                // 将使它获得一个新的线程
                println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
//            }
                launch(Dispatchers.Unconfined) {
                    // 非受限的——将和主线程一起工作
                    println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
                    delay(500)
                    println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
                }
                launch {
                    // 父协程的上下文，主 runBlocking 协程
                    println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
                    delay(1000)
                    println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
                }
            }
        }
    }


    @Test
    fun testCoroutineLoop() {
        runBlocking {
            val job1 = launch {
                printCoroutineContext()
                printMemory()
                val job2 = launch {
                    printCoroutineContext()
                    printMemory()
                }
                val job3 = GlobalScope.launch {
                    printCoroutineContext()
                    printMemory()
                    val job4 = launch {
                        printCoroutineContext()
                        printMemory()
                    }
                }
            }
            printCoroutineContext()
            delay(1000L * 5)
        }
    }

    private fun CoroutineScope.printCoroutineContext() {
        println("coroutine name :${this.coroutineContext}")
    }


    fun testScope() {
        val mainScope = MainScope()
    }
}