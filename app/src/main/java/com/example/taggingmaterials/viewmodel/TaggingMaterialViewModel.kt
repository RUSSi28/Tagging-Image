package com.example.taggingmaterials.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.taggingmaterials.data.TaggedImage
import com.example.taggingmaterials.data.TaggedImageRepository
import com.example.taggingmaterials.paging.TaggingImagePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaggingMaterialViewModel @Inject constructor(
    private val taggedImageRepository: TaggedImageRepository
) : ViewModel() {
    private var inputText by mutableStateOf("")
    private var isSearchBarActive by mutableStateOf(false)

    var allTags = taggedImageRepository.getAllTags()
    var queryTags by mutableStateOf<List<String>>(emptyList())
    var currentlyUsedImages = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { TaggingImagePagingSource(taggedImageRepository) }
        )
            .flow
            .cachedIn(viewModelScope)
    var searchedImages by mutableStateOf<List<TaggedImage>>(emptyList())

    var inputImageUri by mutableStateOf("")

    fun getTags() = taggedImageRepository.getTag(inputText)
    fun queryTags() = taggedImageRepository.getTag(inputText)

    //Text Fieldのための関数群
    fun getInText(): String {
        return inputText
    }

    fun changeInputText(input: String) {
        inputText = input
    }

    fun getIsSearchBarActive(): Boolean {
        return isSearchBarActive
    }

    fun changeIsSearchBarActive(bool: Boolean) {
        isSearchBarActive = bool
    }

    //URIの取得
    fun canGetUri() = inputImageUri != ""
    fun getQueryTags() {
        //getTagsの方がいい
        queryTags = taggedImageRepository.getTag(inputText)
    }

    fun getSearchedImages() {
        searchedImages = taggedImageRepository.getAssignedTaggedImages(inputText)
    }

    //データ取得のための関数群
    suspend fun insertTaggedImage(taggedImage: TaggedImage) =
        taggedImageRepository.insertTaggedImage(taggedImage)

    suspend fun deleteTaggedImage(taggedImage: TaggedImage) = viewModelScope.launch {
        withContext(Dispatchers.Default) {
            taggedImageRepository.deleteTaggedImage(taggedImage)
        }
    }
}