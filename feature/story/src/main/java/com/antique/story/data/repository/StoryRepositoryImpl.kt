package com.antique.story.data.repository

import androidx.core.net.toUri
import com.antique.common.util.Constant
import com.antique.story.data.mapper.mapperToDomain
import com.antique.story.data.mapper.mapperToDto
import com.antique.story.data.model.PlaceDto
import com.antique.story.data.model.StoryDto
import com.antique.story.domain.model.Door
import com.antique.story.domain.model.PlaceInformation
import com.antique.story.domain.model.Story
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
    override suspend fun registerStory(body: String, pictures: List<String>, videos: List<String>, place: PlaceInformation, date: String): Story = withContext(dispatcher) {
        val pictureList = mutableListOf<String>()
        val videoList = mutableListOf<String>()
        val placeDto = mapperToDto(place)
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

        val storyDto = StoryDto(body, pictureList, videoList, placeDto,  date, id)

        Firebase.database.reference.child(Constant.STORY_NODE).child(uid).child(id).setValue(storyDto).await()
        mapperToDomain(Firebase.database.reference.child(Constant.STORY_NODE).child(uid).child(id).get().await().getValue(StoryDto::class.java) ?: throw RuntimeException())
    }

    override suspend fun fetchStories(index: String): List<Story> = withContext(dispatcher) {
        val stories = mutableListOf<Story>()
        val uid = Firebase.auth.currentUser?.uid.toString()

        if(index.isEmpty()) {
            val response = Firebase.database.reference.child(Constant.STORY_NODE).child(uid).limitToLast(10).get().await()

            response.children.forEach {
                it.getValue(StoryDto::class.java)?.let { storyDto ->
                    stories.add(mapperToDomain(storyDto))
                }
            }

            stories.reversed().toList()
        } else {
            val response = Firebase.database.reference.child(Constant.STORY_NODE).child(uid).orderByKey().endBefore(index).limitToLast(10).get().await()

            response.children.forEach {
                it.getValue(StoryDto::class.java)?.let { storyDto ->
                    stories.add(mapperToDomain(storyDto))
                }
            }
            stories.reversed().toList()
        }
    }

    override suspend fun fetchDoor(): Door = withContext(dispatcher) {
        val uid = Firebase.auth.currentUser?.uid.toString()
        val nickName = Firebase.database.reference.child(Constant.USER_NODE).child(uid).child(Constant.NICKNAME_NODE).get().await().getValue(String::class.java) ?: throw RuntimeException()
        val door = Firebase.database.reference.child(Constant.DOOR_NODE).get().await().getValue(String::class.java) ?: throw RuntimeException()

        Door(nickName, door)
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