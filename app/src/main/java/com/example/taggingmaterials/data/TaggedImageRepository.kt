package com.example.taggingmaterials.data

import kotlinx.coroutines.flow.Flow

interface TaggedImageRepository {
    suspend fun insertTaggedImage(taggedImage: TaggedImage)
    suspend fun deleteTaggedImage(taggedImage: TaggedImage)

    suspend fun getAssignedTaggedImages(tag: String) : List<TaggedImage>
    fun getAllTags() : Flow<List<String>>

    fun getTaggedImageFirst() : List<TaggedImage>?
    fun getTaggedImageAfter(timeStamp: Long) : List<TaggedImage>?

    fun getTag(inputText: String) : List<String>
}