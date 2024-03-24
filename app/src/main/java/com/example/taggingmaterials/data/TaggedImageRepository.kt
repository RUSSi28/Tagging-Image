package com.example.taggingmaterials.data

import kotlinx.coroutines.flow.Flow

interface TaggedImageRepository {
    suspend fun insertTaggedImage(taggedImage: TaggedImage)
    suspend fun deleteTaggedImage(taggedImage: TaggedImage)

    fun getAllTags() : Flow<List<String>>
    fun getTaggedImage(id: Int) : List<TaggedImage>

    fun getAssignedTaggedImages(tag: String) : List<TaggedImage>
    fun getTag(inputText: String) : List<String>
}