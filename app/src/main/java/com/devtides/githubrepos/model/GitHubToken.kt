package com.devtides.githubrepos.model

import com.google.gson.annotations.SerializedName

    data class GitHubToken (

    @SerializedName("access_token")
    val accessToken: String?,

    @SerializedName("token_type")
    val tokenType: String?
)