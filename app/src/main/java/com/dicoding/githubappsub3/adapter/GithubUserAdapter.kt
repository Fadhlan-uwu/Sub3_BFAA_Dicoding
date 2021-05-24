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

class GithubUserAdapter(private val activity: Activity) : RecyclerView.Adapter<GithubUserAdapter.GithubUserViewHolder>() {

    val TAG =GithubUserAdapter::class.java.simpleName

    var listGithub = ArrayList<GithubUser>()
        set(listUser) {
            if (listUser.size > 0) {
                this.listGithub.clear()
            }
            this.listGithub.addAll(listUser)

            notifyDataSetChanged()
        }

    fun setData(items: ArrayList<GithubUser>) {
        listGithub.clear()
        listGithub.addAll(items)
        notifyDataSetChanged()
    }

    inner class GithubUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGithubuserBinding.bind(itemView)
        @SuppressLint("ResourceType")
        fun bind(githubuser: GithubUser) {
            Glide.with(itemView.context)
                .load(githubuser.photo)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_baseline_error_24)
                .into(binding.imgPhoto)

            binding.tvUsername.text = githubuser.login
            Log.d(TAG, githubuser.toString())

            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(githubuser)
            }

            binding.rvItemGithubUser.setOnClickListener(CustomOnItemClickListener(bindingAdapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                override fun onItemClicked(view: View, position: Int) {
                    val intent = Intent(activity, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USER, githubuser.login)
                    intent.putExtra(DetailUserActivity.EXTRA_STATE, githubuser)
                    intent.putExtra(DetailUserActivity.EXTRA_FAVORITE, "favorite")
                    activity.startActivity(intent)
                }
            }))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_githubuser, parent, false)
        return GithubUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        holder.bind(listGithub[position])
    }

    override fun getItemCount(): Int = listGithub.size

    private var onItemClickCallback: OnitemClickCallback? = null

    interface OnitemClickCallback {
        fun onItemClicked(user: GithubUser)
    }
}