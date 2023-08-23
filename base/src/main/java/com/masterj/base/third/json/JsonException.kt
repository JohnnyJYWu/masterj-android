package com.masterj.base.third.json

import java.io.IOException

class JsonException : IOException {
    constructor() : super()
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(detailMessage: String?) : super(detailMessage)
    constructor(cause: Throwable?) : super(cause)
}
