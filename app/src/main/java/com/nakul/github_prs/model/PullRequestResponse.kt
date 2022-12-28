package com.nakul.github_prs.model

import com.google.gson.annotations.SerializedName

data class PullRequestResponse(
    @SerializedName("title") val title: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("closed_at") val closedAt: String?,
    @SerializedName("user") val user: UserResponse?
) {
    fun toPullRequestModel() = PullRequestModel(
        title, createdAt, closedAt, user?.name, user?.imageUrl
    )
}

data class UserResponse(
    @SerializedName("login") val name: String?,
    @SerializedName("avatar_url") val imageUrl: String?,
)