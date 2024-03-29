package com.example.taggingmaterials.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
class TaggedImageRepositoryImpl @Inject constructor(
    private val taggedImageDao: TaggedImageDao
) : TaggedImageRepository {
    override suspend fun insertTaggedImage(taggedImage: TaggedImage) =
        withContext(Dispatchers.IO) {
            taggedImageDao.insertTaggedImage(taggedImage)
        }

    override suspend fun deleteTaggedImage(taggedImage: TaggedImage) =
        withContext(Dispatchers.IO) {
            taggedImageDao.deleteTaggedImage(taggedImage)
        }

    override fun getAllTags(): Flow<List<String>> = taggedImageDao.getAllTags()
    override fun getAllTaggedImage(): Flow<List<TaggedImage>> = taggedImageDao.getAllTaggedImages()

    override suspend fun getAssignedTaggedImages(tag: String): List<TaggedImage> {
        return withContext(Dispatchers.IO) {
            taggedImageDao.getAssignedTaggedImages(tag)
        }
    }

    override fun getTag(inputText: String): List<String> {
        val queryInput = "%${inputText}%"
        return taggedImageDao.getTag(queryInput)
    }
}

