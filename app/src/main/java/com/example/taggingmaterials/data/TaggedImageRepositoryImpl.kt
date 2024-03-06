package com.example.taggingmaterials.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaggedImageRepositoryImpl @Inject constructor(
    private val taggedImageDao: TaggedImageDao
): TaggedImageRepository {
    override suspend fun insertTaggedImage(taggedImage: TaggedImage)
        = taggedImageDao.insertTaggedImage(taggedImage)

    override suspend fun deleteTaggedImage(taggedImage: TaggedImage)
        = taggedImageDao.deleteTaggedImage(taggedImage)

    override fun getTaggedImage(): Flow<List<TaggedImage>>
        = taggedImageDao.getTaggedImages().flowOn(Dispatchers.IO)

    override fun getTag(inputText: String): Flow<List<String>> {
        val queryInput = "%${inputText}%"
        return taggedImageDao.getTag(queryInput)
    }
}