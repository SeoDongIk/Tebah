package com.example.presentation.auth.viewmodel

import com.example.presentation.auth.state.SignUpSideEffect
import com.example.presentation.auth.state.SignUpState
import org.orbitmvi.orbit.ContainerHost

interface SignUpViewModel<S : SignUpState> : ContainerHost<S, SignUpSideEffect> {
    fun onSignInClick()
    fun onSignUpClick()
}