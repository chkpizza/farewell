package com.antique.story.repo

import com.antique.common.data.User
import com.antique.common.util.Constant
import com.antique.story.data.story.door.Door
import com.antique.story.data.story.story.Story
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher) : StoryRepository {
    override suspend fun getStories(): List<Story> = withContext(dispatcher) {
        val stories = mutableListOf<Story>()
        val uid = Firebase.auth.currentUser?.uid.toString()

        val response = Firebase.database.reference.child(Constant.STORY_NODE).child(Constant.USER_NODE).child(uid).limitToLast(4).get().await()
        response.children.forEach {
            it.getValue(Story::class.java)?.let { story ->
                stories.add(story)
            }
        }
        stories.toList()
    }

    override suspend fun getMoreStories(idx: String): List<Story> = withContext(dispatcher) {
        val stories = mutableListOf<Story>()
        val uid = Firebase.auth.currentUser?.uid.toString()

        val response = Firebase.database.reference.child(Constant.STORY_NODE).child(Constant.USER_NODE).child(uid).orderByKey().endBefore(idx).limitToLast(4).get().await()
        response.children.forEach {
            it.getValue(Story::class.java)?.let { story ->
                stories.add(story)
            }
        }
        stories.toList()
    }

    override suspend fun getDoor(): Door = withContext(dispatcher) {
        val uid = Firebase.auth.currentUser?.uid.toString()
        val nickName = Firebase.database.reference.child(Constant.USER_NODE).child(uid).get().await().getValue(User::class.java)?.let {
            it.nickName
        } ?: throw RuntimeException()

        val doorText = Firebase.database.reference.child(Constant.DOOR_NODE).get().await().getValue(String::class.java)?.let {
            it
        } ?: throw RuntimeException()

        Door(nickName, doorText)
    }
}