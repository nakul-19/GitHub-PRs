package com.nakul.github_prs.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nakul.github_prs.BR
import com.nakul.github_prs.databinding.LayoutPullRequestItemBinding
import com.nakul.github_prs.databinding.LayoutLoaderBinding
import com.nakul.github_prs.model.PaginationModel
import com.nakul.github_prs.model.PullRequestModel

class PRAdapter(private val model: PaginationModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class PullRequestVH(val binding: LayoutPullRequestItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class LoaderVH(val binding: LayoutLoaderBinding) : RecyclerView.ViewHolder(binding.root)

    private var isLoading = false

    private val data = arrayListOf<PullRequestModel>()

    @SuppressLint("NotifyDataSetChanged") fun setData(list: List<PullRequestModel?>) {
        data.clear()
        data.addAll(list.filterNotNull())
        notifyDataSetChanged()
    }

    fun addData(list: List<PullRequestModel?>, load: Boolean = true) {
        val newList = list.filter { data.contains(it).not() }.filterNotNull()
        val oldSize = data.size
        data.addAll(newList)
        isLoading = load
        notifyItemRangeChanged(oldSize, data.size + 1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < data.size)
            PULL_REQUEST_VIEW_TYPE else LOADER_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == PULL_REQUEST_VIEW_TYPE) PullRequestVH(
            LayoutPullRequestItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        else LoaderVH(
            LayoutLoaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PullRequestVH -> holder.binding.apply {
                setVariable(BR.item, data[position])
                executePendingBindings()
            }
            is LoaderVH -> {
                holder.binding.progress.visibility = if (isLoading) View.VISIBLE else View.GONE
                model.fetchData()
            }
        }
    }

    override fun getItemCount() = data.size + 1

    companion object {
        const val PULL_REQUEST_VIEW_TYPE = 1
        const val LOADER_VIEW_TYPE = 2
    }
}