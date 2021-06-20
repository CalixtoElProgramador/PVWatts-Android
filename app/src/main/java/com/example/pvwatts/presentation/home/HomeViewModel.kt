package com.example.pvwatts.presentation.home

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.pvwatts.core.Result
import com.example.pvwatts.data.model.member.Project
import com.example.pvwatts.repository.home.HomeRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProjectViewModel(private val repo: HomeRepo) : ViewModel() {

    fun setProject(imageBitmap: Bitmap, project: Project) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.setProject(imageBitmap, project)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getProject() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(repo.getProject())
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getUser() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.getUser()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class ProjectViewModelFactory(private val repo: HomeRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeRepo::class.java).newInstance(repo)
    }
}