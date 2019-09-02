package com.example.thirdlibrary.util

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import java.lang.reflect.Field

/**
 * Created by zyx on 19-8-30.
 */

fun <T : Any> Observable<T>.fieldLoop(): Observable<Pair<String, String>> {
    return this.flatMapIterable(Function<T, Iterable<Field>> { it.javaClass.declaredFields.toMutableList() },
        BiFunction<T, Field, Pair<String, String>> { musicBean, field ->
            field.isAccessible = true
            return@BiFunction Pair(field.name, field.get(musicBean).toString())
        })
}