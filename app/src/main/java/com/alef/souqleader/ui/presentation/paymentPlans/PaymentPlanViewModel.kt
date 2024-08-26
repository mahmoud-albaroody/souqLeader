package com.alef.souqleader.ui.presentation.paymentPlans


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.UsersItem
import com.alef.souqleader.data.remote.dto.Plan
import com.alef.souqleader.domain.AllUsersUseCase
import com.alef.souqleader.domain.PaymentPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentPlanViewModel @Inject constructor(
    val paymentPlanUseCase: PaymentPlanUseCase,val allUsersUseCase: AllUsersUseCase
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var statePaymentPlan by mutableStateOf(emptyList<Plan>())

    private val _allUsers =
        MutableSharedFlow<ArrayList<UsersItem>>()
    val allUsers: MutableSharedFlow<ArrayList<UsersItem>>
        get() = _allUsers

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getPaymentPlan() {
        viewModelScope.launch(job) {
            statePaymentPlan = paymentPlanUseCase.getPaymentPlan().data?.data!!
        }
    }

    fun allUsers() {
        viewModelScope.launch(job) {
            _allUsers.emit(allUsersUseCase.allUser().data?.data!!)
        }
    }



}