package com.nakul.github_prs.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nakul.github_prs.BR
import com.nakul.github_prs.databinding.LayoutPullRequestItemBinding
import com.nakul.github_prs.model.PullRequestModel
import com.nakul.github_prs.viewmodel.PagingListener

class PRAdapter(private val listener: PagingListener) : RecyclerView.Adapter<PRAdapter.VH>() {

    val data = arrayListOf<PullRequestModel>()

    @SuppressLint("NotifyDataSetChanged") fun setData(list: List<PullRequestModel?>) {
        data.clear()
        data.addAll(list.filterNotNull())
        notifyDataSetChanged()
    }

    fun addData(list: List<PullRequestModel?>) {
        val newList = list.filter { data.contains(it).not() }.filterNotNull()
        val oldSize = data.size
        data.addAll(newList)
        notifyItemRangeChanged(oldSize, data.size)
    }

    class VH(val binding: LayoutPullRequestItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutPullRequestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            setVariable(BR.item, data[position])
            executePendingBindings()
        }
        if (position == data.size - 1) listener.getAndAddItems()
    }

    override fun getItemCount() = data.size

}