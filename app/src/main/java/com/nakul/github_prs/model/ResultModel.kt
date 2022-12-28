package com.nakul.github_prs.model

import com.nakul.github_prs.util.Constants

sealed class ResultModel<out T> {
    class Success<out T>(val result: T) : ResultModel<T>()
    object Loading : ResultModel<Nothing>()
    class Error(val message: String = Constants.DEFAULT_ERROR) : ResultModel<Nothing>()
}
