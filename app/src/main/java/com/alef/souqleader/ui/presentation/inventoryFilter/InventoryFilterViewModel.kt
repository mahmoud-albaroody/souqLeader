package com.alef.souqleader.ui.presentation.inventoryFilter


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.CampaignResponse
import com.alef.souqleader.data.remote.dto.CategoryResponse
import com.alef.souqleader.data.remote.dto.CommunicationWayResponse
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.MarketerResponse
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.RegionsResponse
import com.alef.souqleader.data.remote.dto.SalesResponse
import com.alef.souqleader.domain.FilterUseCase
import com.alef.souqleader.domain.GetLeadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryFilterViewModel @Inject constructor(
    private val filterUseCase: FilterUseCase,val getLeadUseCase: GetLeadUseCase
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _allLead =
        MutableSharedFlow<Resource<LeadsStatusResponse>>()
    val allLead: MutableSharedFlow<Resource<LeadsStatusResponse>>
        get() = _allLead

    private val _region =
        MutableSharedFlow<Resource<RegionsResponse>>()
    val region: MutableSharedFlow<Resource<RegionsResponse>>
        get() = _region


    private val _propertyView =
        MutableSharedFlow<Resource<RegionsResponse>>()
    val propertyView: MutableSharedFlow<Resource<RegionsResponse>>
        get() = _propertyView

    private val _propertyCategory =
        MutableSharedFlow<Resource<CategoryResponse>>()
    val propertyCategory: MutableSharedFlow<Resource<CategoryResponse>>
        get() = _propertyCategory

    private val _propertyFinishing =
        MutableSharedFlow<Resource<RegionsResponse>>()
    val propertyFinishing: MutableSharedFlow<Resource<RegionsResponse>>
        get() = _propertyFinishing


    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }


    fun getRegion() {
        viewModelScope.launch(job) {
            filterUseCase.regions().catch {

            }
                .onStart {
                    _region.emit(Resource.Loading())
                }.buffer().collect {
                    _region.emit(it)
                }
        }
    }
    fun getLeads() {
        viewModelScope.launch(job) {
            getLeadUseCase.getLeadStatus().catch { }
                .onStart {
                    _allLead.emit(Resource.Loading())
                }.buffer().collect {
                    _allLead.emit(it)
                }
        }
    }

    fun propertyView() {
        viewModelScope.launch(job) {
            filterUseCase.propertyView().catch {

            }
                .onStart {
                    _propertyView.emit(Resource.Loading())
                }.buffer().collect {
                    _propertyView.emit(it)
                }
        }
    }

    fun propertyCategory() {
        viewModelScope.launch(job) {
            filterUseCase.propertyCategory().catch {
            }
                .onStart {
                    _propertyCategory.emit(Resource.Loading())
                }.buffer().collect {
                    _propertyCategory.emit(it)
                }
        }
    }


    fun propertyFinishing() {
        viewModelScope.launch(job) {
            filterUseCase.propertyFinishing().catch {

            }
                .onStart {
                    _propertyFinishing.emit(Resource.Loading())
                }.buffer().collect {
                    _propertyFinishing.emit(it)
                }
        }
    }

}