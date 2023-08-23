package com.masterj.base.third.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

/**
 * moshi序列化适配器
 * 需要ToJson注解序列化
 * @ToJson：
 * <any access modifier> R toJson(T value) throws <any>;
 * <any access modifier> void toJson(JsonWriter writer, T value) throws <any>;
 * <any access modifier> void toJson(JsonWriter writer, T value, JsonAdapter<any> delegate, <any more delegates>) throws <any>;
 * 需要FromJson注解反序列化
 * @FromJson：
 * <any access modifier> R fromJson(T value) throws <any>;
 * <any access modifier> R fromJson(JsonReader jsonReader) throws <any>;
 * <any access modifier> R fromJson(JsonReader jsonReader, JsonAdapter<any> delegate, <any more delegates>) throws <any>;
 **/
abstract class MoshiAdapter : CustomSerializerAdapter, CustomDeserializerAdapter {
    init {
        javaClass.declaredMethods.firstOrNull { it.isAnnotationPresent(FromJson::class.java) }
            ?: throw RuntimeException("need annotation FromJson")
        javaClass.declaredMethods.find { it.isAnnotationPresent(ToJson::class.java) }
            ?: throw RuntimeException("need annotation ToJson")
    }
}
