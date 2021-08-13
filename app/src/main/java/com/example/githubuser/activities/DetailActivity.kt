package com.example.githubuser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.models.UserModel


class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var user = intent.getParcelableExtra<UserModel>(EXTRA_USER) as UserModel

        binding.tvCompanyDetail.text = user.company
        binding.tvNameDetail.text = user.name
        binding.tvUserNameDetail.text = user.username
        binding.tvLocationDetail.text = user.location
        binding.tvFollowerDetail.text = user.follower.toString()
        binding.tvFollowingDetail.text = user.following.toString()
        binding.tvRepositoryDetail.text = user.repository.toString()

        Glide.with(this)
            .load(user.avatar)
            .into(binding.ivIconDetail)
    }
}