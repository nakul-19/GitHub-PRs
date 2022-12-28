package com.nakul.github_prs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakul.github_prs.data.GitHubRepository
import com.nakul.github_prs.model.PaginationModel
import com.nakul.github_prs.model.PullRequestModel
import com.nakul.github_prs.model.ResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: GitHubRepository) : ViewModel() {

    val model by lazy { PaginationModel(this) }
    val showLoading = MutableLiveData(false)
    val errorMessage = MutableLiveData<String?>()

    fun getAdapter() = model.adapter

    fun resetData() {
        model.reset()
        getItems(0, true)
    }

    fun getItems(page: Int, isForce: Boolean = false) {
        if (showLoading.value != true)
            viewModelScope.launch {
                repo.getPullRequests(page).flowOn(Dispatchers.Main).collect {
                    when (it) {
                        is ResultModel.Success -> {
                            errorMessage.postValue(null)
                            model.addData(it.result)
                            showLoading.postValue(false)
                        }
                        is ResultModel.Error -> {
                            showLoading.postValue(false)
                            errorMessage.postValue(it.message)
                        }
                        ResultModel.Loading -> {
                            if (isForce)
                                showLoading.postValue(true)
                        }
                    }
                }
            }
    }

}
