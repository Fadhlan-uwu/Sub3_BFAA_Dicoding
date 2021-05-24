package com.dicoding.githubappsub3.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubappsub3.R
import com.dicoding.githubappsub3.databinding.ItemGithubuserBinding
import com.dicoding.githubappsub3.model.GithubUser
import com.dicoding.githubappsub3.ui.CustomOnItemClickListener
import com.dicoding.githubappsub3.ui.DetailUserActivity

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    val TAG = FavoriteAdapter::class.java.simpleName

    var listUserFav = ArrayList<GithubUser>()
        set(listUser) {
            if (listUser.size > 0) {
                this.listUserFav.clear()
            }
            this.listUserFav.addAll(listUser)

            notifyDataSetChanged()
        }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGithubuserBinding.bind(itemView)
        @SuppressLint("ResourceType")
        fun bind(user: GithubUser) {
            Glide.with(itemView.context)
                    .load(user.photo)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_baseline_error_24)
                    .into(binding.imgPhoto)

            binding.tvUsername.text = user.login
            Log.d(TAG, user.toString())

            // item clicked
            binding.rvItemGithubUser.setOnClickListener(CustomOnItemClickListener(bindingAdapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                override fun onItemClicked(view: View, position: Int) {
                    val intent = Intent(activity, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USER, user.login)
                    intent.putExtra(DetailUserActivity.EXTRA_STATE, user)
                    intent.putExtra(DetailUserActivity.EXTRA_FAVORITE, "favorite")
                    activity.startActivity(intent)
                }
            }))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_githubuser, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        holder.bind(listUserFav[position])
    }

    override fun getItemCount(): Int = this.listUserFav.size
}