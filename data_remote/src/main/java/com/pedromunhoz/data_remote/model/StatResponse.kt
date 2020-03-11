package com.pedromunhoz.data_remote.model

import com.google.gson.annotations.SerializedName

data class StatResponse(
    @SerializedName("stat") val stat: StatItemResponse,
    @SerializedName("base_stat") val baseStat: Int
)