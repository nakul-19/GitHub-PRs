package com.nakul.github_prs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakul.github_prs.data.GitHubRepository
import com.nakul.github_prs.model.PullRequestModel
import com.nakul.github_prs.model.ResultModel
import com.nakul.github_prs.ui.PRAdapter
import com.nakul.github_prs.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: GitHubRepository) : ViewModel(), PagingListener {

    val adapter by lazy { PRAdapter(this) }
    val showLoading = MutableLiveData(false)
    val errorMessage = MutableLiveData<String?>()
    private var isLastPage = false
    private var page = 1

    fun resetData() {
        isLastPage = false
        adapter.setData(listOf())
        page = 1
        getAndAddItems()
    }

    override fun getAndAddItems() {
        if (showLoading.value != true && isLastPage.not())
            viewModelScope.launch {
                repo.getPullRequests(page).flowOn(Dispatchers.Main).collect {
                    when (it) {
                        is ResultModel.Success -> {
                            handleSuccess(it.result)
                            errorMessage.postValue(null)
                        }
                        is ResultModel.Error -> {
                            showLoading.postValue(false)
                            errorMessage.postValue(it.message)
                        }
                        ResultModel.Loading -> {
                            showLoading.postValue(true)
                        }
                    }
                }
            }
    }

    private fun handleSuccess(result: List<PullRequestModel>?) {
        if (!result.isNullOrEmpty())
            adapter.addData(result)
        else isLastPage = true
        showLoading.postValue(false)
        page++
    }

}

interface PagingListener {
    fun getAndAddItems()
}