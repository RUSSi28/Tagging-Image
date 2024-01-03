package com.example.taggingmaterials.module

import android.content.Context
import androidx.room.Room
import com.example.taggingmaterials.data.TaggedImageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaggedImageDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, TaggedImageDatabase::class.java, "taggedImage_db").build()

    @Singleton
    @Provides
    fun provideDao(db: TaggedImageDatabase) = db.taggedImageDao()
}