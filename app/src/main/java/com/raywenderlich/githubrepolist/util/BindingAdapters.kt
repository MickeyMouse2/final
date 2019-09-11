package com.raywenderlich.githubrepolist.util

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.raywenderlich.githubrepolist.ui.adapters.RepoListAdapter
import com.raywenderlich.githubrepolist.data.objects.UserResponse

@BindingAdapter(value = ["app:users", "app:adapter"], requireAll = true)
fun setItems(view: androidx.recyclerview.widget.RecyclerView, items: UserResponse?, adapter: RepoListAdapter) {
    if (items != null) {
        adapter.data = items.items
        adapter.notifyDataSetChanged()
    }
}

@BindingAdapter(value = ["app:loadWithUrl"])
fun ImageView.loadWithUrl(src: String) {
    com.bumptech.glide.Glide
        .with(this)
        .load(src)
        .into(this)
}