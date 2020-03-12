package com.pedromunhoz.data_local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavoritePokemonEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val imgUrl: String
)