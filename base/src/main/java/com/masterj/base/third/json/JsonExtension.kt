package com.masterj.base.third.json

internal fun <T> Class<T>.getObjectClassType(): Class<*> {
    return when (this.name) {
        "boolean" -> Boolean::class.java
        "char" -> Character::class.java
        "byte" -> Byte::class.java
        "short" -> Short::class.java
        "int" -> Integer::class.java
        "float" -> Float::class.java
        "long" -> Long::class.java
        "double" -> Double::class.java
        "void" -> Void::class.java
        else -> this
    }
}
