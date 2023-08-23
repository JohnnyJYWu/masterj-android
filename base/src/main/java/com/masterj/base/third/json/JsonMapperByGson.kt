package com.masterj.base.third.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.masterj.base.data.DataWithExpiration
import com.masterj.base.log.L
import org.json.JSONObject

object JsonMapperByGson : JsonMapperInterface {

    private val DESERIALIZER_BUILDER: GsonBuilder = GsonBuilder()
    var deserializer: Gson = DESERIALIZER_BUILDER.create()
        private set

    private val SERIALIZER_BUILDER: GsonBuilder = GsonBuilder()
    var serializer: Gson = SERIALIZER_BUILDER.create()
        private set

    @Throws(JsonException::class)
    override fun <T : IJsonable> parseJsonObject(json: JSONObject?, clazz: Class<T>): T? {
        if (json == null) return null
        return parseJsonObject(json.toString(), clazz)
    }

    @Throws(JsonException::class)
    override fun <T : IJsonable> parseJsonObject(json: String?, clazz: Class<T>): T? {
        try {
            if (json == null) return null
            return deserializer.fromJson(json, clazz)
        } catch (e: Throwable) {
            throw JsonException("json=$json", e)
        }
    }

    @Throws(JsonException::class)
    override fun <T, R> parseJsonObject(json: String?, clazzT: Class<T>, clazzR: Class<R>): T? {
        try {
            if (json == null) return null
            val typeToken = TypeToken.getParameterized(clazzT, clazzR)
            return deserializer.fromJson(json, typeToken.type)
        } catch (e: Throwable) {
            throw JsonException("json=$json", e)
        }
    }

    override fun <T : IJsonable> toJson(obj: T?, clazz: Class<T>): String? {
        return try {
            serializer.toJson(obj)
        } catch (e: Throwable) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    override fun <T, R> toJson(obj: T?, clazzT: Class<T>, clazzR: Class<R>): String? {
        return try {
            val typeToken = TypeToken.getParameterized(clazzT, clazzR)
            serializer.toJson(obj, typeToken.type)
        } catch (e: Throwable) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    override fun <T> arrayToJson(array: Array<T>?): String? {
        return try {
            serializer.toJson(array)
        } catch (e: Throwable) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    override fun <T> jsonToArray(json: String?, clazz: Class<T>): T? {
        return try {
            deserializer.fromJson(json, clazz)
        } catch (e: Exception) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    override fun <T> listToJson(list: List<T>?, clazz: Class<T>): String? {
        return try {
            val typeToken = TypeToken.getParameterized(List::class.java, clazz.getObjectClassType())
            serializer.toJson(list, typeToken.type)
        } catch (e: Exception) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    override fun <T> jsonToList(json: String?, clazz: Class<T>): List<T>? {
        return try {
            val typeToken = TypeToken.getParameterized(List::class.java, clazz.getObjectClassType())
            deserializer.fromJson(json, typeToken.type)
        } catch (e: Exception) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    override fun <K, V> mapToJson(map: Map<K, V>?, clazzK: Class<K>, clazzV: Class<V>): String? {
        return try {
            val typeToken = TypeToken.getParameterized(Map::class.java, clazzK.getObjectClassType(), clazzV.getObjectClassType())
            serializer.toJson(map, typeToken.type)
        } catch (e: Exception) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    override fun <K, V> jsonToMap(json: String?, clazzK: Class<K>, clazzV: Class<V>): Map<K, V>? {
        return try {
            val typeToken = TypeToken.getParameterized(Map::class.java, clazzK.getObjectClassType(), clazzV.getObjectClassType())
            deserializer.fromJson(json, typeToken.type)
        } catch (e: Exception) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    override fun writeValue(obj: Any?): String? {
        return try {
            serializer.toJson(obj)
        } catch (e: Exception) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    override fun <T> readValue(json: String?, clazz: Class<T>): T? {
        return try {
            deserializer.fromJson(json, clazz)
        } catch (e: Exception) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    override fun registerDeserializerAdapter(adapter: CustomDeserializerAdapter) {
        synchronized(
            this,
            object : () -> Unit {
                override fun invoke() {
                    if (adapter is GsonDeserializerAdapter<*>) {
                        DESERIALIZER_BUILDER.registerTypeAdapter(adapter.clazz, adapter.deserializer)
                        deserializer = DESERIALIZER_BUILDER.create()
                    } else {
                        throw RuntimeException("adapter must be GsonDeserializerAdapter")
                    }
                }
            }
        )
    }

    override fun registerSerializerAdapter(adapter: CustomSerializerAdapter) {
        synchronized(
            this,
            object : () -> Unit {
                override fun invoke() {
                    if (adapter is GsonSerializerAdapter<*>) {
                        SERIALIZER_BUILDER.registerTypeAdapter(adapter.clazz, adapter.serializer)
                        serializer = SERIALIZER_BUILDER.create()
                    } else {
                        throw RuntimeException("adapter must be GsonSerializerAdapter")
                    }
                }
            }
        )
    }

    //region 单独给JsonMapper使用的方法
    fun <T : IJsonable> toJson(obj: T?): String? {
        return try {
            serializer.toJson(obj)
        } catch (e: Throwable) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    fun <T> listToJson(list: List<T>?, type: TypeToken<List<T>>): String? {
        return try {
            serializer.toJson(list, type.type)
        } catch (e: Throwable) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    fun <T> jsonToList(json: String?, type: TypeToken<List<T>>): List<T>? {
        return try {
            deserializer.fromJson(json, type.type)
        } catch (e: Throwable) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    fun <K, V> mapToJson(map: Map<K, V>?, type: TypeToken<Map<K, V>>): String? {
        return try {
            serializer.toJson(map, type.type)
        } catch (e: Throwable) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    fun <K, V> jsonToMap(json: String?, type: TypeToken<Map<K, V>>): Map<K, V>? {
        return try {
            deserializer.fromJson(json, type.type)
        } catch (e: Throwable) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    fun <T> dataWithExpirationToJson(dataWrapper: DataWithExpiration<T>?, type: TypeToken<DataWithExpiration<T>>): String? {
        return try {
            deserializer.toJson(dataWrapper, type.type)
        } catch (e: Throwable) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    fun <T> jsonToDataWithExpiration(json: String?, type: TypeToken<DataWithExpiration<T>>): DataWithExpiration<T>? {
        return try {
            deserializer.fromJson(json, type.type)
        } catch (e: Throwable) {
            L.e(JsonMapperByGson::class.java, e)
            null
        }
    }

    @Throws(JsonException::class)
    fun <T : IJsonable> parseJsonObject(json: JsonElement?, clazz: Class<T>): T? {
        try {
            if (json == null) return null
            return deserializer.fromJson(json, clazz)
        } catch (e: Throwable) {
            throw JsonException("json=$json", e)
        }
    }
    //endregion
}
