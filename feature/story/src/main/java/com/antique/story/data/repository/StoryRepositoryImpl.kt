package com.antique.story.data.repository

import androidx.core.net.toUri
import com.antique.common.util.Constant
import com.antique.story.data.model.StoryDto
import com.antique.story.domain.repository.StoryRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher) : StoryRepository {
    override suspend fun registerStory(body: String, pictures: List<String>, videos: List<String>, date: String): Boolean = withContext(dispatcher) {
        val pictureList = mutableListOf<String>()
        val videoList = mutableListOf<String>()
        val uid = Firebase.auth.currentUser?.uid.toString()
        val id = Firebase.database.reference.child(Constant.STORY_NODE).child(uid).push().key.toString()

        if(pictures.isNotEmpty()) {
            pictures.forEach {
                pictureList.add(uploadPicture(it))
            }
        }

        if(videos.isNotEmpty()) {
            videos.forEach {
                videoList.add(uploadVideo(it))
            }
        }

        val storyDto = StoryDto(body, pictureList, videoList, date, id)

        Firebase.database.reference.child(Constant.STORY_NODE).child(uid).child(id).setValue(storyDto).await()
        Firebase.database.reference.child(Constant.STORY_NODE).child(uid).child(id).get().await().exists()
    }

    private suspend fun uploadPicture(uri: String): String {
        val fileName = SimpleDateFormat("yyyy-MM-dd-hhmmss").format(Date()) + ".jpg"
        Firebase.storage.reference.child("images/${fileName}").putFile(uri.toUri()).await()
        return Firebase.storage.reference.child("images/$fileName").downloadUrl.await().toString()
    }

    private suspend fun uploadVideo(uri: String): String {
        val fileName = SimpleDateFormat("yyyy-MM-dd-hhmmss").format(Date()) + ".mp4"
        Firebase.storage.reference.child("videos/${fileName}").putFile(uri.toUri()).await()
        return Firebase.storage.reference.child("videos/${fileName}").downloadUrl.await().toString()
    }
}