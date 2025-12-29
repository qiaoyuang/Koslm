package com.yuangqiao.koslm.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform