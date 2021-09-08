package com.example.rickandmorty.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
