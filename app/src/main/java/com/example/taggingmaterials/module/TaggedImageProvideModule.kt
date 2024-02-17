package com.example.taggingmaterials.module

import com.example.taggingmaterials.data.TaggedImageDao
import com.example.taggingmaterials.data.TaggedImageRepository
import com.example.taggingmaterials.data.TaggedImageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TaggedImageProvideModule {

    @Provides
    fun provideTaggedImageRepository(
        taggedImageDao: TaggedImageDao
    ) : TaggedImageRepository {
        return TaggedImageRepositoryImpl(taggedImageDao)
    }
}