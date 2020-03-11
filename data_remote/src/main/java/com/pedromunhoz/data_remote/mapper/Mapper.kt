package com.pedromunhoz.data_remote.mapper

interface Mapper<in R, out M> {
    fun parse(remote: R): M
}