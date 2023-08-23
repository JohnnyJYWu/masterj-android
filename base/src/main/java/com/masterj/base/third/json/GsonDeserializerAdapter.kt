package com.masterj.base.third.json

import com.google.gson.JsonDeserializer

abstract class GsonDeserializerAdapter<T>(val clazz: Class<T>, val deserializer: JsonDeserializer<T>) : CustomDeserializerAdapter
