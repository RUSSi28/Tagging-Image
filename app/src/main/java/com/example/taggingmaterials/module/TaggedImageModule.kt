package com.example.taggingmaterials.module

import com.example.taggingmaterials.data.TaggedImageRepository
import com.example.taggingmaterials.data.TaggedImageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class TaggedImageModule {

    @Binds
    abstract fun bindTaggedImageRepository(
        taggedImageRepositoryImpl: TaggedImageRepositoryImpl
    ) : TaggedImageRepository
}