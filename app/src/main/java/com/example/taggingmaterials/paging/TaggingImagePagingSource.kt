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
            withContext(Dispatchers.Default) {
                taggedImageRepository.getTaggedImage(it)
            }
        } ?: emptyList()

        var nextKey: Int? = if (paramsKey == null) {
            1
        } else {
            paramsKey + 10
        }
        if (response.lastOrNull() == null) nextKey = null

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