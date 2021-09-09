package com.example.githubuser.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapters.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.models.GithubUserDetailModel
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(userData().company == null){binding.ivCompany.visibility = View.GONE}
        if(userData().name == null){binding.tvNameDetail.visibility = View.GONE}
        if(userData().location == null){binding.ivLocation.visibility = View.GONE}

        binding.tvCompanyDetail.text = userData().company
        binding.tvNameDetail.text = userData().name
        binding.tvUserNameDetail.text = userData().login
        binding.tvLocationDetail.text = userData().location
        binding.tvFollowerDetail.text = userData().followers
        binding.tvFollowingDetail.text = userData().following
        binding.tvRepositoryDetail.text = userData().public_repos

        Glide.with(this)
            .load(userData().avatar_url)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    fun userData(): GithubUserDetailModel {
        val user = intent.getParcelableExtra<GithubUserDetailModel>(EXTRA_USER) as GithubUserDetailModel
        return user
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