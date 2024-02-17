package com.example.taggingmaterials.data

import kotlinx.coroutines.flow.Flow

interface TaggedImageRepository {
    suspend fun insertTaggedImage(taggedImage: TaggedImage)
    suspend fun deleteTaggedImage(taggedImage: TaggedImage)
    fun getTaggedImage() : Flow<List<TaggedImage>>
}