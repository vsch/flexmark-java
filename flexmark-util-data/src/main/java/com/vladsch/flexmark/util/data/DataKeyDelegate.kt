package com.vladsch.flexmark.util.data

import kotlin.reflect.KProperty

@JvmInline
value class DataKeyDelegate<T : Any>(private val key: DataKey<T>) {

    operator fun setValue(thisRef: MutableDataHolder, property: KProperty<*>, value: T) {
        key.set(thisRef, value)
    }

    operator fun getValue(thisRef: MutableDataHolder, property: KProperty<*>): T =
        key.get(thisRef)
}

fun <T : Any> key(key: DataKey<T>) = DataKeyDelegate(key)