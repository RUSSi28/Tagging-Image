package com.example.taggingmaterials.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaggedImageDao {
//    //タグ付き画像のURIをinsertする用のメソッド
//    @Insert
//    abstract suspend fun insertTaggedImage()
//    //タグ付き画像をdeleteする用のメソッド
//    @Delete
//    abstract suspend fun deleteTaggedImage()
//    //タグ付きの画像を取得するためのメソッド
//    @Query("SELECT * from image")
//    abstract fun getTaggedImages(): List<Flow<TaggedImage>>
//
//    //新しくタグをinsertするメソッド
//    abstract suspend fun insertTag()
//    //タグだけをdeleteするためのメソッド
//    abstract suspend fun deleteTag()
//    //ついているタグを編集するメソッド
//    abstract suspend fun updateTag(): Flow<TaggedImage>
}