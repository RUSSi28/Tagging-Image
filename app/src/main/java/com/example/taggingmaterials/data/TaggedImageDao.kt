package com.example.taggingmaterials.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaggedImageDao {
    //タグ付き画像のURIをinsertする用のメソッド
    @Insert
    suspend fun insertTaggedImage(taggedImage: TaggedImage)
    //タグ付き画像をdeleteする用のメソッド
    @Delete
    suspend fun deleteTaggedImage(taggedImage: TaggedImage)
    //タグ付きの画像を取得するためのメソッド
    @Query("SELECT * from image")
    fun getTaggedImages(): Flow<List<TaggedImage>>
}