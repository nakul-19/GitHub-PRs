package com.nakul.github_prs.data

import com.nakul.github_prs.model.ResultModel
import com.nakul.github_prs.util.Constants
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GitHubRepository @Inject constructor(private val api: GitHubApi) {

    suspend fun getPullRequests(page: Int) = flow {
        emit(ResultModel.Loading)
        kotlin.runCatching {
            val response = api.getPullRequests(page)
            if (response.isSuccessful) {
                val list = response.body()?.mapNotNull { it?.toPullRequestModel() }
                emit(ResultModel.Success(list))
            } else if (response.code() == Constants.LIMIT_EXCEEDED_ERROR_CODE) {
                    emit(ResultModel.Error(Constants.LIMIT_EXCEEDED))
            }
        }.getOrElse { exception ->
            emit(ResultModel.Error(if (exception is IOException) Constants.INTERNET_ERROR else Constants.DEFAULT_ERROR))
        }
    }

}