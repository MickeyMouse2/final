
package com.raywenderlich.githubrepolist.ui.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.*
import kotlinx.android.synthetic.main.item_repo.view.*
import android.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import com.raywenderlich.githubrepolist.R
import com.raywenderlich.githubrepolist.data.objects.ItemsItem
import com.raywenderlich.githubrepolist.util.loadWithUrl

class RepoListAdapter(
        var data: List<ItemsItem> = ArrayList(),
        var itemClick: ((item: ItemsItem) -> Unit?)? = null
) : androidx.recyclerview.widget.RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
    return ViewHolder(view, itemClick)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindForecast(data[position])

  override fun getItemCount() = data.size



  class ViewHolder(view: View, private val itemClick: ((item: ItemsItem) -> Unit?)? = null) :
          androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    fun bindForecast(item: ItemsItem) {
      with(itemView){
        userAvatar.loadWithUrl(item.avatarUrl)
        userName.text = item.login
        showUserLocation.setOnClickListener {
          itemClick?.run { item.htmlUrl }
        }
      }
    }

  }



}
