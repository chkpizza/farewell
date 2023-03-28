package com.antique.story.data.repo

import androidx.core.net.toUri
import com.antique.common.util.Constant
import com.antique.story.data.model.place.PlaceResponse
import com.antique.story.data.model.story.story.Content
import com.antique.story.data.model.story.story.Place
import com.antique.story.data.model.story.story.Story
import com.antique.story.data.retrofit.PlaceApiService
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

class WriteStoryRepositoryImpl @Inject constructor(
    private val apiService: PlaceApiService,
    private val dispatcher: CoroutineDispatcher
) : WriteStoryRepository {
    override suspend fun getLocations(key: String, query: String, page: Int): PlaceResponse = withContext(dispatcher) {
        val response = apiService.getPlaceByKeyword(key, query, page)
        if(response.isSuccessful) {
            response.body() ?: throw RuntimeException("body is null")
        } else {
            throw RuntimeException("not successful")
        }
    }

    override suspend fun registerStory(body: String, contents: List<Content>, place: Place, date: String) = withContext(dispatcher) {
        val contentList = mutableListOf<Content>()
        contents.forEach {
            when(it.type) {
                Constant.PHOTO_CONTENT -> {
                    val downloadUrl = uploadPhoto(it.uri)
                    contentList.add(Content(downloadUrl, Constant.PHOTO_CONTENT))
                }
                Constant.VIDEO_CONTENT -> {
                    val downloadUrl = uploadVideo(it.uri)
                    contentList.add(Content(downloadUrl, Constant.VIDEO_CONTENT))
                }
                else -> {}
            }
        }
        val uid = Firebase.auth.currentUser?.uid.toString()
        val storyId = Firebase.database.reference.child(Constant.STORY_NODE).child(uid).push().key.toString()
        val story = Story(body, contentList, place, date, storyId)

        Firebase.database.reference.child(Constant.STORY_NODE).child(uid).child(storyId).setValue(story).await()
        Firebase.database.reference.child(Constant.STORY_NODE).child(uid).child(storyId).get().await().getValue(
            Story::class.java)?.let {
            it
        } ?: throw RuntimeException()
    }

    private suspend fun uploadPhoto(uri: String): String {
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