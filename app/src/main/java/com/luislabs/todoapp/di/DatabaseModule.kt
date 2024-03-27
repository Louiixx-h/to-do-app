package com.luislabs.todoapp.di

import android.content.Context
import androidx.room.Room
import com.luislabs.todoapp.data.ToDoDAO
import com.luislabs.todoapp.data.ToDoDatabase
import com.luislabs.todoapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providerDatabase(
        @ApplicationContext context: Context
    ): ToDoDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = ToDoDatabase::class.java,
            name = Constants.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providerDAO(database: ToDoDatabase): ToDoDAO {
        return database.toDoDAO()
    }
}