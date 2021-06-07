package net.eknm.eknmlogistics

import org.mockito.Mockito

fun <T> customAny(typeClass: Class<T>): T = Mockito.any<T>(typeClass)
