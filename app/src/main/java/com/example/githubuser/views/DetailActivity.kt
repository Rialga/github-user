package com.example.githubuser.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Toast
import com.example.githubuser.factory.ViewModelFactory
import com.example.githubuser.models.GithubUserFavoriteModel


class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val intentData = intent.getParcelableExtra<GithubUserDetailModel>(EXTRA_USER) as GithubUserModel.Item
        this.title = "Detail User"


        detailViewModel = obtainViewModel(this)
        detailViewModel.getData(intentData.login)



        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)

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
            showLoading(false)
        })
        val sectionsPagerAdapter = SectionsPagerAdapter(this)



        val viewPager =  binding.viewPager
        val tabs = binding.tabs
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabs,viewPager){
            tab, position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f



        val githubUserFavoriteModel = GithubUserFavoriteModel()

        detailViewModel.search(intentData.login)

        var isFavorite = false
            detailViewModel.search(intentData.login).observe(this, { data ->

                if(data.isNotEmpty()){
                    isFavorite = true
                    githubUserFavoriteModel.avatar_url =data.last().avatar_url
                    githubUserFavoriteModel.user_id =data.last().user_id
                    githubUserFavoriteModel.login =data.last().login
                    githubUserFavoriteModel.id =data.last().id
                    binding.favoriteButton.imageTintList = ColorStateList.valueOf(Color.RED)

                }


            })


        if(!isFavorite){
            githubUserFavoriteModel.avatar_url = intentData.avatar_url
            githubUserFavoriteModel.user_id = intentData.id
            githubUserFavoriteModel.login = intentData.login
            binding.favoriteButton.imageTintList = ColorStateList.valueOf(Color.GRAY)
        }

        binding.favoriteButton.setOnClickListener {
            if(isFavorite){
                detailViewModel.delete(githubUserFavoriteModel)
                binding.favoriteButton.imageTintList = ColorStateList.valueOf(Color.GRAY)
                Toast.makeText(this, "Remove From Favorite", Toast.LENGTH_SHORT).show()
                isFavorite = false

            }
            else{
                detailViewModel.insert(githubUserFavoriteModel)
                binding.favoriteButton.imageTintList = ColorStateList.valueOf(Color.RED)
                Toast.makeText(this, "Add to Favorite", Toast.LENGTH_SHORT).show()
                isFavorite = true
            }
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting-> startActivity(Intent(this@DetailActivity, SettingActivity::class.java))
            R.id.favorite-> startActivity(Intent(this@DetailActivity, FavoriteListActivity::class.java))

        }
        return super.onOptionsItemSelected(item)

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.loadingDetail.visibility = View.VISIBLE
            binding.scrollView2.visibility = View.GONE
        } else {
            binding.loadingDetail.visibility = View.GONE
            binding.scrollView2.visibility = View.VISIBLE
        }
    }


    fun getUsername(): String {
        val intentData = intent.getParcelableExtra<GithubUserDetailModel>(EXTRA_USER) as GithubUserModel.Item
        return intentData.login
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