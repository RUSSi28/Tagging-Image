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
) : PagingSource<Int, TaggedImage>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TaggedImage> {
        val paramsKey = params.key
        val response = paramsKey?.let {
            withContext(Dispatchers.IO) {
                taggedImageRepository.getTaggedImage(it)
            }
        } ?: run {
            withContext(Dispatchers.IO) {
                taggedImageRepository.getTaggedImage(1)
            }
        }

        val nextKey: Int? = response.lastOrNull()?.let {
            it.id + 1
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

    override fun getRefreshKey(state: PagingState<Int, TaggedImage>): Int? {
        return null
    }
}