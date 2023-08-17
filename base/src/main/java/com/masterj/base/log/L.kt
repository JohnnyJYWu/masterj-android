package com.masterj.base.log

import android.util.Log

interface LCallback {
    fun e(tag: Any, msg: Any?, e: Throwable?) {}
    fun w(tag: Any, msg: Any?, e: Throwable?) {}
    fun i(tag: Any, msg: Any?, e: Throwable?) {}
    fun d(tag: Any, msg: Any?, e: Throwable?) {}
    fun v(tag: Any, msg: Any?, e: Throwable?) {}
}

object L {
    private const val NULL_STRING = "null"
    var isDebug = true
    var callback: LCallback? = null

    @JvmStatic
    fun e(tag: Any, msg: Any?) {
        e(tag, msg, null)
    }

    @JvmStatic
    fun e(tag: Any, e: Throwable?) {
        e(tag, null, e)
    }

    @JvmStatic
    fun e(tag: Any, msg: Any?, e: Throwable?) {
        if (e != null) {
            Log.e(fullTag(tag), msg?.toString() ?: NULL_STRING, e)
        } else {
            Log.e(fullTag(tag), msg?.toString() ?: NULL_STRING)
        }
        callback?.e(tag, msg, e)
    }

    @JvmStatic
    fun w(tag: Any, msg: Any?) {
        w(tag, msg, null)
    }

    @JvmStatic
    fun w(tag: Any, e: Throwable?) {
        w(tag, null, e)
    }

    @JvmStatic
    fun w(tag: Any, msg: Any?, e: Throwable?) {
        if (e != null) {
            Log.w(fullTag(tag), msg?.toString() ?: NULL_STRING, e)
        } else {
            Log.w(fullTag(tag), msg?.toString() ?: NULL_STRING)
        }
        callback?.w(tag, msg, e)
    }

    @JvmStatic
    fun i(tag: Any, msg: Any?) {
        i(tag, msg, null)
    }

    @JvmStatic
    fun i(tag: Any, e: Throwable?) {
        i(tag, null, e)
    }

    @JvmStatic
    fun i(tag: Any, msg: Any?, e: Throwable? = null) {
        if (isDebug) {
            if (e != null) {
                Log.i(fullTag(tag), msg?.toString() ?: NULL_STRING, e)
            } else {
                Log.i(fullTag(tag), msg?.toString() ?: NULL_STRING)
            }
        }
        callback?.i(tag, msg, e)
    }

    @JvmStatic
    fun d(tag: Any, e: Throwable?) {
        d(tag, null, e)
    }

    @JvmStatic
    fun d(tag: Any, msg: Any?) {
        d(tag, msg, null)
    }

    @JvmStatic
    fun d(tag: Any, msg: Any?, e: Throwable?) {
        if (isDebug) {
            if (e != null) {
                Log.d(fullTag(tag), msg?.toString() ?: NULL_STRING, e)
            } else {
                Log.d(fullTag(tag), msg?.toString() ?: NULL_STRING)
            }
        }
        callback?.d(tag, msg, e)
    }

    @JvmStatic
    fun v(tag: Any, e: Throwable?) {
        v(tag, null, e)
    }

    @JvmStatic
    fun v(tag: Any, msg: Any?) {
        v(tag, msg, null)
    }

    @JvmStatic
    fun v(tag: Any, msg: Any?, e: Throwable?) {
        if (isDebug) {
            if (e != null) {
                Log.v(fullTag(tag), msg?.toString() ?: NULL_STRING, e)
            } else {
                Log.v(fullTag(tag), msg?.toString() ?: NULL_STRING)
            }
        }
        callback?.v(tag, msg, e)
    }

    private fun fullTag(tag: Any): String {
        val contextName = when (tag) {
            is String -> tag
            is Class<*> -> tag.simpleName
            else -> tag.javaClass.simpleName
        }
        return "masterj-log-$contextName"
    }
}
