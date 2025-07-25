package com.alef.souqleader.ui.presentation.crmSystem


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.CommentsResponse
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.PostResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.data.remote.dto.TimelinePostResponse
import com.alef.souqleader.domain.AddCommentUseCase
import com.alef.souqleader.domain.AddPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CRMViewModel @Inject constructor(
    private val addCommentUseCase: AddCommentUseCase,
    private val addPostUseCase: AddPostUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _stateAddComment =
        MutableSharedFlow<StatusResponse>()
    val stateAddComment: MutableSharedFlow<StatusResponse>
        get() = _stateAddComment

    private val _stateDeletComment =
        MutableSharedFlow<StatusResponse>()
    val stateDeleteComment: MutableSharedFlow<StatusResponse>
        get() = _stateDeletComment

    private val _stateComments =
        MutableSharedFlow<CommentsResponse>()
    val stateComments: MutableSharedFlow<CommentsResponse>
        get() = _stateComments

    private val _post =
        MutableSharedFlow<TimelinePostResponse>()
    val post: MutableSharedFlow<TimelinePostResponse>
        get() = _post

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun timelinePost(
        id: String
    ) {
        viewModelScope.launch(job) {
            addPostUseCase.timelinePost(id).data?.let { _post.emit(it) }
        }
    }
    fun companyPost(
        id: String
    ) {
        viewModelScope.launch(job) {
            addPostUseCase.companyPost(id).data?.let { _post.emit(it) }
        }
    }

    fun addComment(comment: String, post_id: String) {
        viewModelScope.launch(job) {
            addCommentUseCase.addComment(comment, post_id).data?.let { _stateAddComment.emit(it) }
        }
    }
    fun addCompanyComment(comment: String, post_id: String) {
        viewModelScope.launch(job) {
            addCommentUseCase.addCompanyComment(comment, post_id).data?.let { _stateAddComment.emit(it) }
        }
    }

    fun deleteComment(
        id: String
    ) {
        viewModelScope.launch(job) {
            addPostUseCase.deleteComment(id).data?.let { _stateDeletComment.emit(it) }
        }
    }
    fun deleteCompanyComment(
        id: String
    ) {
        viewModelScope.launch(job) {
            addPostUseCase.deleteCompanyComment(id).data?.let { _stateDeletComment.emit(it) }
        }
    }


    fun getComments(
        id: String
    ) {
        viewModelScope.launch(job) {
            addPostUseCase.getComments(id).data?.let { _stateComments.emit(it) }
        }
    }
    fun getCompanyComment(
        id: String
    ) {
        viewModelScope.launch(job) {
            addPostUseCase.getCompanyComment(id).data?.let { _stateComments.emit(it) }
        }
    }

}