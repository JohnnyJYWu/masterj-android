package com.masterj.base.third.json

import org.json.JSONObject

interface JsonMapperInterface {

    @Throws(JsonException::class)
    fun <T : IJsonable> parseJsonObject(json: JSONObject?, clazz: Class<T>): T?

    @Throws(JsonException::class)
    fun <T : IJsonable> parseJsonObject(json: String?, clazz: Class<T>): T?

    fun <T : IJsonable> toJson(obj: T?, clazz: Class<T>): String?

    fun <T> arrayToJson(array: Array<T>?): String?

    fun <T> jsonToArray(json: String?, clazz: Class<T>): T?

    fun <T> listToJson(list: List<T>?, clazz: Class<T>): String?

    fun <T> jsonToList(json: String?, clazz: Class<T>): List<T>?

    fun <K, V> mapToJson(map: Map<K, V>?, clazzK: Class<K>, clazzV: Class<V>): String?

    fun <K, V> jsonToMap(json: String?, clazzK: Class<K>, clazzV: Class<V>): Map<K, V>?

    fun writeValue(obj: Any?): String?

    fun <T> readValue(json: String?, clazz: Class<T>): T?

    @Throws(JsonException::class)
    fun <T, R> parseJsonObject(json: String?, clazzT: Class<T>, clazzR: Class<R>): T? // 用于范型解析

    fun <T, R> toJson(obj: T?, clazzT: Class<T>, clazzR: Class<R>): String? // 用于范型解析

    fun registerDeserializerAdapter(adapterCustom: CustomDeserializerAdapter)

    fun registerSerializerAdapter(adapterCustom: CustomSerializerAdapter)
}
