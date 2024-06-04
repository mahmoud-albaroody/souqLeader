package com.alef.souqleader.ui.presentation.gymScreen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.alef.souqleader.domain.model.Gym
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
//    private val getSymbolsUseCase: GymUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    var stateListOfGym by mutableStateOf(emptyList<Gym>())
    var vv by mutableStateOf(false)

//    val job = Job()
//    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        throwable.printStackTrace()
//    }
//
//
//
//
//
//    fun toggleFav(gym: Gym) {
//        val gyms = stateListOfGym.toMutableList()
//        val index = gyms.indexOf(gym)
//        gyms[index] = gyms[index].copy(isFav = !gyms[index].isFav)
//        stateListOfGym = gyms
//    }
//
//
//    fun getGym() {
//        // val gymLis: ArrayList<Gym> = arrayListOf()
//        viewModelScope.launch( dispatcher +errorHandler) {
//            vv = getSymbolsUseCase.getGym().success
////            getMap(getSymbolsUseCase.getGym().symbols)?.entries?.toList()
////                ?.forEachIndexed { index, it ->
////                    gymLis.add(
////                        Gym(
////                            1,
////                            it.key,
////                            R.drawable.baseline_signal_wifi_statusbar,
////                            it.value.toString()
////                        )
////                    )
////                }
////            stateListOfGym = gymLis
//
//        }
//    }

}