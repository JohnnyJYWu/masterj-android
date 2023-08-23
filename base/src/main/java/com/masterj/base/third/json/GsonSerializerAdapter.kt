package com.masterj.base.third.json

import com.google.gson.JsonSerializer

abstract class GsonSerializerAdapter<T>(val clazz: Class<T>, val serializer: JsonSerializer<T>) : CustomSerializerAdapter
