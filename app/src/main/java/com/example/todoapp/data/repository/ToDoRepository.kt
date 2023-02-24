package com.example.todoapp.data.repository

import com.example.todoapp.data.entity.ToDoEntity

interface ToDoRepository {

    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun insertToDoList(toDoList : List<ToDoEntity>)

    suspend fun insertToDoItem(toDoItem : ToDoEntity) : Long

    suspend fun updateToDoItem(toDoItem : ToDoEntity)

    suspend fun getToDoItem(itemId: Long): ToDoEntity?

    suspend fun deleteAll()

    suspend fun deleteToDoItem(id : Long)
}