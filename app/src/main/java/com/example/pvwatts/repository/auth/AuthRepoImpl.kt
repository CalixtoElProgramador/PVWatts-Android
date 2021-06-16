package com.example.pvwatts.repository.auth

import android.graphics.Bitmap
import com.example.pvwatts.data.remote.auth.UserDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private val dataSource: UserDataSource) : AuthRepo {

    override suspend fun singIn(email: String, password: String): FirebaseUser? {
        return dataSource.signIn(email, password)
    }

    override suspend fun signUp(
        name: String,
        lastname: String,
        email: String,
        password: String,
        imageBitmap: Bitmap
    ): FirebaseUser? {
        return dataSource.signUp(name, lastname, email, password, imageBitmap)
    }

    override suspend fun isEmailRegister(email: String): Boolean {
        return dataSource.isEmailRegister(email)
    }
}