package com.alef.souqleader.ui.presentation.gymDetailsScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.alef.souqleader.domain.model.Gym
import com.google.gson.Gson

class DetailsGymScreenViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    var gym: Gym
    init {
         gym = Gson().fromJson(checkNotNull(savedStateHandle["gym_id"]).toString(), Gym::class.java)

    }
}