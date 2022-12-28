package com.nakul.github_prs.util

object Constants {
    const val BASE_URL = "https://api.github.com/"
    const val PULL_REQUESTS_API = "repos/square/retrofit/pulls?state=closed"
    const val QUERY_PAGE_SIZE = "per_page"
    const val QUERY_PAGE_NUMBER = "page"
    const val PAGE_SIZE = 10
    const val DEFAULT_ERROR = "Something went wrong."
    const val INTERNET_ERROR = "No internet."
    const val LIMIT_EXCEEDED = "API rate limit exceeded."
    const val LIMIT_EXCEEDED_ERROR_CODE = 403
}