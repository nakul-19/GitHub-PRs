package com.nakul.github_prs.data

import com.nakul.github_prs.model.PullRequestResponse
import com.nakul.github_prs.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {

    @GET(Constants.PULL_REQUESTS_API)
    suspend fun getPullRequests(
        @Query(Constants.QUERY_PAGE_NUMBER) page: Int,
        @Query(Constants.QUERY_PAGE_SIZE) pageSize: Int = Constants.PAGE_SIZE
    ): Response<List<PullRequestResponse?>>

}