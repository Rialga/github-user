package com.example.githubuser.views

import android.R.id
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapters.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.models.GithubUserDetailModel
import com.example.githubuser.models.GithubUserModel
import com.example.githubuser.viewmodels.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient.log
import android.R.id.message
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit


class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val intentData = intent.getParcelableExtra<GithubUserDetailModel>(EXTRA_USER) as GithubUserModel.Item
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        detailViewModel.getData(intentData.login)

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel.getDetail().observe(this, { gitHubUserData ->

            with(binding){
                if(gitHubUserData.name == null){tvNameDetail.visibility = View.GONE}
                if(gitHubUserData.location == null){ivLocation.visibility = View.GONE}

                tvCompanyDetail.text = gitHubUserData.company
                tvNameDetail.text = gitHubUserData.name
                tvUserNameDetail.text = gitHubUserData.login
                tvLocationDetail.text = gitHubUserData.location
                tvFollowerDetail.text = gitHubUserData.followers
                tvFollowingDetail.text = gitHubUserData.following
                tvRepositoryDetail.text = gitHubUserData.public_repos
            }
            Glide.with(this)
                .load(gitHubUserData.avatar_url)
                .into(binding.ivIconDetail)
        })
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
        if (item.itemId == R.id.setting) {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
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