package com.example.githubuser.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.helper.FavoriteDiffCallback
import com.example.githubuser.models.GithubUserFavoriteModel
import com.example.githubuser.models.GithubUserModel
import com.example.githubuser.views.DetailActivity

class FavoriteListAdapter : RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>() {
    private val listFavorite = ArrayList<GithubUserFavoriteModel>()
    fun setListFavorite(listFavorite: List<GithubUserFavoriteModel>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorite, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val userFavorite = listFavorite[position]

        var detailUser = GithubUserModel.Item(userFavorite.user_id ,
            userFavorite.login!!, userFavorite.avatar_url.toString()
        )

        holder.binding.tvId.text = "id : "+userFavorite.user_id.toString()
        holder.binding.tvUserName.text = userFavorite.login


        Glide.with(holder.itemView.context)
            .load(userFavorite.avatar_url)
            .into(holder.binding.ivIcon)

        holder.itemView.setOnClickListener { v ->
            val intentDetail = Intent(v.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_USER,detailUser)
            v.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }


    inner class FavoriteViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {}
}