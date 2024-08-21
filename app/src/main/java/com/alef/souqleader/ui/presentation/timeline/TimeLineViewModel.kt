package com.alef.souqleader.ui.presentation.timeline


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.AddLikeResponse
import com.alef.souqleader.data.remote.dto.Plan
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.domain.AddLikeUseCase
import com.alef.souqleader.domain.AddPostUseCase
import com.alef.souqleader.domain.GetPostsUseCase
import com.alef.souqleader.domain.PaymentPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class TimeLineViewModel @Inject constructor(
    val getPostsUseCase: GetPostsUseCase,
    val addPostUseCase: AddPostUseCase,
    val addLikeUseCase: AddLikeUseCase
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

  //  var statePosts by mutableStateOf(emptyList<Post>())

    var addPosts by mutableStateOf(StatusResponse())

    //  var addLike by mutableStateOf(AddLikeResponse())

    private val _addLike = MutableLiveData<AddLikeResponse>()
    val addLike: LiveData<AddLikeResponse> = _addLike

    private val _statePosts = MutableLiveData<ArrayList<Post>?>()
    val statePosts: MutableLiveData<ArrayList<Post>?> = _statePosts

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getPosts() {
        viewModelScope.launch(job) {
            _statePosts.value = getPostsUseCase.getPosts().data?.data!!.data
        }
    }

    fun addPost(
        post: RequestBody,
        images: ArrayList<MultipartBody.Part>?
    ) {
        viewModelScope.launch(job) {
            addPosts = addPostUseCase.addPost(post, images).data!!
        }
    }

    fun addLike(
        like: String,
        postId: String
    ) {
        viewModelScope.launch(job) {
            _addLike.value = addLikeUseCase.addLike(like, postId).data!!
        }
    }

}