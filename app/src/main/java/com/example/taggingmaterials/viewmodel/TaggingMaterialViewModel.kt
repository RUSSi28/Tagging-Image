package com.example.taggingmaterials.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taggingmaterials.data.TaggedImage
import com.example.taggingmaterials.data.TaggedImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaggingMaterialViewModel  @Inject constructor(
    private val taggedImageRepositoryImpl: TaggedImageRepository
): ViewModel() {
    private var inputText by mutableStateOf("")
    private var isSearchBarActive by mutableStateOf(false)

    var allTags = taggedImageRepositoryImpl.getAllTags()
    var queryTags by mutableStateOf<List<String>>(emptyList())
    var currentlyUsedImages = taggedImageRepositoryImpl.getAllTaggedImage()
    var searchedImages by mutableStateOf<List<TaggedImage>>(emptyList())

    var inputImageUri by mutableStateOf("")

    fun getTags() = taggedImageRepositoryImpl.getTag(inputText)
    fun queryTags() = taggedImageRepositoryImpl.getTag(inputText)

    //Text Fieldのための関数群
    fun getInText() : String{
        return inputText
    }
    fun changeInputText(input: String) {
        inputText = input
    }
    fun getIsSearchBarActive() : Boolean{
        return isSearchBarActive
    }
    fun changeIsSearchBarActive(bool: Boolean) {
        isSearchBarActive = bool
    }

    //URIの取得
    fun canGetUri() = inputImageUri != ""
    fun getQueryTags() {
        //getTagsの方がいい
        queryTags = taggedImageRepositoryImpl.getTag(inputText)
    }
    suspend fun getSearchedImages() {
        searchedImages = taggedImageRepositoryImpl.getAssignedTaggedImages(inputText)
    }

    //データ取得のための関数群
    suspend fun insertTaggedImage(taggedImage: TaggedImage) = taggedImageRepositoryImpl.insertTaggedImage(taggedImage)
    suspend fun deleteTaggedImage(taggedImage: TaggedImage) =
        taggedImageRepositoryImpl.deleteTaggedImage(taggedImage)
}