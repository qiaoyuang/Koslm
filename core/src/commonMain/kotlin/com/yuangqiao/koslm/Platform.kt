package com.yuangqiao.koslm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
