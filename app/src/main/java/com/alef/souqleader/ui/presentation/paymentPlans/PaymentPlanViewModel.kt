package com.alef.souqleader.ui.presentation.paymentPlans


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.Plan
import com.alef.souqleader.domain.GetLeadUseCase
import com.alef.souqleader.domain.PaymentPlanUseCase
import com.alef.souqleader.domain.SaveMultiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentPlanViewModel @Inject constructor(
    val paymentPlanUseCase: PaymentPlanUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var statePaymentPlan by mutableStateOf(emptyList<Plan>())

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getPaymentPlan() {
        viewModelScope.launch(job) {
            statePaymentPlan = paymentPlanUseCase.getPaymentPlan().data?.data!!
        }
    }

}