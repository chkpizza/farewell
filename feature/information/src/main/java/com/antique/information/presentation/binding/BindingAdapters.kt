package com.antique.information.presentation.binding

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.antique.common.util.ApiState
import com.antique.information.R
import com.antique.information.domain.model.Preview
import com.antique.information.presentation.view.information.PreviewListAdapter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("url")
    fun bindImage(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                .load(it)
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("preview")
    fun bindPreview(recyclerView: RecyclerView, item: ApiState<List<Preview>>?) {
        item?.let { state ->
            when(state) {
                is ApiState.Success -> {
                    (recyclerView.adapter as? PreviewListAdapter)?.submitList(state.items)
                }
                is ApiState.Error -> {
                    Snackbar.make(recyclerView, recyclerView.resources.getString(R.string.fetch_preview_failure_text), Snackbar.LENGTH_SHORT).show()
                }
                is ApiState.Loading -> {

                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("amount")
    fun bindAmount(textView: TextView, item: Boolean) {
        if(item) {
            textView.background = ResourcesCompat.getDrawable(textView.resources, com.antique.common.R.drawable.shape_rectangle_8dp_pink, null)
            textView.setTextColor(textView.resources.getColor(com.antique.common.R.color.white, null))
            textView.text = textView.resources.getString(R.string.amount_free_text)
        } else {
            textView.background = ResourcesCompat.getDrawable(textView.resources, com.antique.common.R.drawable.shape_rectangle_8dp_orange, null)
            textView.setTextColor(textView.resources.getColor(com.antique.common.R.color.white, null))
            textView.text = textView.resources.getString(R.string.amount_pay_text)
        }
    }
}