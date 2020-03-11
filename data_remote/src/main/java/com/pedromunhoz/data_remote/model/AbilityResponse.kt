package com.pedromunhoz.data_remote.model

import com.google.gson.annotations.SerializedName


data class AbilityResponse(
    @SerializedName("ability") val ability: AbilityItemResponse
)