package com.pixelprodukt.healthapprestbackend

import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

// https://stackoverflow.com/questions/54087977/mockito-when-clause-not-working-in-kotlin
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

// To avoid having to use backticks for "when"
fun <T> whenever(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)