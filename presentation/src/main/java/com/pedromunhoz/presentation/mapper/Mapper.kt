package com.pedromunhoz.presentation.mapper

interface Mapper<D, P> {
    fun toDomain(presentation: P): D
    fun fromDomain(domain: D): P
}
