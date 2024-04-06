package com.example.taggingmaterials.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("image")
data class TaggedImage(
    @PrimaryKey
    val timestamp: Long,
    @ColumnInfo
    val imageUri: String,
    @ColumnInfo
    val tag1: String = "",
    @ColumnInfo
    val tag2: String = "",
    @ColumnInfo
    val tag3: String = "",
)


