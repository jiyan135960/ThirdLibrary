package com.example.thirdlibrary

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.UnicastSubject
import org.junit.After
import org.junit.Test

/**
 * Created by zyx on 19-7-22.
 */
class RxJavaTest {

    val emitElement = listOf("1", "2", "3", "4", "5", "6").iterator()

    @After
    fun verifyEmitElementIsEmpty() {
//        assert(!emitElement.hasNext())
    }

    @Test
    fun testUnicastSubject() {
        val unicastSubject = UnicastSubject.create<String>()
        unicastSubject.onNext("1")
        unicastSubject.onNext("2")
        unicastSubject.onNext("3")
        unicastSubject.subscribe { assert(emitElement.next() == it) }
        unicastSubject.onNext("4")
        unicastSubject.onNext("5")
        unicastSubject.onNext("6")
    }

    @Test
    fun testPublishSubject() {
        val publishSubject = PublishSubject.create<String>()
        var currentElement = ""
        publishSubject.doOnNext { currentElement = emitElement.next() }
        publishSubject.onNext("1")
        publishSubject.subscribe {
            println("first subscribe :$it")
            println("first subscribe :$currentElement")
//            assert(currentElement == it)
        }
        publishSubject.onNext("2")
        publishSubject.onNext("3")
        publishSubject.subscribe {
            println("first subscribe :$currentElement")
            println("second subscribe :$it")
//            assert(currentElement == it)
        }
        publishSubject.onNext("4")
        publishSubject.onNext("5")
        publishSubject.onNext("6")

    }

}