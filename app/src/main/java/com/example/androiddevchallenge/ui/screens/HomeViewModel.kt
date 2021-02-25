package com.example.androiddevchallenge.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.puppy.PuppyContract
import com.example.androiddevchallenge.ui.screens.model.Puppy
import com.example.androiddevchallenge.ui.screens.model.PuppyMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(private val puppyRepository: PuppyContract.Repository) : ViewModel() {
    val state = MutableStateFlow<State>(State.Loading)

    init {
        viewModelScope.launch {
            puppyRepository.getAllPuppies()
                .onStart {
                    state.emit(State.Loading)
                }
                .catch { cause ->
                    state.emit(State.Error(cause))
                }
                .collect {
                    state.emit(State.Success(it.map(PuppyMapper::map)))
                }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val value: List<Puppy>) : State()
        data class Error(val cause: Throwable) : State()
    }
}
