package com.dicoding.consumerapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.consumerapp.R
import com.dicoding.consumerapp.databinding.ItemGithubuserBinding
import com.dicoding.consumerapp.model.GithubUser

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

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