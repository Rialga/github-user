package com.example.githubuser.adapters


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.views.DetailActivity
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.models.GithubUserDetailModel


class UserAdapter(private val ListUser: ArrayList<GithubUserDetailModel>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = ListUser[position]


        holder.binding.tvName.text = user.name
        holder.binding.tvUserName.text = user.login
        holder.binding.tvFollower.text = user.followers
        holder.binding.tvFollowing.text = user.following
        holder.binding.tvRepository.text = user.public_repos

        Glide.with(holder.itemView.context)
            .load(user.avatar_url)
            .into(holder.binding.ivIcon)

        holder.itemView.setOnClickListener { v ->
            val intentDetail = Intent(v.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_USER,user)
            v.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int {
        return ListUser.size
    }

    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)


}

