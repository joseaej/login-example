package com.example.loginlourdes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginlourdes.domain.data.model.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val session: Session) : ViewModel() {

    var state by mutableStateOf(MainState(activeAccount = false))
        private set

    init {
        viewModelScope.launch {
            session.isUserLoggedIn().collectLatest { isLoggedIn ->
                state = state.copy(activeAccount = isLoggedIn)
            }
        }
    }
}
