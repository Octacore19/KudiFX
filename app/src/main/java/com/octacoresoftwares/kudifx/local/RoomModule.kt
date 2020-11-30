package com.octacoresoftwares.kudifx.local

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context) = AppDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideRatesDao(database: AppDatabase) = database.ratesDao()
}