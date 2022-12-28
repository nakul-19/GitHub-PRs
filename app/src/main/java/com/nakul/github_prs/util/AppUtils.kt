package com.nakul.github_prs.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.nakul.github_prs.R

object AppUtils {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView?, url: String?) {
        if (!url.isNullOrBlank())
            imageView?.let { view ->
                Glide.with(view).load(url).into(view)
            }
    }

    fun getNewSnackbar(view: View, message: String, action: (view: View) -> Unit): Snackbar {
        val context = view.context
        return Snackbar.make(context, view, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(context.getString(R.string.retry), action)
    }
}