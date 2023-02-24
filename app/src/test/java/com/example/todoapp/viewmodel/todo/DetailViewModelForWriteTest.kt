package com.example.todoapp.viewmodel.todo

import com.example.todoapp.data.entity.ToDoEntity
import com.example.todoapp.domain.todo.InsertToDoItemUseCase
import com.example.todoapp.presentation.detail.DetailMode
import com.example.todoapp.presentation.detail.DetailViewModel
import com.example.todoapp.presentation.detail.ToDoDetailState
import com.example.todoapp.presentation.list.ListViewModel
import com.example.todoapp.presentation.list.ToDoListState
import com.example.todoapp.viewmodel.ViewModelTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

/* DetailViewModel을 테스트 하기 위한 Unit Test

2. test viewModel fetch
3. test insert


*/

internal class DetailViewModelForWriteTest: ViewModelTest() {

    private val id = 0L

    private val detailViewModel by inject<DetailViewModel> { parametersOf(DetailMode.WRITE, id) }
    private val listViewModel by inject<ListViewModel>()


    private val todo = ToDoEntity(
        id = id,
        title = "title $id",
        description = "description $id",
        hasCompleted = false
    )

    @Test
    fun `test viewModel fetch`() = runTest {
        val testObservable = detailViewModel.toDoDetailLiveData.test()

        detailViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Write
            )
        )
    }

    @Test
    fun `test insert todo`() = runTest {
        val detailTestObservable = detailViewModel.toDoDetailLiveData.test()
        val listTestObservable = listViewModel.todoListLiveData.test()

        detailViewModel.writeToDo(
            title = todo.title,
            description = todo.description
        )

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )

        assert(detailViewModel.detailMode == DetailMode.DETAIL)
        assert(detailViewModel.id == id)

        listViewModel.fetchData()
        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(listOf(todo))
            )
        )
    }
}