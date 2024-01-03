package com.example.taggingmaterials.viewmodel

import androidx.lifecycle.ViewModel
import com.example.taggingmaterials.data.TaggedImageRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaggingMaterialViewModel  @Inject constructor(
    private val taggedImageRepository: TaggedImageRepositoryImpl
): ViewModel(

) {
}