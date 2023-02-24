package com.example.todoapp.domain.todo

import com.example.todoapp.data.entity.ToDoEntity
import com.example.todoapp.data.repository.ToDoRepository
import com.example.todoapp.domain.UseCase

internal class DeleteToDoItemUseCase(
    private val toDoRepository: ToDoRepository
) : UseCase{

    suspend operator fun invoke(itemId : Long) {
         toDoRepository.deleteToDoItem(itemId)
    }
}