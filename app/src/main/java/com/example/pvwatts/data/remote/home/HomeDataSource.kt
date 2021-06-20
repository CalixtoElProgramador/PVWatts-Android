package com.example.pvwatts.data.remote.home

import android.graphics.Bitmap
import com.example.pvwatts.core.Result
import com.example.pvwatts.data.model.member.Project
import com.example.pvwatts.data.model.member.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class HomeDataSource {

    suspend fun setProject(imageBitmap: Bitmap, project: Project) {
        val user = FirebaseAuth.getInstance().currentUser
        val randomName = UUID.randomUUID().toString()
        val imageRef =
            FirebaseStorage.getInstance().reference.child("${user?.uid}/project/$randomName")
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val downloadUrl =
            imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()
        user?.let { it ->
            FirebaseFirestore.getInstance().collection("projects").add(
                Project(
                    battery_capacity = project.battery_capacity,
                    battery_depth_discharge = project.battery_depth_discharge,
                    battery_voltage = project.battery_voltage,
                    created_at = project.created_at,
                    days_autonomy = project.days_autonomy,
                    image_url = downloadUrl,
                    inverter_efficiency = project.inverter_efficiency,
                    load_max = project.load_max,
                    load_month = project.load_month,
                    location = project.location,
                    module_current = project.module_current,
                    module_power = project.module_power,
                    module_voltage = project.module_voltage,
                    name = project.name,
                    sun_hours = project.sun_hours,
                    temperature = project.temperature,
                    type_project = project.type_project,
                    uid = it.uid
                )
            ).await()
        }
    }

    suspend fun getProject(): Result<List<Project>> {
        val user = FirebaseAuth.getInstance().currentUser
        val projectsList = mutableListOf<Project>()
        val querySnapshot =
            FirebaseFirestore.getInstance().collection("projects").whereEqualTo("uid", user?.uid)
                .get().await()
        for (project in querySnapshot.documents) {
            project.toObject(Project::class.java)?.let {
                projectsList.add(it)
            }
        }
        return Result.Success(projectsList)
    }

    suspend fun getUser(): User? {
        val user = FirebaseAuth.getInstance().currentUser
        val querySnapshot =
            FirebaseFirestore.getInstance().collection("users").document("${user?.uid}").get()
                .await()
        return querySnapshot.toObject(User::class.java)
    }

}