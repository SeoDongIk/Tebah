package com.example.presentation.auth.state

interface SignUpState {
    val name: String
    val id: String
    val password: String
    val repeatPassword: String
    val isSigningUp: Boolean
    val isSigningIn: Boolean
}