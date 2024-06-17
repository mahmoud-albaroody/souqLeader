package com.alef.souqleader.ui.presentation.timeline


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.Plan
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.domain.GetPostsUseCase
import com.alef.souqleader.domain.PaymentPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeLineViewModel @Inject constructor(
    val getPostsUseCase: GetPostsUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var statePosts by mutableStateOf(emptyList<Post>())

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getPosts() {
        viewModelScope.launch(job) {
            statePosts = getPostsUseCase.getPosts().data?.data!!
        }
    }

}