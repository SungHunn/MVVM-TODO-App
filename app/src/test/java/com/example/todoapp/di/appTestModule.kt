package com.example.todoapp.di

import android.widget.ListView
import com.example.todoapp.data.repository.TestToDoRepository
import com.example.todoapp.data.repository.ToDoRepository
import com.example.todoapp.domain.todo.*
import com.example.todoapp.presentation.detail.DetailMode
import com.example.todoapp.presentation.detail.DetailViewModel
import com.example.todoapp.presentation.list.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    //ViewModel
    viewModel { ListViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) ->
        DetailViewModel(
            detailMode,
            id,
            get(),
            get(),
            get(),
            get()
        )
    }

    //UseCase
    factory { GetToDoListUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoItemUseCase(get()) }
    factory { UpdateToDoUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }


    //Repository
    single<ToDoRepository> { TestToDoRepository() }
}