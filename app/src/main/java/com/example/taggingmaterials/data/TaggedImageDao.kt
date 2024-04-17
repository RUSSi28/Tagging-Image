package com.example.taggingmaterials.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaggedImageDao {
    //タグ付き画像のURIをinsertする用のメソッド
    @Insert
    suspend fun insertTaggedImage(taggedImage: TaggedImage)
    //タグ付き画像をdeleteする用のメソッド
    @Delete
    suspend fun deleteTaggedImage(taggedImage: TaggedImage)
    @Query("SELECT * from image WHERE tag1 LIKE :tag1")
    suspend fun getAssignedTaggedImages(tag1: String): List<TaggedImage>

    //タグ付きの画像を取得するためのメソッド
    @Query("SELECT * from image ORDER BY timestamp ASC Limit 10")
    fun getTaggedImageFirst(): List<TaggedImage>?
    @Query("SELECT * from image WHERE timeStamp > :timeStamp ORDER BY timestamp ASC Limit 10")
    fun getTaggedImagesAfter(timeStamp: Long): List<TaggedImage>?

    @Update
    suspend fun updateTaggedImage(image: TaggedImage)


    @Query("SELECT DISTINCT tag1 from image")
    fun getAllTags(): Flow<List<String>>

    //TODO: inputTextはinputText = "%${query}%"とする必要がある
    @Query("""SELECT DISTINCT tag1 from image
        WHERE tag1 LIKE :inputText
    """)
    fun getTag(inputText: String): List<String>
}