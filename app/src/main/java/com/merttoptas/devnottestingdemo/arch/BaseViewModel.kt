package com.merttoptas.devnottestingdemo.arch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by mertcantoptas on 14.04.2023
 */
interface IViewState

abstract class BaseViewModel<State : IViewState> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State


    public val currentState: State get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState

    fun setState(reduce: State.() -> State) {
        _uiState.update { currentState.reduce() }
    }
}

