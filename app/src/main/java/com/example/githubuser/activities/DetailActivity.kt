package com.example.githubuser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapters.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.models.UserModel
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<UserModel>(EXTRA_USER) as UserModel

        binding.tvCompanyDetail.text = user.company
        binding.tvNameDetail.text = user.name
        binding.tvUserNameDetail.text = user.username
        binding.tvLocationDetail.text = user.location
        binding.tvFollowerDetail.text = user.follower
        binding.tvFollowingDetail.text = user.following
        binding.tvRepositoryDetail.text = user.repository

        Glide.with(this)
            .load(user.avatar)
            .into(binding.ivIconDetail)



        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        val viewPager =  binding.viewPager
        val tabs = binding.tabs
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabs,viewPager){
            tab, position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }
}