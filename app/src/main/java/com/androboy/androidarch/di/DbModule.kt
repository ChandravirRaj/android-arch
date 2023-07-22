package com.androboy.androidarch.di

import android.content.Context
import androidx.room.Room
import com.androboy.androidarch.utils.AppConstant
import com.androboy.androidarch.db.AppDatabase
import com.androboy.androidarch.db.entities.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {
    @Singleton
    @Provides
    fun provideRoomDbInstance(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppConstant.dbName)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase) = db.userDao()

    @Provides
    fun provideEntity() = User()

}