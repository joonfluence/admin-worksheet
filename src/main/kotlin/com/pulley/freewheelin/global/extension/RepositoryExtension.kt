package com.pulley.freewheelin.global.extension

import org.springframework.data.repository.CrudRepository

fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(
    id: ID,
    e: Exception,
): T = findById(id!!).orElseThrow { e }
