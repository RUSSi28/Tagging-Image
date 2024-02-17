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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaggingMaterialViewModel  @Inject constructor(
    private val taggedImageRepositoryImpl: TaggedImageRepository
): ViewModel() {
    private var inputText by mutableStateOf("")
    private var isSearchBarActive by mutableStateOf(false)
    private var wordList = taggedImageRepositoryImpl
    var currentlyUsedImages = taggedImageRepositoryImpl.getTaggedImage()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    var focusedImage: TaggedImage? = null

    var inputImageUri by mutableStateOf("")

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

    //データ取得のための関数群
    suspend fun insertTaggedImage(taggedImage: TaggedImage) = taggedImageRepositoryImpl.insertTaggedImage(taggedImage)
    fun deleteTaggedImage(taggedImage: TaggedImage) = viewModelScope.launch {
        withContext(Dispatchers.Default) {
            taggedImageRepositoryImpl.deleteTaggedImage(taggedImage)
        }
    }
}