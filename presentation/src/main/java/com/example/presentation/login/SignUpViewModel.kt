package com.example.presentation.login

import androidx.lifecycle.ViewModel
import com.example.presentation.model.SignUpSideEffect
import com.example.presentation.model.SignUpState
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost

interface SignUpViewModel<S : SignUpState> : ContainerHost<S, SignUpSideEffect> {
    fun onSignUpClick()
}