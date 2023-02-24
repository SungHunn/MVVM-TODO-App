package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.local.db.ToDoDatabase
import com.example.todoapp.data.repository.DefaultToDoRepository
import com.example.todoapp.data.repository.ToDoRepository
import com.example.todoapp.domain.todo.*
import com.example.todoapp.domain.todo.DeleteAllToDoItemUseCase
import com.example.todoapp.domain.todo.DeleteToDoItemUseCase
import com.example.todoapp.domain.todo.GetToDoItemUseCase
import com.example.todoapp.domain.todo.GetToDoListUseCase
import com.example.todoapp.domain.todo.InsertToDoItemUseCase
import com.example.todoapp.domain.todo.InsertToDoListUseCase
import com.example.todoapp.domain.todo.UpdateToDoUseCase
import com.example.todoapp.presentation.detail.DetailMode
import com.example.todoapp.presentation.detail.DetailViewModel
import com.example.todoapp.presentation.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }

    factory { GetToDoListUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { UpdateToDoUseCase(get()) }

    single<ToDoRepository> { DefaultToDoRepository(get(), get()) }

    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }

    viewModel { ListViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) -> DetailViewModel(detailMode, id, get(), get(), get(), get()) }

}

internal fun provideDB(context: Context): ToDoDatabase =
    Room.databaseBuilder(context, ToDoDatabase::class.java, ToDoDatabase.DB_NAME).build()

internal fun provideToDoDao(database: ToDoDatabase) = database.toDoDao()