package com.pedromunhoz.data_remote.model

import com.google.gson.annotations.SerializedName

data class TypeResponse(
    @SerializedName("type") val type: TypeItemResponse
)