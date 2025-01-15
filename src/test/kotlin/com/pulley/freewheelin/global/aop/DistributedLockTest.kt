package com.pulley.freewheelin.global.aop

import com.pulley.freewheelin.global.annotation.DistributedLock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Service
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@ExtendWith(SpringExtension::class)
@SpringBootTest
class DistributedLockTest {

    @Autowired
    private lateinit var exampleService: ExampleService

    @Test
    @DisplayName("분산 락 테스트 : 10개의 스레드에서 +1 할 때, 실행 순서에 따라 카운터가 증가되어야 한다.")
    fun `test distributed lock`() {
        val numberOfThreads = 10
        val latch = CountDownLatch(numberOfThreads)
        val executorService = Executors.newFixedThreadPool(numberOfThreads)
        var countList = mutableListOf<Int>()
        var counter = 0

        for (i in 0 until numberOfThreads) {
            executorService.submit {
                try {
                    exampleService.process {
                        counter++
                        countList.add(counter)
                        println("Counter: $counter")
                    }
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        executorService.shutdown()

        // 최종 카운터 값이 numberOfThreads와 같아야 함
        assertEquals(numberOfThreads, counter)
        for (i in 0 until numberOfThreads) {
            assertEquals(i + 1, countList[i])
        }
    }
}

@Service
class ExampleService {

    @DistributedLock(lockKey = "exampleLock", keyParameter = "userId")
    fun process(task: () -> Unit) {
        task()
    }
}
