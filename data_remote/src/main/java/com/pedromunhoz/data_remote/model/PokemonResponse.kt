package com.pedromunhoz.data_remote.model

import com.google.gson.annotations.SerializedName


data class PokemonResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("abilities") val abilities: MutableList<AbilityResponse>,
    @SerializedName("stats") val stats: MutableList<StatResponse>,
    @SerializedName("sprites") val sprites: SpritesResponse,
    @SerializedName("base_experience") val baseExperience: Int,
    @SerializedName("types") val types: MutableList<TypeResponse>
)


