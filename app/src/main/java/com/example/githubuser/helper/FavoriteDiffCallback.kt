package com.example.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser.models.GithubUserFavoriteModel

class FavoriteDiffCallback(private val mOldFavoriteList: ArrayList<GithubUserFavoriteModel>, private val mNewFavoriteList: List<GithubUserFavoriteModel>) : DiffUtil.Callback()  {
    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].id == mNewFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldList = mOldFavoriteList[oldItemPosition]
        val newList = mNewFavoriteList[newItemPosition]
        return oldList.login == newList.login && oldList.avatar_url == newList.avatar_url
    }
}