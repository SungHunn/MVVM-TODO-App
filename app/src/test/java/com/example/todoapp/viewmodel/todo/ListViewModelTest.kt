package com.example.todoapp.viewmodel.todo

import com.example.todoapp.data.entity.ToDoEntity
import com.example.todoapp.domain.todo.GetToDoItemUseCase
import com.example.todoapp.domain.todo.InsertToDoListUseCase
import com.example.todoapp.presentation.list.ListViewModel
import com.example.todoapp.presentation.list.ToDoListState
import com.example.todoapp.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.core.inject


/*
1. initdata()
2. test viewModel fetch
3. test Item Update
4. test Item Delete All

 */

@ExperimentalCoroutinesApi
internal class ListViewModelTest : ViewModelTest() {

    private val viewModel: ListViewModel by inject()

    private val insertToDoListUseCase: InsertToDoListUseCase by inject()
    private val getToDoItemUseCase: GetToDoItemUseCase by inject()

    private val mockList = (0 until 10).map {
        ToDoEntity(
            id = it.toLong(),
            title = "title $it",
            description = "description $it",
            hasCompleted = false
        )
    }

    // 1. Insert 2. GetItem

    @Before
    fun init() {
        initData()
    }

    private fun initData() = runBlockingTest {
        insertToDoListUseCase(mockList)
    }

    // Test : 입력된 데이터를 불러와 검증
    @Test
    fun `test viewmodel fetch`(): Unit = runTest {
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(mockList)
            )
        )
    }


    //Test : 데이터를 업데이트 했을 때 반영 여부
    @Test
    fun `test Item Update` () : Unit = runTest {
        val todo = ToDoEntity(
            id = 1,
            title = "title 1",
            description = "description 1",
            hasCompleted = true
        )
        viewModel.updateEntity(todo)
        assert(getToDoItemUseCase(todo.id)?.hasCompleted ?: false == todo.hasCompleted )
    }

    //Test : 데이터 모두 삭제 체크
    @Test
    fun `test Item Delete All`(): Unit = runTest {
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.fetchData()
        viewModel.deleteAll()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(listOf())
            )
        )
    }
}