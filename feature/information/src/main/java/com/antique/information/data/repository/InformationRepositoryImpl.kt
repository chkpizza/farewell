package com.antique.information.data.repository

import android.util.Log
import com.antique.common.util.Constant
import com.antique.information.data.mapper.mapperToDomain
import com.antique.information.data.model.PreviewDto
import com.antique.information.domain.model.Preview
import com.antique.information.domain.repository.InformationRepository
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InformationRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher) : InformationRepository {
    override suspend fun fetchPreview(): List<Preview> = withContext(dispatcher) {
        val preview = mutableListOf<Preview>()

        Firebase.database.reference.child(Constant.PREVIEW_NODE).get().await().children.forEach {
            it.getValue(PreviewDto::class.java)?.let { previewDto ->
                Log.d("previewTest", previewDto.toString())
                preview.add(mapperToDomain(previewDto))
            } ?: throw RuntimeException()
        }
        preview
    }

}