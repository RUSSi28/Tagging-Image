package com.example.taggingmaterials.viewmodel

import android.util.Log
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaggingMaterialViewModel @Inject constructor(
    private val taggedImageRepository: TaggedImageRepository
) : ViewModel() {
    var inputText by mutableStateOf("")
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
        viewModelScope.launch {
            try {
                searchedImages = taggedImageRepository.getAssignedTaggedImages(inputText)
            }catch (e: Exception) {
                Log.e("ViewModel", "getSearchedImages: $e", )
            }
        }
    }

    //データ取得のための関数群
    fun insertTaggedImage(taggedImage: TaggedImage) {
        viewModelScope.launch {
            try {
                taggedImageRepository.insertTaggedImage(taggedImage)
            } catch (e: Exception) {
                Log.e("ViewModel", "insertTaggedImage: $e")
            }
        }
    }

    fun deleteTaggedImage(taggedImage: TaggedImage) {
        viewModelScope.launch {
            try {
                taggedImageRepository.deleteTaggedImage(taggedImage)
            } catch (e: Exception) {
                Log.d("ViewModel", "deleteTaggedImage: $e")
            }
        }
    }

    fun updateTaggedImage(taggedImage: TaggedImage) {
        viewModelScope.launch {
            try {
                taggedImageRepository.updateTaggedImage(taggedImage)
            }catch (e: Exception) {
                Log.e("ViewModel", "updateTaggedImage: $e", )
            }
        }
    }

}


