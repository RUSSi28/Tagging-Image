package com.example.taggingmaterials.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaggedImageRepositoryImpl @Inject constructor(
    private val taggedImageDao: TaggedImageDao
): TaggedImageRepository {
    override suspend fun insertTaggedImage(taggedImage: TaggedImage)
        = taggedImageDao.insertTaggedImage(taggedImage)

    override suspend fun deleteTaggedImage(taggedImage: TaggedImage)
        = taggedImageDao.deleteTaggedImage(taggedImage)

    override fun getTaggedImage(): Flow<List<TaggedImage>>
        = taggedImageDao.getTaggedImages()
}