package com.simi.shelfpal.notification

interface BackgroundScheduler {
    fun startScheduler()
    fun cancelScheduler()
}