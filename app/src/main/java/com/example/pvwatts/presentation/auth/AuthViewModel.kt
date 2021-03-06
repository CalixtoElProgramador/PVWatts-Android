package com.example.pvwatts.presentation.auth

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.pvwatts.core.Result
import com.example.pvwatts.repository.auth.AuthRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class AuthViewModel(private val repo: AuthRepo) : ViewModel() {

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.singIn(email, password)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun signUp(
        name: String,
        lastname: String,
        email: String,
        password: String,
        imageBitmap: Bitmap
    ) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.signUp(name, lastname, email, password, imageBitmap)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun isEmailRegister(email: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.isEmailRegister(email)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class AuthViewModelFactory(private val repo: AuthRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repo) as T
    }
}