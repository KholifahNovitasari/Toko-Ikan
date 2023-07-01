package com.example.tokoikanapp.ui

import android.media.tv.TimelineRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tokoikanapp.model.Fish
import com.example.tokoikanapp.repository.FishRepository
import kotlinx.coroutines.launch
import kotlin.IllegalArgumentException

class FishViewModel(private val repository: FishRepository): ViewModel() {
    val allFish: LiveData<List<Fish>> = repository.allFish.asLiveData()

    fun insert(fish: Fish) = viewModelScope.launch {
        repository.insertFish(fish)
    }

    fun delete(fish: Fish) = viewModelScope.launch {
        repository.deleteFish(fish)
    }

    fun update(fish: Fish) = viewModelScope.launch {
        repository.updateFish(fish)
    }
}

class FishViewModeFactory(private val repository: FishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((FishViewModel::class.java))){
        return FishViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}