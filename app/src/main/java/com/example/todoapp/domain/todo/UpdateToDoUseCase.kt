package com.example.todoapp.domain.todo

import com.example.todoapp.data.entity.ToDoEntity
import com.example.todoapp.data.repository.ToDoRepository
import com.example.todoapp.domain.UseCase

internal class UpdateToDoUseCase(
    private val toDoRepository: ToDoRepository
) : UseCase{

    suspend operator fun invoke(toDoEntity: ToDoEntity) {
        toDoRepository.updateToDoItem(toDoEntity)
    }
}