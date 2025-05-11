package com.alef.souqleader.ui.presentation.timeline


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AddLikeResponse
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.Plan
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.data.remote.dto.PostData
import com.alef.souqleader.data.remote.dto.PostResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.domain.AddLikeUseCase
import com.alef.souqleader.domain.AddPostUseCase
import com.alef.souqleader.domain.GetPostsUseCase
import com.alef.souqleader.domain.PaymentPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
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


    private val _addPosts = MutableLiveData<StatusResponse>()
    val addPosts: LiveData<StatusResponse> = _addPosts

    private val _addLike = MutableLiveData<AddLikeResponse>()
    val addLike: LiveData<AddLikeResponse> = _addLike

    private val _statePosts =
        MutableSharedFlow<Resource<PostResponse>>(replay = 1)
    val statePosts: MutableSharedFlow<Resource<PostResponse>>
        get() = _statePosts
    var page by mutableIntStateOf(1)
    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getPosts(page: Int) {
        viewModelScope.launch(job) {
            getPostsUseCase.getPosts(page).catch {
                Log.e("dddddd", it.toString())
            }
                .onStart {
                    _statePosts.emit(Resource.Loading())
                }.buffer().collect {

                    _statePosts.emit(it)
                }
        }
    }

    fun addPost(
        post: RequestBody,
        images: ArrayList<MultipartBody.Part>?
    ) {
        viewModelScope.launch(job) {
            _addPosts.value = addPostUseCase.addPost(post, images).data!!
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