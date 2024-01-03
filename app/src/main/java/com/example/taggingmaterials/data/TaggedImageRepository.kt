package com.example.taggingmaterials.data

import com.example.taggingmaterials.viewmodel.TaggingMaterialViewModel
import javax.inject.Inject

class TaggedImageRepository @Inject constructor(
    taggedImageDao: TaggedImageDao
): TaggedImageRepositoryImpl {

}