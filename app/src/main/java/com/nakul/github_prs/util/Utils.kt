package com.nakul.github_prs.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    const val INITIAL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val TARGET_FORMAT = "dd MMM''yy h:mm a"

    @JvmStatic
    fun getFormattedDate(date: String?): String = try {
        val parsingFormat = SimpleDateFormat(INITIAL_FORMAT, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val targetFormat = SimpleDateFormat(TARGET_FORMAT, Locale.getDefault()).apply{
            timeZone = TimeZone.getDefault()
        }
        targetFormat.format(parsingFormat.parse(date))
    } catch (exception: Exception) {
        Timber.w(exception)
        "-"
    }


    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView?, url: String?) {
        if (!url.isNullOrBlank())
            imageView?.let { view ->
                Glide.with(view).load(url).into(view)
            }
    }

}