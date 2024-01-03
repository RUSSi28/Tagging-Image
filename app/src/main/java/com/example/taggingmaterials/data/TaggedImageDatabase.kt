package com.example.taggingmaterials.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [TaggedImage::class], exportSchema = false)
abstract class TaggedImageDatabase : RoomDatabase(){
    abstract fun taggedImageDao() : TaggedImageDao
}