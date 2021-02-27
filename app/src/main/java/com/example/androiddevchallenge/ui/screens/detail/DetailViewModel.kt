package com.example.androiddevchallenge.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.puppy.PuppyContract
import com.example.androiddevchallenge.di.SingletonInjector
import com.example.androiddevchallenge.ui.screens.model.Puppy
import com.example.androiddevchallenge.ui.screens.model.PuppyMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(
    private val puppyRepository: PuppyContract.Repository,
    puppyId: Long
) : ViewModel() {
    val state = MutableStateFlow<State>(State.Loading)

    init {
        getPuppy(puppyId)
    }

    fun getPuppy(puppyId: Long) {
        viewModelScope.launch {
            puppyRepository.getPuppy(puppyId)
                .onStart {
                    state.emit(State.Loading)
                }
                .catch { cause ->
                    state.emit(State.Error(cause))
                }
                .collect {
                    state.emit(State.Success(PuppyMapper.map(it)))
                }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val value: Puppy) : State()
        data class Error(val cause: Throwable) : State()
    }
}

class DetailViewModelFactory(
    private val singletonInjector: SingletonInjector,
    private val puppyId: Long
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(singletonInjector.providePuppyRepository(), puppyId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
