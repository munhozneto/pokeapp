package com.pedromunhoz.data_remote.model

import com.google.gson.annotations.SerializedName


data class PokemonSpecieResponse(
    @SerializedName("name") val name: String
)