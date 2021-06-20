package com.example.pvwatts.repository.home

import android.graphics.Bitmap
import com.example.pvwatts.core.Result
import com.example.pvwatts.data.model.member.Project
import com.example.pvwatts.data.model.member.User
import com.example.pvwatts.data.remote.home.HomeDataSource

class HomeRepoImpl(private val dataSource: HomeDataSource): HomeRepo {

    override suspend fun setProject(imageBitmap: Bitmap, project: Project) {
        dataSource.setProject(imageBitmap, project)
    }

    override suspend fun getProject(): Result<List<Project>> = dataSource.getProject()

    override suspend fun getUser(): User? = dataSource.getUser()
}