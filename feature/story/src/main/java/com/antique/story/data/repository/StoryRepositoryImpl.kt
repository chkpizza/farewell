package com.antique.story.data.repository

import com.antique.common.data.User
import com.antique.common.util.Constant
import com.antique.story.data.model.DoorDto
import com.antique.story.data.model.StoryDto
import com.antique.story.domain.repository.StoryRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher) :
    StoryRepository {
    override suspend fun getStories(): List<StoryDto> = withContext(dispatcher) {
        val stories = mutableListOf<StoryDto>()
        val uid = Firebase.auth.currentUser?.uid.toString()

        val response = Firebase.database.reference.child(Constant.STORY_NODE).child(uid).limitToLast(20).get().await()
        response.children.forEach {
            it.getValue(StoryDto::class.java)?.let { story ->
                stories.add(story)
            }
        }
        stories.toList()
    }

    override suspend fun getMoreStories(idx: String): List<StoryDto> = withContext(dispatcher) {
        val stories = mutableListOf<StoryDto>()
        val uid = Firebase.auth.currentUser?.uid.toString()

        val response = Firebase.database.reference.child(Constant.STORY_NODE).child(uid).orderByKey().endBefore(idx).limitToLast(20).get().await()
        response.children.forEach {
            it.getValue(StoryDto::class.java)?.let { story ->
                stories.add(story)
            }
        }
        stories.toList()
    }

    override suspend fun getDoor(): DoorDto = withContext(dispatcher) {
        val uid = Firebase.auth.currentUser?.uid.toString()
        val nickName = Firebase.database.reference.child(Constant.USER_NODE).child(uid).get().await().getValue(User::class.java)?.let {
            it.nickName
        } ?: throw RuntimeException()

        val doorText = Firebase.database.reference.child(Constant.DOOR_NODE).get().await().getValue(String::class.java)?.let {
            it
        } ?: throw RuntimeException()

        DoorDto(nickName, doorText)
    }

    override suspend fun getStory(storyId: String): StoryDto = withContext(dispatcher) {
        val uid = Firebase.auth.currentUser?.uid.toString()
        Firebase.database.reference.child(Constant.STORY_NODE).child(uid).child(storyId).get().await().getValue(
            StoryDto::class.java) ?: throw RuntimeException()
    }

    override suspend fun removeStory(storyId: String): Boolean = withContext(dispatcher) {
        val uid = Firebase.auth.currentUser?.uid.toString()
        Firebase.database.reference.child(Constant.STORY_NODE).child(uid).child(storyId).setValue(null).await()
        !Firebase.database.reference.child(Constant.STORY_NODE).child(uid).child(storyId).get().await().exists()
    }
}