package com.masterj.base.data

import com.masterj.base.third.json.IJsonable
import com.masterj.base.third.json.JsonMapperByGson

open class BaseData : IJsonable, ICheckable {
    override fun writeJson(): String? {
        return JsonMapperByGson.toJson(this)
    }

    override fun isValid(): Boolean {
        return true
    }
}
