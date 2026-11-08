package com.koolunkle.gettingstarted

/** 실행 중인 플랫폼 정보. 각 플랫폼 소스 세트에서 actual로 구현합니다. */
interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
