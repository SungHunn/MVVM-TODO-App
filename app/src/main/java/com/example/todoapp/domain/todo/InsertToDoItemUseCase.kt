package com.example.todoapp.domain.todo

import com.example.todoapp.data.entity.ToDoEntity
import com.example.todoapp.data.repository.ToDoRepository
import com.example.todoapp.domain.UseCase

internal class InsertToDoItemUseCase(
    private val toDoRepository: ToDoRepository
) : UseCase{

    suspend operator fun invoke(toDoItem : ToDoEntity) : Long{
        return toDoRepository.insertToDoItem(toDoItem)
    }
}