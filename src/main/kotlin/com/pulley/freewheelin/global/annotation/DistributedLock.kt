package com.pulley.freewheelin.global.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedLock(val lockKey: String, val keyParameter: String = "")