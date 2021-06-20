package com.example.pvwatts.repository.home

import android.graphics.Bitmap
import com.example.pvwatts.core.Result
import com.example.pvwatts.data.model.member.Project
import com.example.pvwatts.data.model.member.User

interface HomeRepo {

    suspend fun setProject(imageBitmap: Bitmap, project: Project)

    suspend fun getProject(): Result<List<Project>>

    suspend fun getUser(): User?

}