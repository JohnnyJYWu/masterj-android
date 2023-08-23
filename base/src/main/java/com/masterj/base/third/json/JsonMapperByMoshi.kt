package com.masterj.base.third.json

import com.masterj.base.log.L
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONObject

object JsonMapperByMoshi : JsonMapperInterface {

    private val DESERIALIZER_BUILDER: Moshi.Builder = Moshi.Builder()
    private val SERIALIZER_BUILDER: Moshi.Builder = Moshi.Builder()
    private var deserializerMoshi: Moshi = DESERIALIZER_BUILDER.addLast(KotlinJsonAdapterFactory()).build()

    private var serializerMoshi: Moshi = SERIALIZER_BUILDER.addLast(KotlinJsonAdapterFactory()).build()

    @Throws(JsonException::class)
    override fun <T : IJsonable> parseJsonObject(json: JSONObject?, clazz: Class<T>): T? {
        if (json == null) return null
        return parseJsonObject(json.toString(), clazz)
    }

    @Throws(JsonException::class)
    override fun <T : IJsonable> parseJsonObject(json: String?, clazz: Class<T>): T? {
        return try {
            if (json == null) return null
            val adapter = deserializerMoshi.adapter<T>(clazz).nullSafe()
            adapter.fromJson(json)
        } catch (e: Throwable) {
            throw JsonException("json=$json")
        }
    }

    @Throws(JsonException::class)
    override fun <T, R> parseJsonObject(json: String?, clazzT: Class<T>, clazzR: Class<R>): T? {
        try {
            if (json == null) return null
            val type = Types.newParameterizedType(clazzT, clazzR)
            val adapter = deserializerMoshi.adapter<T>(type).nullSafe()
            return adapter.fromJson(json)
        } catch (e: Throwable) {
            throw JsonException("json=$json")
        }
    }

    override fun <T : IJsonable> toJson(obj: T?, clazz: Class<T>): String? {
        return try {
            val adapter = serializerMoshi.adapter<T>(clazz).nullSafe()
            adapter.toJson(obj)
        } catch (e: Throwable) {
            L.e(JsonMapperByMoshi::class.java, e)
            null
        }
    }

    override fun <T, R> toJson(obj: T?, clazzT: Class<T>, clazzR: Class<R>): String? {
        return try {
            val type = Types.newParameterizedType(clazzT, clazzR)
            val adapter = serializerMoshi.adapter<T>(type)
            adapter.toJson(obj)
        } catch (e: Throwable) {
            L.e(JsonMapperByMoshi::class.java, e)
            null
        }
    }

    override fun <T> arrayToJson(array: Array<T>?): String? {
        return try {
            val adapter = serializerMoshi.adapter<Array<T>>(array!!::class.java).nullSafe()
            adapter.toJson(array)
        } catch (e: Throwable) {
            L.e(JsonMapperByMoshi::class.java, e)
            null
        }
    }

    override fun <T> jsonToArray(json: String?, clazz: Class<T>): T? {
        return try {
            val adapter = deserializerMoshi.adapter<T>(clazz).nullSafe()
            adapter.fromJson(json)
        } catch (e: Throwable) {
            L.e(JsonMapperByMoshi::class.java, e)
            null
        }
    }

    override fun <T> listToJson(list: List<T>?, clazz: Class<T>): String? {
        return try {
            val type = Types.newParameterizedType(List::class.java, clazz.getObjectClassType())
            val adapter = serializerMoshi.adapter<List<T>>(type).nullSafe()
            adapter.toJson(list)
        } catch (e: Throwable) {
            L.e(JsonMapperByMoshi::class.java, e)
            null
        }
    }

    override fun <T> jsonToList(json: String?, clazz: Class<T>): List<T>? {
        return try {
            val type = Types.newParameterizedType(List::class.java, clazz.getObjectClassType())
            val adapter = deserializerMoshi.adapter<List<T>>(type).nullSafe()
            return adapter.fromJson(json)
        } catch (e: Throwable) {
            L.e(JsonMapperByMoshi::class.java, e)
            null
        }
    }

    override fun <K, V> mapToJson(map: Map<K, V>?, clazzK: Class<K>, clazzV: Class<V>): String? {
        return try {
            val type = Types.newParameterizedType(Map::class.java, clazzK.getObjectClassType(), clazzV.getObjectClassType())
            val adapter = serializerMoshi.adapter<Map<K, V>>(type).nullSafe()
            adapter.toJson(map)
        } catch (e: Throwable) {
            L.e(JsonMapperByMoshi::class.java, e)
            null
        }
    }

    override fun <K, V> jsonToMap(json: String?, clazzK: Class<K>, clazzV: Class<V>): Map<K, V>? {
        return try {
            val type = Types.newParameterizedType(Map::class.java, clazzK.getObjectClassType(), clazzV.getObjectClassType())
            val adapter = deserializerMoshi.adapter<Map<K, V>>(type).nullSafe()
            adapter.fromJson(json)
        } catch (e: Throwable) {
            L.e(JsonMapperByMoshi::class.java, e)
            null
        }
    }

    override fun writeValue(obj: Any?): String? {
        return try {
            val adapter = serializerMoshi.adapter<Any>(obj!!::class.java).nullSafe()
            return adapter.toJson(obj)
        } catch (e: Throwable) {
            L.e(JsonMapperByMoshi::class.java, e)
            null
        }
    }

    override fun <T> readValue(json: String?, clazz: Class<T>): T? {
        return try {
            val adapter = deserializerMoshi.adapter<T>(clazz).nullSafe()
            adapter.fromJson(json)
        } catch (e: Throwable) {
            L.e(JsonMapperByMoshi::class.java, e)
            null
        }
    }

    override fun registerDeserializerAdapter(adapterCustom: CustomDeserializerAdapter) {
        synchronized(
            this,
            object : () -> Unit {
                override fun invoke() {
                    if (adapterCustom is MoshiAdapter) {
                        deserializerMoshi = DESERIALIZER_BUILDER.add(adapterCustom).build()
                    } else {
                        throw RuntimeException("adapter must be MoshiAdapter")
                    }
                }
            }
        )
    }

    override fun registerSerializerAdapter(adapterCustom: CustomSerializerAdapter) {
        synchronized(
            this,
            object : () -> Unit {
                override fun invoke() {
                    if (adapterCustom is MoshiAdapter) {
                        serializerMoshi = SERIALIZER_BUILDER.add(adapterCustom).build()
                    } else {
                        throw RuntimeException("adapter must be MoshiAdapter")
                    }
                }
            }
        )
    }

    // 既注册序列化也注册反序列化
    fun registerAdapter(adapter: MoshiAdapter) {
        registerDeserializerAdapter(adapter)
        registerSerializerAdapter(adapter)
    }
}
