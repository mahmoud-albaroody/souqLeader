package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl

import com.alef.souqleader.data.remote.dto.PlanResponse



import javax.inject.Inject

class PaymentPlanUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getPaymentPlan(): Resource<PlanResponse> {
        return  repository.paymentPlan()
    }
}