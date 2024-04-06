package com.example.taggingmaterials.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.taggingmaterials.data.TaggedImage
import com.example.taggingmaterials.data.TaggedImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaggingImagePagingSource @Inject constructor(
    private val taggedImageRepository: TaggedImageRepository
) : PagingSource<Long, TaggedImage>() {
    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, TaggedImage> {
        val paramsKey = params.key
        var response = paramsKey?.let {
            withContext(Dispatchers.IO) {
                taggedImageRepository.getTaggedImageAfter(it.toLong())
            }
        } ?: run {
            withContext(Dispatchers.IO) {
                taggedImageRepository.getTaggedImageFirst()
            }
        }
        val nextKey: Long? = response?.lastOrNull()?.timestamp


        if (response == null) {
            response = emptyList()
        }
        return try {
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, TaggedImage>): Long? {
        return null
    }
}