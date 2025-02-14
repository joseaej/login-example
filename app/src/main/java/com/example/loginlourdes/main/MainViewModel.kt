package com.example.loginlourdes.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginlourdes.domain.data.model.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val session: Session) : ViewModel() {

    var state by mutableStateOf(MainState(activeAccount = false))
        private set

    init {
        checkUser()
    }
    private fun checkUser(){
        viewModelScope.launch {
            session.isUserLoggedIn().collect() { isLoggedIn ->
                state = MainState(activeAccount = isLoggedIn)
            }
        }
    }
}
