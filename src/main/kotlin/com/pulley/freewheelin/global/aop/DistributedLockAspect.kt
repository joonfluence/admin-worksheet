package com.pulley.freewheelin.global.aop

import com.pulley.freewheelin.global.annotation.DistributedLock
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Aspect
@Component
class DistributedLockAspect(private val redissonClient: RedissonClient) {

    private val logger = LoggerFactory.getLogger(DistributedLockAspect::class.java)

    @Around("@annotation(distributedLock)")
    fun around(joinPoint: ProceedingJoinPoint, distributedLock: DistributedLock): Any? {
        val lockKey = if (distributedLock.keyParameter.isNotEmpty()) {
            val args = joinPoint.args
            val methodSignature = joinPoint.signature as MethodSignature
            val parameterNames = methodSignature.parameterNames
            val keyParamIndex = parameterNames.indexOf(distributedLock.keyParameter)
            if (keyParamIndex != -1) {
                "${distributedLock.lockKey}:${args[keyParamIndex]}"
            } else {
                distributedLock.lockKey
            }
        } else {
            distributedLock.lockKey
        }

        val lock: RLock = redissonClient.getLock(lockKey)
        return try {
            val isLocked: Boolean = lock.tryLock(500, 10000, TimeUnit.MILLISECONDS)
            if (isLocked) {
                try {
                    joinPoint.proceed()
                } finally {
                    lock.unlock()
                }
            } else {
                throw IllegalStateException("락을 획득 할 수 없습니다.")
            }
        } catch (e: Exception) {
            logger.error("락을 획득하는 과정에서 예외가 발생했습니다: ${e.message}", e)
            throw e
        }
    }
}
