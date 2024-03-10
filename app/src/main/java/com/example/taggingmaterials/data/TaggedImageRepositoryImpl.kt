package com.example.taggingmaterials.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaggedImageRepositoryImpl @Inject constructor(
    private val taggedImageDao: TaggedImageDao
) : TaggedImageRepository {
    override suspend fun insertTaggedImage(taggedImage: TaggedImage) =
        taggedImageDao.insertTaggedImage(taggedImage)

    override suspend fun deleteTaggedImage(taggedImage: TaggedImage) =
        taggedImageDao.deleteTaggedImage(taggedImage)

    override fun getAllTags(): Flow<List<String>> = taggedImageDao.getAllTags()
    override fun getAllTaggedImage(): Flow<List<TaggedImage>> = taggedImageDao.getAllTaggedImages()

    override fun getAssignedTaggedImages(tag: String): List<TaggedImage> {
        return taggedImageDao.getAssignedTaggedImages(tag)
    }

    override fun getTag(inputText: String): List<String> {
        val queryInput = "%${inputText}%"
        return taggedImageDao.getTag(queryInput)
    }
}