package com.example.presentation.model

import org.orbitmvi.orbit.ContainerHost

interface SignUpViewModel<S : SignUpState> : ContainerHost<S, SignUpSideEffect> {
    fun onSignInClick()
    fun onSignUpClick()
}