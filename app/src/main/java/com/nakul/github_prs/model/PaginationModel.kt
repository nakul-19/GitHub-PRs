package com.nakul.github_prs.model

import com.nakul.github_prs.ui.PRAdapter
import com.nakul.github_prs.viewmodel.MainViewModel

class PaginationModel(private val viewModel: MainViewModel) {

    var isLastPage = false
    private var page = 1
    val adapter = PRAdapter(this)

    fun reset() {
        isLastPage = false
        adapter.setData(listOf())
        page = 1
    }

    fun fetchData() {
        if (!isLastPage) viewModel.getItems(page)
    }

    fun addData(result: List<PullRequestModel>?) {
        isLastPage = result.isNullOrEmpty()
        adapter.addData(result.orEmpty(), isLastPage.not())
        page++
    }

}