package com.simi.shelfpal

actual interface ILogger {
    actual fun log(s: String)
}

class AndroidLogger : ILogger {
    override fun log(s: String) {
        println("Android logging: $s")
    }
}